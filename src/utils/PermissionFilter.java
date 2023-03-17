package utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//允许访问后缀名为.otf,.eot,.svg,.ttf,.woff,.woff2的文件
//过滤器
//value、urlPatterns、servletNames 三者必需至少包含一个，且 value 和 urlPatterns不能共存
//配置多个用/*
@WebFilter(urlPatterns= {"/*"},initParams= {
        //排除哪些不拦截exclude
        @WebInitParam(name="exclude",value="/login.jsp,/login,/noprivilige.jsp,.css,.png,.jpg,.js,.otf,.eot,.svg,.ttf,.woff,.woff2")
})
//权限过滤器
public class PermissionFilter implements Filter {

    public static String excludeStr ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeStr = filterConfig.getInitParameter("exclude");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest httpserreq = (HttpServletRequest)req;
        HttpServletResponse httpserresp = (HttpServletResponse)resp;
        Object user = httpserreq.getSession().getAttribute("user");//得到对象信息
        String requestURI = httpserreq.getRequestURI();
//        httpserreq.getContextPath()得到项目名
        if(isExist(requestURI) || requestURI.equals(httpserreq.getContextPath()+"/")) {
            //如果存在则直接放行，或者是项目名结束的匹配情况也直接放行
            filterchain.doFilter(httpserreq, httpserresp);
        }else {
            if(user != null) {
                //存在对象，直接放行
                filterchain.doFilter(httpserreq, httpserresp);
            }else {
                //没有访问权限
                //直接尝试登录index时会显示没有权限
                httpserresp.sendRedirect("noprivilige.jsp");
            }
        }
    }

    public static boolean isExist(String uri) {
        //最后url的结尾是否exclude匹配，匹配直接放行
        String[] array = excludeStr.split(",");
        boolean myFlag = false;
        //变成数组一个个去判断
        for (String str : array) {
            if(uri.endsWith(str)) {
                myFlag = true;
            }
        }
        return myFlag;
    }


}
