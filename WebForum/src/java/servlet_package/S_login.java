package servlet_package;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giovanni
 */
public class S_login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String risposta = "";
        Cookie cookie;
        int registrato = 0;
        
        Cookie[] cookies = request.getCookies();
        
        for(int i=0; i<cookies.length; i++){
          if(cookies[i].getName().equals("name") == true){
            risposta = "<!DOCTYPE html><html><head><title>Servlet web_form_servlet</title></head><body><h1>ti sei registrato con il nome "+cookies[i].getValue()+ " "+cookies[i].getName()+ "</h1></body></html>";
            registrato = 1;
          }
        } 
        if(registrato == 0 && request.getParameter("name") == null)
              {
            risposta = "<!DOCTYPE html><html><head><title>Servlet web_form_servlet</title></head><body><h1>form</h1><br><form>First name <input type=\"text\" name=\"name\"><br></form></body></html>"; 
          }
        if(registrato == 0 && request.getParameter("name") != null){              
           String nome = request.getParameter("name");
           String passw = request.getParameter("passw");
           cookie = new Cookie("name",nome);
           cookie.setMaxAge(600);
           response.addCookie(cookie);
        
           risposta = "<!DOCTYPE html><html><head><title>Servlet web_form_servlet</title></head><body><h1>HAI SETTATO IL COOKIE</h1></body></html>"; 
               }
        try {
            out.println(risposta);
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
