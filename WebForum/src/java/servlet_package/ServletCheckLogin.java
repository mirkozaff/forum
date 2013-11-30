package servlet_package;

import utility_package.User;
import db_package.DBmanager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utility_package.Variabili;


public class ServletCheckLogin extends HttpServlet {

    DBmanager manager;
    Cookie lastAccessedTime = new Cookie("ultimoAccesso", null);
    String name, password;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
    
    this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");  //mi connetto al database
        
        // richiesta sessione
        HttpSession session = request.getSession();
        
        if(session.isNew()){
            
            //prendo i parametri dala richiesta
            name = request.getParameter("name");   
            password = request.getParameter("password"); 
            session.setAttribute("name", name);
            session.setAttribute("password", password);
        }
        else{
            name = session.getAttribute("name").toString();
            password = session.getAttribute("password").toString();
            lastAccessedTime.setValue(Long.toString(session.getLastAccessedTime()));
        }
        
        //se l'user esiste nel database e la password è corretta
        if(manager.authenticate(name, password)){
            
            System.out.println("autenticato");
            System.out.println("Prima connessione");
            System.out.println(User.getName());
            System.out.println(User.getPassword());
            
            //ricavo url immagine
            manager.getImageURL(User.getName());

            //aggiungo il cookie della data ultimo accesso
            lastAccessedTime.setMaxAge(-1);
            response.addCookie(lastAccessedTime);
            
            // rimando alla Main Page
            response.sendRedirect("/WebForum/servletMainPage");
        }
        else{    
            //se i parametri nn ci sono
            System.out.println("Utente non esistente");
            //invalido la sessione e mando segnale di login fail
            Variabili.loginFail = true;
            session.invalidate();
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
