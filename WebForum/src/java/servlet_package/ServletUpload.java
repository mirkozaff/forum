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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletContext;


@WebServlet(name = "ServletUpload", urlPatterns = {"/ServletUpload"})
public class ServletUpload extends HttpServlet {
    
    private String dirName;
    
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
        
        String filename = "";
        PrintWriter out = response.getWriter();
        String datiUtente = "forumHTML/datiUtente.html";
        response.setContentType("text/html;charset=UTF-8"); 
        try{    
        MultipartRequest multi = new MultipartRequest(request, dirName, 10*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
        Enumeration files = multi.getFileNames();
        String name = (String)files.nextElement();
        filename = multi.getFilesystemName(name);
        }
        catch (IOException lEx) {
            this.getServletContext().log(lEx, "error reading or saving file");
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
            out.println("src=\"forumIMG/img.jpg\" alt=\"No image.\" class=\"img-rounded center-block\">"
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