package servlet;

import dao.DaoFactory;
import entity.*;
import org.apache.commons.lang3.StringUtils;
import utils.PageInfo;
import utils.PathUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/sc")
public class SCServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if("select".equals(method)) {
//            选课列表信息
            this.select(request, response);
        }else if("submit".equals(method)) {
//            选课提交
            this.submit(request, response);
            this.submit1(request, response);
        }else if("tc".equals(method)) {
//            老师所教的课程
            this.teacher_course(request, response);
        }else if("cs".equals(method)) {
//            老师查看课程下所选的学生
            this.course_student(request, response);
        }else if("cs1".equals(method)){
            this.course_student1(request, response);
        }else if("score_submit".equals(method)) {
//            教师评分
            this.score_submit(request, response);
        }else if("time".equals(method)){
            try {
                this.time(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void time(HttpServletRequest request,HttpServletResponse response) throws Exception {
         Time time = DaoFactory.getInstance().getTimeDao().findNew();
         request.setAttribute("start",time.getStart());
         request.setAttribute("end",time.getEnd());
    }
    //老师评分提交
    private void score_submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        每次评分对应一门课
        Integer cId = getIntParameter(request, "cId");
//        用数组保存学生
        String[] stuIdArr = request.getParameterValues("stuId");
//        用数组保存分数
        String[] scoreArr = request.getParameterValues("score");

        String id=request.getParameter("scid");
        if(id.equals("0")){

            try {
//            同select中的信息返回方法

                DaoFactory.getInstance().getScDao().update1(stuIdArr, scoreArr, cId);
                response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=cs1&cId="+cId+"&msg=1");
            } catch (Exception e) {
                response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=cs1&cId="+cId+"&msg=0");
                e.printStackTrace();
            }
        }else if(id.equals("1")){

            try {
//            同select中的信息返回方法

                DaoFactory.getInstance().getScDao().update(stuIdArr, scoreArr, cId);
                response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=cs&cId="+cId+"&msg=1");
            } catch (Exception e) {
                response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=cs&cId="+cId+"&msg=0");
                e.printStackTrace();
            }
        }

    }
    //cs，课程下的学生，即选课的学生查询
    private void course_student(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端cid
        Integer cId = getIntParameter(request, "cId");
        //获取学生列表
        try {
            List<Student> list = DaoFactory.getInstance().getScDao().listStudentByCId1(cId);
            request.setAttribute("list", list);
            request.setAttribute("cId", cId);
            request.getRequestDispatcher("page/sc/course_student.jsp").forward(request, response);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void course_student1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端cid
        Integer cId = getIntParameter(request, "cId");
        //获取学生列表
        try {
            List<Student> list = DaoFactory.getInstance().getScDao().listStudentByCId1(cId);
            request.setAttribute("list", list);
            request.setAttribute("cId", cId);
            request.getRequestDispatcher("page/sc/course_student.jsp").forward(request, response);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void teacher_course(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取老师对应的session中对象
        Teacher teacher =(Teacher)request.getSession().getAttribute("user");
//        通过tid查询所教课程的信息，不要通过老师的姓名
        Integer pageNo = getIntParameter(request, "pageNo");
//        建立课程对象，设置课程里的老师
        Course course = new Course();
        course.setTeacher(teacher);
//        通过pageinfo分页来返回信息
        PageInfo<Course> pageInfo = new PageInfo<>(pageNo);
        //查询所教课程列表
        try {
            pageInfo = DaoFactory.getInstance().getCourseDao().list(course, pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("pageInfo", pageInfo);
        //将信息返回到teacher_course.jsp页面
        request.getRequestDispatcher("page/sc/teacher_course.jsp").forward(request, response);
    }


    //用于保存选课的信息
    private void submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        以数组的形式接收数据
        String[] cIds = request.getParameterValues("cId");
        List<Integer> cIdArray = new ArrayList<Integer>();
        for (String string : cIds) {
            cIdArray.add(Integer.parseInt(string));
        }
//        获取学生的信息，新建学生对象
        Student student = (Student)request.getSession().getAttribute("user");
        try {
            int[] arr = DaoFactory.getInstance().getScDao().add(cIdArray, student.getStdId());
//            数组长度为0，返回选课失败，数组长度为1，返回选课成功
            if(arr.length == 0) {
                response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=select&msg=0");
            }else {
                response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=select&msg=1");
            }
        } catch (SQLException e) {
            response.sendRedirect(PathUtils.getBasePath(request)+"sc?method=select&msg=0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //用于保存选课的信息
    private void submit1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        以数组的形式接收数据
        String[] cIds = request.getParameterValues("cId");
        List<Integer> cIdArray = new ArrayList<Integer>();
        for (String string : cIds) {
            cIdArray.add(Integer.parseInt(string));
        }
//        获取学生的信息，新建学生对象
        Student student = (Student)request.getSession().getAttribute("user");
        try {
            int[] arr = DaoFactory.getInstance().getScDao().add1(cIdArray, student.getStdId());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //选课，使用分页技术，读取所有课程
    private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageInfo<Course> info = new PageInfo<>(1);
        info.setPageSize(1000);
        try {
//            使用courseDao中的list查询所有课程
            info = DaoFactory.getInstance().getCourseDao().list(null, info);
            //获取登录的学生信息
            Student student = (Student)request.getSession().getAttribute("user");
            //获取已选课的课程的id，防止重复选课，为了回写，使已经选了的课checked为true
            List<Sc> list = DaoFactory.getInstance().getScDao().listByStuId(student.getStdId());
            request.setAttribute("scs",list);
            request.setAttribute("courses", info.getList());
            request.setAttribute("stuid", student.getStdId());
            request.getRequestDispatcher("page/sc/select.jsp").forward(request, response);
        }catch (Exception e) {
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

