package servlet_package;

import db_package.User;
import db_package.DBmanager;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ServletCheckLogin extends HttpServlet {

    User user;
    DBmanager manager;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
    
        response.setContentType("text/html;charset=UTF-8");     //mi preparo per rispondere
        PrintWriter out = response.getWriter();
        String loginfail = "loginfail.html";
        String loginsuccess = "mainPage.html";
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");  //mi connetto al database
         
        String name = request.getParameter("name");   //prendo i parametri dala richiesta
        String password = request.getParameter("password"); 
        
        user = manager.authenticate(name, password);   //vedo se i parametri sono corretti
        System.out.println("autenticato");
        
        HttpSession session = request.getSession();
        if (session.isNew()){
            
        }
        
        
        if(user != null){ //se i parametri ci sono nel database
        try {
           ServletContext context = getServletContext();
           InputStream inp = context.getResourceAsStream(loginsuccess);
           if (inp != null) {
               InputStreamReader isr = new InputStreamReader(inp);
               BufferedReader reader = new BufferedReader(isr);
               String text = "";
               while ((text = reader.readLine()) != null) {
                   out.println(text);
               }
           }
        }finally {
            out.close();
        }  
        }else{    //se i parametri nn ci sono
        try {
           ServletContext context = getServletContext();
           InputStream inp = context.getResourceAsStream(loginfail);
           if (inp != null) {
               InputStreamReader isr = new InputStreamReader(inp);
               BufferedReader reader = new BufferedReader(isr);
               String text = "";
               while ((text = reader.readLine()) != null) {
                   out.println(text);
               }
           }
        }finally {
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
            Logger.getLogger(ServletCheckLogin.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletCheckLogin.class.getName()).log(Level.SEVERE, null, ex);
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
