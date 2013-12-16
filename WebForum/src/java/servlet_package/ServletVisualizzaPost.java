package servlet_package;

import db_package.DBmanager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility_package.Functions;
import utility_package.Post;

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
           out.print("<form id=\"form\" action=\"servletUpload?op=testo&gname=" +gname+ "&gadmin=" +gadmin+ "\" method=POST enctype=\"multipart/form-data\">"
               + "<input type=\"file\" name=\"filepost\">"
               + "<button type=\"submit\" class=\"btn btn-primary\" name=\"bottone\" value=\"upload_gruppi\">Save changes</button>"
               + "<input type=\"hidden\" name=\"op\" value=\"testo\">");
           out.println("<input type=\"hidden\" name=\"gname\" value=\""+gname+"\">");
           out.println("<input type=\"hidden\" name=\"gadmin\" value=\""+gadmin+"\">");
           out.println("</form></div></div></div></div></div><div class=\"navbar-default divcentrato\">");
           out.println("<p align=\"center\" style=\"font-size: 250%\">"+gname+"</p>");
           if(gadmin.toString().equals(Functions.getUserName(request))){
           out.println("<div style=\"text-align: center\">"
                   + "<form action=\"servletEditGruppo\" method=POST>"
                   + "<button type=\"submit\" class=\"btn btn-success navbar-btn\">edita gruppo</button>&nbsp;"
                   + "<input type=\"hidden\" name=\"gname\" value=\""+gname+"\">"
                   + "<input type=\"hidden\" name=\"gadmin\" value=\""+gadmin+"\">"
                   + "<button type=\"submin\" class=\"btn btn-success navbar-btn\" formaction=\"servletPDF\">pdf del gruppo</button>"
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
                   + "<div class=\"box\">"+modifica(listapost.get(i).getTesto())+"</div>"
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
    
    public String modifica(String testo){
        Scanner s = new Scanner(testo);
        String testoFinale ="";
        String nomeLink = "";
        String modifing;
        while(s.hasNext())
        {
            modifing=s.next();
            char [] caratteri = new char[modifing.length()];
            for (int i=0; i<caratteri.length; i++) {
            caratteri[i] = modifing.charAt(i);}
            
            if(caratteri[0]=='$' && caratteri[1]=='$' && caratteri[caratteri.length-1]=='$' && caratteri[caratteri.length-2]=='$'){
                String tmp;
                for(int y = 2; y<caratteri.length-2; y++){ 
                tmp = Character.toString(caratteri[y]);    
                nomeLink = nomeLink+tmp;       //questo è il nome del link
                modifing = "<a href=\""+nomeLink+"\" target=\"_blank\">"+nomeLink+"</a>"; 
                }  
            }
            testoFinale = testoFinale+" "+modifing;
       
        }return testoFinale;
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
