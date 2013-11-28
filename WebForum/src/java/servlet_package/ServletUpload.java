package servlet_package;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;
import db_package.DBmanager;
import db_package.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "ServletUpload", urlPatterns = {"/ServletUpload"})
public class ServletUpload extends HttpServlet {
    
    private String dirName;
    DBmanager manager;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // legge la uploadDir dai parametri della servlet
        dirName = config.getInitParameter("uploadDir");
        if (dirName == null) {
            throw new ServletException("Please supply uploadDir parameter");
        }
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        String filename = "";
        try{    
            MultipartRequest multi = new MultipartRequest(request, dirName, 10*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
            Enumeration files = multi.getFileNames();
            String name = (String)files.nextElement();
            filename = multi.getFilesystemName(name);
            manager.setImageURL(User.getName(), "forumIMG/"+filename);
        }
        catch (IOException lEx) {
            this.getServletContext().log(lEx, "error reading or saving file");
        } catch (SQLException ex) {
            Logger.getLogger(ServletUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/WebForum/servletDatiUtente");
    }
}  