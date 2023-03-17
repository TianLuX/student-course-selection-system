package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import dao.DaoFactory;
import entity.Sc;
import entity.Course;
import entity.Student;
import utils.MD5;
import utils.PageInfo;
import utils.PathUtils;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //无论是doGet还是doPost都转向doPost
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //通过action属性传入的method参数进行判断调用什么方法
        String method = req.getParameter("method");
        if("list".equals(method)) {
            //执行列表查询
            try {
                this.list(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("add".equals(method)) {
            //执行添加方法
            try {
                this.add(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("edit".equals(method)) {
            this.findById(req, resp);
        }else if("editsubmit".equals(method)) {
            //执行更新方法
            try {
                this.editsubmit(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("delete".equals(method)) {
            //执行删除方法
            this.delete(req, resp);
        }else if("detail".equals(method)) {
            this.detail(req, resp);
        }else if("editsubmit1".equals(method)){
            try {
                this.editsubmit1(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void editsubmit1(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer stdId = Integer.parseInt(req.getParameter("stuId"));
        String stdNo = req.getParameter("stuNo");
        String stdName = req.getParameter("stuName");
        String stdPhone = req.getParameter("stdPhone");
        String stdEmail = req.getParameter("stdEmail");
        String stdAddress = req.getParameter("stdAddress");
        Student std = new Student();
        std.setStdName(stdName);
        std.setStdNo(stdNo);
        std.setStdId(stdId);
        std.setStdPhone(stdPhone);
        std.setStdEmail(stdEmail);
        std.setStdAddress(stdAddress);
        try {
            DaoFactory.getInstance().getStudentDao().update1(std);
            //直接重定向到列表页面
            resp.sendRedirect(PathUtils.getBasePath(req)+"student?method=list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //列表查询方法
    private void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //当前页码
        Integer pageNo = getIntParameter(req, "pageNo");
        Integer stdId = getIntParameter(req, "stdId");
        String stdName = req.getParameter("stdName");
        String stdNo = req.getParameter("stdNo");

        Student std = new Student();
        std.setStdId(stdId);
        std.setStdName(stdName);
        std.setStdNo(stdNo);

        //构造了一个pageInfo对象
        PageInfo<Student> pageInfo = new PageInfo<>(pageNo);
        try {
            pageInfo = DaoFactory.getInstance().getStudentDao().list(std,pageInfo);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            req.setAttribute("pageInfo", pageInfo);
            //回写到页面
            req.setAttribute("student", std);
            req.getRequestDispatcher("page/student/list.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查找方法
    private void findById(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        try {
            Student std = DaoFactory.getInstance().getStudentDao().findById(Integer.parseInt(id));
            req.setAttribute("student", std);
            req.getRequestDispatcher("page/student/update.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //修改提交
    private void editsubmit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer stdId = Integer.parseInt(req.getParameter("stuId"));
        String stdNo = req.getParameter("stuNo");
        String stdName = req.getParameter("stuName");
        Student std = new Student();
        std.setStdName(stdName);
        std.setStdNo(stdNo);
        std.setStdId(stdId);
        try {
            DaoFactory.getInstance().getStudentDao().update(std);
            //直接重定向到列表页面
            resp.sendRedirect(PathUtils.getBasePath(req)+"student?method=list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //删除
    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        try {
            DaoFactory.getInstance().getStudentDao().delete(Integer.parseInt(id));
            //直接重定向到列表页面
            resp.sendRedirect(PathUtils.getBasePath(req)+"student?method=list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void detail(HttpServletRequest request, HttpServletResponse response) {
        Student entity = (Student)request.getSession().getAttribute("user");
        try {
            Student student = DaoFactory.getInstance().getStudentDao().findById(entity.getStdId());
            List<Sc> list = DaoFactory.getInstance().getScDao().listByStuId(entity.getStdId());
            request.setAttribute("student", student);
            for(Sc sc:list) {
                Course c  = DaoFactory.getInstance().getCourseDao().findById(sc.getcId());
                sc.setcName(c.getcName());
            }
            request.setAttribute("list", list);
            request.getRequestDispatcher("page/student/detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //管理员新增学生方法
    private void add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String stdNo = req.getParameter("stuNo");
        String stdName = req.getParameter("stuName");
        String stdPwd = req.getParameter("stuPwd");
        Student std = new Student();
        std.setStdName(stdName);
        std.setStdNo(stdNo);
        std.setStdPwd(MD5.encrypByMd5(MD5.encrypByMd5(stdPwd)));
        try {
            DaoFactory.getInstance().getStudentDao().add(std);
            //直接重新定向到列表页面
            //PathUtils.getBasePath(req)防止路径定向错误
            resp.sendRedirect(PathUtils.getBasePath(req)+"student?method=list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
