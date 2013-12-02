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
import utility_package.Post;
import utility_package.User;

/**
 *
 * @author giovanni
 */
public class ServletVisualizzaPost extends HttpServlet {
    private DBmanager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
         
        //ricavo il nome e l'autore del gruppo a cui vuole accedere l'utente
        String gname =request.getParameter("gname");
        String gadmin=request.getParameter("gadmin");
        
        ArrayList<Post> listapost = new ArrayList<Post>();
       
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        manager.getpost(gname, gadmin, listapost);
        
        response.setContentType("text/html;charset=UTF-8");
        String filename="forumHTML/visualizzaPost.html";
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
           out.println("<input type=\"hidden\" name=\"gname\" value=\""+gname+"\">");
           out.println("<input type=\"hidden\" name=\"gadmin\" value=\""+gadmin+"\">");
           out.println("</form></div></div></div></div></div><div class=\"navbar-default divcentrato\">");
           out.println("<p align=\"center\" style=\"font-size: 250%\">"+gname+"</p>");
           if(gadmin.toString().equals(User.getName())){
           out.println("<div style=\"text-align: center\">"
                   + "<form action=\"servletEditGruppo\" method=POST>"
                   + "<button type=\"submit\" class=\"btn btn-success navbar-btn\">edita gruppo</button>&nbsp;"
                   + "<input type=\"hidden\" name=\"gname\" value=\""+gname+"\">"
                   + "<input type=\"hidden\" name=\"gadmin\" value=\""+gadmin+"\">"
                   + "<button type=\"button\" class=\"btn btn-success navbar-btn\">pdf del gruppo</button>"
                   + "</form>"
                   + "</div>");
           }
           if(listapost.isEmpty()){
               out.println("<p align=\"center\">non ci sono post in questo gruppo</p>");
           }
           for(int i=0; i<listapost.size();i++){
           out.println("<div class=\"container\">"
                   + "<div class=\"row\">"
                   + "<div class=\"col-md-2\">"
                   + "<div class=\"row\">"
                   + "<div>"
                   + "<h2>"+listapost.get(i).getUtente_postante()
                   +"</h2>"
                   + "</div>"
                   + "</div>"
                   + "<div class=\"row\">"
                   + "<div>"
                   + "<img src=\"forumIMG/serengetipark4.jpg\" alt=\"cagna\" class=\"img-rounded\" style=\"width: 100px\">"
                   + "</div>"
                   + "</div>"
                   + "<div class=\"row\">"
                   + "<div>"+listapost.get(i).getData()+"</div>"
                   + "</div>"
                   + "</div>"
                   + "<div style=\"margin-top: 20px\">"
                   + "<textarea name=\"post\" cols=\"150\" rows=\"6\" maxlength=\"10000\" style=\"resize: none\" readonly>"+listapost.get(i).getTesto()+"</textarea>"
                   + "</div>"
                   + "</div>"
                   + "</div>");    
           }
           out.println("</div><script src=\"bootstrapJS/modal.js\"></script>\n"
                   + "<script src=\"bootstrapJS/jquery.js\"></script>\n"
                   + "<script src=\"bootstrapJS/bootstrap.min.js\"></script>\n"
                   + "</body>\n"
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
            Logger.getLogger(ServletVisualizzaPost.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletVisualizzaPost.class.getName()).log(Level.SEVERE, null, ex);
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
