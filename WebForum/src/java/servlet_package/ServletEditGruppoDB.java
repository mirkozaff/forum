/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet_package;

import db_package.DBmanager;
import db_package.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giovanni
 */
public class ServletEditGruppoDB extends HttpServlet {
DBmanager manager;
User user;
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
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");  //mi connetto al database
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();        
        String nomegruppo = request.getParameter("nomegruppo");
        String[] utentiNuovoGruppo = request.getParameterValues("utente");
        
        this.user = (User)super.getServletContext().getAttribute("user");
        manager.aggiornalistagruppi(nomegruppo,user.getName());
       
        RequestDispatcher rd = request.getRequestDispatcher("servletListaGruppi");
        rd.forward(request, response);
        
  /*     try {
            
            out.println("<!DOCTYPE html>"
                    + "<html><body>"
                    + "<h1> il nome del gruppo è</h1>"
                    + nomegruppo
                    + "<br><h1> gli utenti che hai invitato sono </h1>"
                    + "<ul>");
            for(int i=0;i<utentiNuovoGruppo.length;i++){
                out.println("<li>"+utentiNuovoGruppo[i]+"</li>");
            }
            out.println("</ul>"
                    + "</body></html>");
            
        } finally {
            out.close();
        } */
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
        Logger.getLogger(ServletEditGruppoDB.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(ServletEditGruppoDB.class.getName()).log(Level.SEVERE, null, ex);
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
