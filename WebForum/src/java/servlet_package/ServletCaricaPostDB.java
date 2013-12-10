package servlet_package;

import db_package.DBmanager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility_package.Functions;

//questa servlet viene invocata quando l'utente scrive un nuovo post. si occupa di aggiornare il db e richiamare la servletVisualizzaPost
public class ServletCaricaPostDB extends HttpServlet {
    private DBmanager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        
        String gname=request.getParameter("gname");
        String gadmin=request.getParameter("gadmin");
        String post=request.getParameter("post");
        String filepost=request.getParameter("filepost");
        String bottone=request.getParameter("bottone");
        String data = new Date().toString();
        System.out.println(" "+gname+" "+gadmin+" "+post+" "+filepost+" "+data);
        
        manager.aggiornapost(post, Functions.getUserName(request), gname, gadmin, data);
        
        request.setAttribute("gname", gname);
        request.setAttribute("gadmin", gadmin);
        request.setAttribute("filepost", filepost);
        request.setAttribute("bottone", bottone);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/servletUpload");
        dispatcher.forward(request, response);

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
            Logger.getLogger(ServletCaricaPostDB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletCaricaPostDB.class.getName()).log(Level.SEVERE, null, ex);
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
