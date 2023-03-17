package utils;


import javax.servlet.http.HttpServletRequest;

public class PathUtils {

    public static String getBasePath(HttpServletRequest req) {
        String path = req.getContextPath();
        String basePath = req.getScheme() +"://" + req.getServerName() +":"+req.getServerPort()+path+"/";
        return basePath;
    }

}

