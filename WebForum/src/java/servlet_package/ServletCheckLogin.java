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
import javax.servlet.http.HttpSession;
import utility_package.Variabili;


public class ServletCheckLogin extends HttpServlet {

    DBmanager manager;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
    
        response.setContentType("text/html;charset=UTF-8");     //mi preparo per rispondere
        
        
        PrintWriter out = response.getWriter();
        String loginfail = "forumHTML/loginfail.html";
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");  //mi connetto al database
         
        //prendo i parametri dala richiesta
        String name = request.getParameter("name");   
        String password = request.getParameter("password"); 

        //se l'user esiste nel database e la password è corretta
        if(manager.authenticate(name, password)){
        
            System.out.println("autenticato");
            System.out.println(User.getName());
            System.out.println(User.getPassword());

            // richiesta sessione
            HttpSession session = request.getSession();
            String sessionUser;
            
            if(session.isNew()){
                session.setAttribute("name", User.getName());
                System.out.println("Prima connessione");
            }
            else{         
                sessionUser = session.getAttribute("name").toString();
                System.out.println("sessionID: " + session.getId());
                System.out.println("sessionUser: " + sessionUser);
                System.out.println("Gia connesso");
            }
            User.password = password;
            manager.getImageURL(User.name);
            
            // rimando alla Main Page
            response.sendRedirect("/WebForum/servletMainPage");
        }else{    //se i parametri nn ci sono
            System.out.println("Utente non esistente");
            Variabili.loginFail = true;
            // rimando alla pagina di Login Fail
            response.sendRedirect("/WebForum/servletLogin");
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
