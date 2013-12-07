package servlet_package;

import utility_package.User;
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
            out.println("<img src=\"file/"
                    + User.imageURL 
                    + "?op=img_profilo\""
                    + "\" alt=\"No image.\" class=\"img-rounded center-block\" style=\"width: 400px\"> "
                    + "</div>"
                    + "</div>"
                    + "<form action=\"servletUpload?op=img_profilo\" method=POST enctype=\"multipart/form-data\">"
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
