package servlet_package;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility_package.Functions;


@WebServlet(name = "ServletDatiUtente", urlPatterns = {"/ServletDatiUtente"})
public class ServletDatiUtente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String datiUtente = "forumHTML/datiUtente.html";
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
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
            out.println("<div class=\"contenitore-azzurro\">"
                    +"<img src=\"file/"
                    + Functions.getUserIMG(request)
                    + "?op=img_profilo\""
                    + "\" alt=\"no image.\" onerror=\"src=\'/WebForum/forumIMG/default-no-profile-pic.jpg'\" class=\"img-rounded center-block\" style=\"width: 400px\">"
                    + "<br>"
                    + "<div class=\"centra\">"
                    + "<form class=\"form-inline\" action=\"servletUpload?op=img_profilo\" method=POST enctype=\"multipart/form-data\">"
                    + "<div class=\"form-group\">"
                    + "<input type=file name=file1>"
                    + "</div>"
                    + "<div class=\"form-group\">"
                    + "<input class=\"btn btn-lg btn-success\" type=\"submit\" value=\"Upload\">"
                    + "</div>"
                    + "</form>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "<div class=\"col-md-4\"></div>"
                    + "</body>"
                    + "</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletDatiUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletDatiUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
