package servlet_package;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;
import db_package.DBmanager;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility_package.Functions;
import utility_package.Variabili;


@WebServlet(name = "ServletUpload", urlPatterns = {"/ServletUpload"})
public class ServletUpload extends HttpServlet {
    
    private String filePath;
    private String gname;
    private String gadmin;
    private String redirect;
    private boolean imgChange = false;
    DBmanager manager;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        //prendo i valori dei parametri
        gadmin = request.getParameter("gadmin");
        gname = request.getParameter("gname");  
        
        //setta il path
        if(request.getParameter(Variabili.OP).equals(Variabili.PROFILE_IMG)){
            filePath = Variabili.PATH_PROFILE_IMG + Functions.getUserName(request);
            redirect = "/WebForum/servletDatiUtente";
            imgChange = true;
        }
        else if(request.getParameter(Variabili.OP).equals(Variabili.TESTO)){
            filePath = Variabili.PATH_GROUPS + gname + "_" + gadmin;
            redirect = "/WebForum/servletVisualizzaPost?gname="+gname+"&gadmin="+gadmin;
        }
        else{
            // error 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        
        //creo il filepath se non esiste
        File f = new File(filePath);
        f.mkdirs();
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        
        String filename = "";
        try{    
            MultipartRequest multi = new MultipartRequest(request, f.getAbsolutePath(), 10*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
            String post = multi.getParameter("post");
            Enumeration files = multi.getFileNames();
            String name = (String)files.nextElement();
            filename = multi.getFilesystemName(name);
            if(imgChange){
                manager.setImageURL(Functions.getUserName(request), filename, request.getSession());
                imgChange = false;
            }
            if(post != null){
                String data = new Date().toString();
                post = post + " <a href=\"file/" +filename+ "?op=allegato&gname="+gname+"&gadmin="+gadmin+"\" target=\"_blank\">"+filename+"</a>";
                manager.aggiornapost(post, Functions.getUserName(request), gname, gadmin, data);
            }
        }
        catch (IOException lEx) {
            this.getServletContext().log(lEx, "error reading or saving file");
        } catch (SQLException ex) {
            Logger.getLogger(ServletUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect(redirect);
    }
}  