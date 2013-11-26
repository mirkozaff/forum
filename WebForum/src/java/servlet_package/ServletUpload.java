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
import java.io.File;


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
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        out.println("Demo Upload Servlet using MultipartRequest");
        out.println();
        
        try {
            // Use an advanced form of the constructor that specifies a character
            // encoding of the request (not of the file contents) and a file
            // rename policy.
            MultipartRequest multi = new MultipartRequest(request, dirName, 10*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
            out.println("PARAMS:");
            Enumeration params = multi.getParameterNames();
            
            while (params.hasMoreElements()) {
                String name = (String)params.nextElement();
                String value = multi.getParameter(name);
                out.println(name + "=" + value);
            }
            
            out.println();
            out.println("FILES:");
            Enumeration files = multi.getFileNames();
            
            while (files.hasMoreElements()) {
                String name = (String)files.nextElement();
                String filename = multi.getFilesystemName(name);
                String originalFilename = multi.getOriginalFileName(name);
                String type = multi.getContentType(name);
                File f = multi.getFile(name);
                
                out.println("name: " + name);
                out.println("filename: " + filename);
                out.println("originalFilename: " + originalFilename);
                out.println("type: " + type);
                
                if (f != null) {
                    out.println("f.toString(): " + f.toString());
                    out.println("f.getName(): " + f.getName());
                    out.println("f.exists(): " + f.exists());
                    out.println("f.length(): " + f.length());
                }
                out.println();
            }
        } 
        catch (IOException lEx) {
            this.getServletContext().log(lEx, "error reading or saving file");
        } 
    }
}  