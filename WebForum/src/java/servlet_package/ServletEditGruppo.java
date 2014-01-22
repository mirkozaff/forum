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
import utility_package.Functions;

// questa servlet invia l'html che mostra il form per creare un gruppo
public class ServletEditGruppo extends HttpServlet {
    private DBmanager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String gname =request.getParameter("gname");
        String gadmin=request.getParameter("gadmin");
        Boolean modifica = false;
        
        if(gname!=null && gadmin!=null){
            modifica = true;
        }
        
        ArrayList<String> listanomi = new <String>ArrayList();
        ArrayList<String> listaiscritti = new <String>ArrayList();
        ArrayList<String> listavisualizzata = new <String>ArrayList();
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        
        manager.listanomi(listanomi); 
        manager.listaiscritti(gname, gadmin, listaiscritti);
        
        if(listanomi.contains(Functions.getUserName(request))){
            listanomi.remove(Functions.getUserName(request));
        }
        for (String s : listanomi){
            System.out.println(s);
        }
        
        for (String s : listanomi) {  
            if(!listaiscritti.contains(s)){
                listavisualizzata.add(s);
            }
        }
        
        
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
           out.println("<div class=\"contenitore-azzurro\">");
           out.println("<form action=\"servletEditGruppoDB\" method=POST name=\"modulo\" onSubmit=\"return contr()\">");
           out.println("<div class=\"form-group\"> "
                   + "<label>Nome del gruppo</label> "
                   + "<input type=\"text\" class=\"form-control\" name=\"nomegruppo\"");
           //se nella chiamata ho passato il parametro gname lo metto nel placeholder
           if(modifica){
           out.println("value=\""+gname+"\">");
           out.println("<input type=\"hidden\" name=\"gname\" value=\""+gname+"\"> "
                   + "</div>");
           }else{
           out.println(">"
                   + "</div>");
           }
           
           out.println("<div class=\"form-group\"> "
                   + "<label>invita i tuoi amici</label>");
       
           //visualizzo le checkbox con gli utenti invitabili
           if(!listavisualizzata.isEmpty()){
           for(int i=0;i<listavisualizzata.size();i++){
               out.println("<div class=\"checkbox\">"
                       + "<label>"
                       + "<input type=\"checkbox\" name=\"utente\" value=\""+listavisualizzata.get(i)+"\">"+listavisualizzata.get(i)+"</label>"
                       + "</div>");
           }
           }else{out.println("<p class=\"testorosso\">non ci sono utenti da invitare</p>");}
           
           if(modifica){
           out.println("<div class=\"centra\">"
                   + "<input class=\"btn btn-lg btn-success\" type=\"submit\" name=\"bottone\" value=\"modifica gruppo\">"
                   + "</div>"
                   + "</form>");
           }else{
           out.println("<div class=\"centra\">"
                   + "<input class=\"btn btn-lg btn-success\" type=\"submit\" name=\"bottone\" value=\"crea gruppo\">"
                   + "</div>"
                   + "</form>");    
           }
            out.println("</div></div><div class=\"col-md-4\"></div></div>"
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
