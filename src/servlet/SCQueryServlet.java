package servlet;

import dao.DaoFactory;
import entity.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/scquery")
//成绩排序查询
public class SCQueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        if ("query_range".equals(method)) {
//            query_range成绩区间查询
            try {
                this.query_range(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("query_jgl".equals(method)) {
//            成绩及格率查询
            this.query_jgl(request, response);
        } else if ("query_teacher".equals(method)) {
//            老师所教科目的统计情况
            this.query_teacher(request, response);
        }
    }
    //            老师所教科目的统计情况，及格率和分数区间合二为一
    public void query_teacher(HttpServletRequest request, HttpServletResponse response){
//        获取老师对象，便于后期获取tid
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        try {
//            list1返回分数区间
            List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_rangeByTid(teacher.gettId());
            request.setAttribute("list1", list);
//            list2返回及格率
            List<Map<String, Object>> list2 = DaoFactory.getInstance().getScDao().query_jglByTid(teacher.gettId());
            request.setAttribute("list2", list2);
            request.getRequestDispatcher("page/sc/query_teacher.jsp").forward(request, response);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
//    区间排名方法
    public void query_range(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_range();
//            将信息返回给前端list
            request.setAttribute("list", list);
//            界面跳转，统计信息放入score下的query_range.jsp中
            request.getRequestDispatcher("page/sc/query_range.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//及格率查询方法
    public void query_jgl(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_jgl();
            request.setAttribute("list", list);
            request.getRequestDispatcher("page/sc/query_jgl.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
