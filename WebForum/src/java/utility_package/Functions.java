package utility_package;

import java.io.Closeable;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class Functions {
    
    public static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getUserName(HttpServletRequest request){
        HttpSession session = request.getSession();
        return session.getAttribute("name").toString();
    }
    
    public static String getUserIMG(HttpServletRequest request){
        HttpSession session = request.getSession();
        return session.getAttribute("img").toString();
    }
    
    public static String getUserPassword(HttpServletRequest request){
        HttpSession session = request.getSession();
        return session.getAttribute("password").toString();
    }
    
}
