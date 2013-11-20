/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet_package;

import db_package.User;
import db_package.DBmanager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giovanni
 */
public class servlet extends HttpServlet {

    User user;
    DBmanager manager;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
    
        response.setContentType("text/html;charset=UTF-8");     //mi preparo per rispondere
        PrintWriter out = response.getWriter();
        
        this.manager = new DBmanager();  //mi connetto al database
         
        String name = request.getParameter("name");   //prendo i parametri dala richiesta
        String password = request.getParameter("password"); 
        
        user = manager.authenticate(name, password);   //vedo se i parametri sono corretti
        System.out.println("autenticato");
        
        if(user != null){ //se i parametri ci sono nel database
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet servlet at " + user.getName()+ " " + user.getPassword() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
        }else{    //se i parametri nn ci sono
          try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet servlet at :  chi minchia è sto tizio? </h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }  
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
            Logger.getLogger(servlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(servlet.class.getName()).log(Level.SEVERE, null, ex);
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
