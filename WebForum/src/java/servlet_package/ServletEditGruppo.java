/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

// questa servlet invia l'html che mostra il form per creare un gruppo
public class ServletEditGruppo extends HttpServlet {
    private DBmanager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        ArrayList<String> listanomi = new <String>ArrayList();
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        manager.listanomi(listanomi);
        
        response.setContentType("text/html;charset=UTF-8");       
        String filename = "forumHTML/creagruppo.html";
        PrintWriter out = response.getWriter();
        
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
           out.println("<form action=\"servletEditGruppoDB\" method=POST>");
           out.println("<h1>nome gruppo </h1>"
                   + "<input type=\"text\" name=\"nomegruppo\"/><br>");
           out.println("<h1>chi vuoi invitare? </h1>");
           for(int i=0;i<listanomi.size();i++){
               out.println("<input type=\"checkbox\" name=\"utente\" value=\""+listanomi.get(i)+"\">"+listanomi.get(i)+"<br>");
           }
           out.println("<input class=\"btn btn-lg btn-success\" type=\"submit\" value=\"Upload\">"
                   + "</form>");
            out.println("</td></tr></table></div><div class=\"col-md-4\"></div></div>"
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
            Logger.getLogger(ServletEditGruppo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletEditGruppo.class.getName()).log(Level.SEVERE, null, ex);
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
