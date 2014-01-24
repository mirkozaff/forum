package servlet_package;

import db_package.DBmanager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giovanni
 */
public class ServletListaGruppi extends HttpServlet {
    private DBmanager manager;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String filename = "forumHTML/listagruppi.html";
        String name;
        ArrayList<String> listagruppi = new <String>ArrayList();
        ArrayList<String> listaadmin = new <String>ArrayList();
        PrintWriter out = response.getWriter();
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        name = (String)request.getSession(false).getAttribute("name");
        
        if(name!=null){
        manager.listagruppi(name, listagruppi, listaadmin);
        }
        
       try {
           ServletContext context = getServletContext();
           InputStream inp = context.getResourceAsStream(filename);
           if (inp != null) {
               InputStreamReader isr = new InputStreamReader(inp);
               BufferedReader reader = new BufferedReader(isr);
               String text = "";
               while ((text = reader.readLine()) != null) {
                   out.println(text);
               }
           }
            for(int i=0;i<listagruppi.size();i++){
            out.println("<tr><td>"+listagruppi.get(i)+"</td>"
                    + "<td>"+listaadmin.get(i) +"</td>"
                    + "<td><form action=\"servletVisualizzaPost\" method=POST>"
                    + "<input class=\"btn btn-lg btn-success\" type=\"submit\" value=\"entra\">"
                    + "<input type=\"hidden\" name=\"gname\" value=\""+listagruppi.get(i)+"\">"
                    + "<input type=\"hidden\" name=\"gadmin\" value=\""+listaadmin.get(i)+"\">"
                    + "</form></td>"
                    + "</tr>");}
            out.println("</table></div></div><div class=\"col-md-4\"></div></div>"
                        + "<script src=\"bootstrapJS/jquery.js\"></script>"
                        + "<script src=\"bootstrapJS/bootstrap.min.js\"></script>"
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
            Logger.getLogger(ServletListaGruppi.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletListaGruppi.class.getName()).log(Level.SEVERE, null, ex);
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
