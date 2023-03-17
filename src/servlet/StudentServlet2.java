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
import entity.Student;
import utils.MD5;
import utils.PathUtils;

@WebServlet("/student2")
public class StudentServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //无论是doGet还是doPost都转向doPost
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        if("list".equals(method)) {
            //执行列表查询
            this.list(req, resp);
        }else if("add".equals(method)) {
            //执行添加方法
            try {
                this.add(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("edit".equals(method)) {
            //先查询出结果再跳转到具体页面
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
        }

    }
//删除方法
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            DaoFactory.getInstance().getStudentDao().delete(Integer.parseInt(id));
            //直接重定向到列表页面
            response.sendRedirect(PathUtils.getBasePath(request)+"student?method=list");
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
//    根据id查找学生对象
    private void findById(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        try {
            Student student = DaoFactory.getInstance().getStudentDao().findById(Integer.parseInt(id));
            req.setAttribute("student", student);
//            查询成功再跳转到修改页面
            req.getRequestDispatcher("page/student/update.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加学生方法
    private void add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String stuNo = req.getParameter("stuNo");
        String stuName = req.getParameter("stuName");
        String stuPwd = req.getParameter("stuPwd");
        Student student = new Student();
        student.setStdName(stuName);
        student.setStdNo(stuNo);
        student.setStdPwd(MD5.encrypByMd5(MD5.encrypByMd5(stuPwd)));
        try {
            DaoFactory.getInstance().getStudentDao().add(student);
            //直接重定向到列表页面
            resp.sendRedirect(PathUtils.getBasePath(req)+"student?method=list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //分页功能实现
    private void list(HttpServletRequest request, HttpServletResponse response) {
        //当前页码
        Integer nowPage = getIntParameter(request, "pageNo");
        //每一页的数据条数
        int pageSize = 10;
        //调用integer封装类，当nowPage参数为空的时候，则默认为第1页
        if(nowPage == null) {
            nowPage = 1;
        }
        //数据记录的条数
        Long total = 0L;
        try {
            total = DaoFactory.getInstance().getStudentDao().count(null);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //当它整除的时候，以及记录数不到十条时，进行一个++，total是总记录数
        Long allPage = total/pageSize;
        if(total%pageSize!=0 || allPage == 0) {
            allPage++;
        }
        //下一页
        Integer next = 0;
        if(next<allPage) {
            next = nowPage+1;
        }else {
            //已经是最后一页时，让他一直等于
            next = nowPage;
        }

        //上一页
        Integer pre = 0;
        if(nowPage>1) {
            //已经是第一页时，让他一直等于
            pre = nowPage-1;
        }else {
            pre = nowPage;
        }
        //是否是第一页判断
        boolean flagFirst = false;
        if(nowPage>1) {
            flagFirst = false;
        }else {
            flagFirst = true;
        }
        //是否是最后一页判断
        boolean flagLast = false;
        if(nowPage<allPage) {
            flagLast = false;
        }else {
            flagLast = true;
        }
        try {
            List<Student> list = null;//DaoFactory.getInstance().getStudentDao().list(null,pageNo,pageSize);
            request.setAttribute("list", list);
            request.setAttribute("pageSize", pageSize);

            request.setAttribute("isFirstPage", flagFirst);//是否第一页
            request.setAttribute("isLastPage", flagLast);//是否最后页

            request.setAttribute("totalCount", total);
            request.setAttribute("totalPage",allPage);//总页数，也就是尾页；首页就是1
            request.setAttribute("prePage", pre);
            request.setAttribute("nextPage", next);
            request.setAttribute("pageNo", nowPage);
            request.getRequestDispatcher("page/student/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //封装一个类，使为空的时候，字符串不转换成数字，防止页码为空的时候转换成数字
    public Integer getIntParameter(HttpServletRequest req,String str) {
        if(req.getParameter(str) != null) {
            return Integer.parseInt(req.getParameter(str));
        }else {
            return null;
        }
    }



}
