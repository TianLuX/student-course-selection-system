package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import dao.AdminDao;
import dao.DaoFactory;
import dao.StudentDao;
import dao.TeacherDao;
import entity.Admin;
import entity.Student;
import entity.Teacher;
import utils.MD5;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");//用户名称
        String password = req.getParameter("password");//密码
        String position = req.getParameter("position");//身份

        //判断个人信息不能为空
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(position)) {
            req.setAttribute("error", "录入信息不能为空!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        if(StringUtils.isNotBlank(position)) {
            try {
                if("0".equals(position)) {
                    //学生登录验证
                    Student std = DaoFactory.getInstance().getStudentDao().login(userName, MD5.encrypByMd5(MD5.encrypByMd5(password)));
                    if(std != null) {
                        session.setAttribute("user", std);
                        session.setAttribute("type", position);
                        resp.sendRedirect("index.jsp");
                    }else {
                        req.setAttribute("error", "用户名或密码错误!");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                    }
                }else if("1".equals(position)) {
                    //老师登录验证
                    Teacher tech = DaoFactory.getInstance().getTeacherDao().login(userName, MD5.encrypByMd5(MD5.encrypByMd5(password)));
                    if(tech != null) {
                        session.setAttribute("user", tech);
                        session.setAttribute("type", position);
                        resp.sendRedirect("index.jsp");
                    }else {
                        req.setAttribute("error", "用户名或密码错误!");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                    }
                }else {
                    //管理员登录验证
                    Admin admin = new Admin();
                    admin.setUserName(userName);
                    admin.setPwd(MD5.encrypByMd5(MD5.encrypByMd5(password)));
                    Admin entity = DaoFactory.getInstance().getAdminDao().login(admin);
                    if(entity != null) {
                        //执行跳转
                        session.setAttribute("user", entity);
                        session.setAttribute("type", position);
                        resp.sendRedirect("index.jsp");
                    }else {
                        //用户或密码错误！！
                        req.setAttribute("error", "用户名或密码错误!");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {

        }
    }

}
