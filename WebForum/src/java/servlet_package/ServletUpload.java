package servlet_package;

import java.io.IOException;
import java.io.PrintWriter;
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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


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
        HttpSession session = request.getSession();
        User.name = session.getAttribute("name").toString();
        String filename = "";
        PrintWriter out = response.getWriter();
        String datiUtente = "forumHTML/datiUtente.html";
        response.setContentType("text/html;charset=UTF-8"); 
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
        try {
            ServletContext context = getServletContext();
            InputStream inp = context.getResourceAsStream(datiUtente);
            if (inp != null) {
               InputStreamReader isr = new InputStreamReader(inp);
               BufferedReader reader = new BufferedReader(isr);
               String text = "";
               while ((text = reader.readLine()) != null) {
                   out.println(text);
               }
            }

            out.println("src=\"forumIMG/"+ filename + "\" alt=\"No image.\" class=\"img-rounded center-block\">"
                    + "</div>"
                    + "</div>"
                    + "<form action=\"servletUpload\" method=POST enctype=\"multipart/form-data\">"
                    + "<input class=\"btn btn-lg btn-success\" type=\"submit\" value=\"Upload\">"
                    + "<input class = \"btn btn-lg btn-success\" type=file name=file1>"
                    + "</form>"
                    + "</div>"
                    + "</body>"
                    + "</html>");
        } finally {
            out.close();
        }
    }
}  