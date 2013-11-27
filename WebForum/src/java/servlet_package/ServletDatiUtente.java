package servlet_package;

import db_package.DBmanager;
import db_package.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ServletDatiUtente", urlPatterns = {"/ServletDatiUtente"})
public class ServletDatiUtente extends HttpServlet {

    DBmanager manager;
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String datiUtente = "forumHTML/datiUtente.html";
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        HttpSession session = request.getSession();
        User.name = session.getAttribute("name").toString();
        //manager.setImageURL(user.getName(), "forumIMG/"+filename, user); 
        
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
        processRequest(request, response);
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
        processRequest(request, response);
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
