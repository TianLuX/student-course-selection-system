package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TimeDao;
import entity.Time;
import org.apache.commons.lang3.StringUtils;

import dao.DaoFactory;
import entity.Student;
import entity.Teacher;
import entity.Course;
import utils.MD5;
import utils.PageInfo;
import utils.PathUtils;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if("list".equals(method)) {
            this.list(request, response);
        }else if("add".equals(method)) {
            this.add(request, response);
        }else if("v_add".equals(method)) {
            try {
                this.v_add(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("edit".equals(method)) {
            this.findById(request, response);
        }else if("editsubmit".equals(method)) {
            try {
                this.editsubmit(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("delete".equals(method)) {
            this.delete(request, response);
        }else if("time".equals(method)){
            try {
                this.time(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void time(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        Time time = new Time();
        time.setStart(start);
        time.setEnd(end);
        DaoFactory.getInstance().getTimeDao().add(time);
    }
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            DaoFactory.getInstance().getCourseDao().delete(Integer.parseInt(id));
            //直接重定向到列表页面
            response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void editsubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cName = request.getParameter("cName");
        Integer cId = getIntParameter(request, "cId");
        //老师ID
        Integer tId = Integer.parseInt(request.getParameter("tId"));
        String cTime = request.getParameter("cTime");
        Course course = new Course();
        course.setcId(cId);
        course.setcName(cName);
        //新建一个Teacher对象，设置ID，再通过设置course对象的Teacher关联
        Teacher teacher = new Teacher();
        teacher.settId(tId);
        course.setTeacher(teacher);
        course.setcTime(cTime);
        try {
            DaoFactory.getInstance().getCourseDao().update(course);
            //直接重定向到列表页面
            response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void findById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
//        需要把老师列表传进来
        PageInfo<Teacher> pageInfo = new PageInfo<>(1);
        pageInfo.setPageSize(1000);
        try {
            Course course = DaoFactory.getInstance().getCourseDao().findById(Integer.parseInt(id));
            pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
            request.setAttribute("course", course);
            request.setAttribute("teachers", pageInfo.getList());
            request.getRequestDispatcher("page/course/update.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查找并添加老师
    private void v_add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageInfo<Teacher> pageInfo = new PageInfo<>(1);//默认第一页
        pageInfo.setPageSize(1000);//可以查询到的老师数目
        try {
            pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        跳转之前将老师列表传出
        request.setAttribute("teachers", pageInfo.getList());
//        跳转
        request.getRequestDispatcher("page/course/add.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        String cName = request.getParameter("cName");
        String cTime = request.getParameter("cTime");
        //老师ID
        Integer tId = Integer.parseInt(request.getParameter("tId"));
//        新建课程对象
        Course course = new Course();
        course.setcName(cName);
        course.setcTime(cTime);
        //new一个Teacher对象，然后设置ID，然后再设置course对象的setTeacher关联 一下即可
        Teacher teacher = new Teacher();
        teacher.settId(tId);
        course.setTeacher(teacher);

        try {
            DaoFactory.getInstance().getCourseDao().add(course);
            //直接重定向到列表页面
            response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    课程列表
    private void list(HttpServletRequest request, HttpServletResponse response) {
        //当前页码
        Integer pageNo = getIntParameter(request, "pageNo");
        //查询条件
//      课程id
        Integer cId = getIntParameter(request, "cId");
//      课程名称
        String cName = request.getParameter("cName");
//      老师姓名
        String tName = request.getParameter("tName");
//      老师账号
        String userName = request.getParameter("userName");
//      选课时间
        String cTime = request.getParameter("cTime");
        Course course = new Course();
        course.setcId(cId);
        course.setcName(cName);
        course.setcTime(cTime);
        Teacher teacher = new Teacher();
        teacher.settName(tName);
        teacher.setUserName(userName);
        course.setTeacher(teacher);

        //构造了一个pageInfo对象
        PageInfo<Course> pageInfo = new PageInfo<>(pageNo);
        try {
            pageInfo = DaoFactory.getInstance().getCourseDao().list(course,pageInfo);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("pageInfo", pageInfo);
            //回写到页面
            request.setAttribute("course", course);
            request.getRequestDispatcher("page/course/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Integer getIntParameter(HttpServletRequest request,String name) {
        if(StringUtils.isNoneBlank(request.getParameter(name))) {
            return Integer.parseInt(request.getParameter(name));
        }else {
            return null;
        }
    }
}

