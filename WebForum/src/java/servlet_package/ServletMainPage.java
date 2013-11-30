package servlet_package;

import utility_package.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name = "ServletMainPage", urlPatterns = {"/ServletMainPage"})
public class ServletMainPage extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //richiesta cookie        
        Cookie[] cookies = request.getCookies();
        Cookie tmp, 
               lastAccessedTime = new Cookie("ultimoAccesso", "(nessun ultimo accesso salvato)");
               
        //controllo che il cookie cercato sia già presente
        for(int i = 0 ; i < cookies.length; i++) {
            tmp = cookies[i];
            if(tmp.getName().equals("ultimoAccesso")){
                lastAccessedTime = tmp;                
            }
        }
        
        //setto la stringa di ultimo accesso, se non ci sono accessi salvati lo dico
        String time = "(nessuna accesso salvato)";
        try{
            time = new Date(Long.parseLong(lastAccessedTime.getValue())).toLocaleString();
        }catch(Exception e){}
             
        String mainPage = "forumHTML/mainPage.html";
        PrintWriter out = response.getWriter();       
               
        try {
            ServletContext context = getServletContext();
            InputStream inp = context.getResourceAsStream(mainPage);
            if (inp != null) {
               InputStreamReader isr = new InputStreamReader(inp);
               BufferedReader reader = new BufferedReader(isr);
               String text = "";
               while ((text = reader.readLine()) != null) {
                   out.println(text);
               }
            }
            out.println("Ultimo accesso: " + time
                        + "<a href=\"servletLogout\"> <button type=\"submit\" class=\"btn btn-primary navbar-btn\">Logout</button> </a>"
                        + "&nbsp;"
                        + "</div>"
		        +"</nav>"
                        + "<br><br>"
                        + "<div class=\"container\">"
			+ "<div class=\"row\">"
                        + "<div class=\"col-md-6\">" 
			+ "</div>"
			+ "<div class=\"col-md-6\">"
                        + "<h2> Benvenuto " + User.name + "!</h2>"
			+ "</div>"
                        + "</div>"
			+ "<div class=\"row\">"
			+ "<div class=\"col-md-6\">"
			+ "<div class=\"row\">"
			+ "<div class=\"col-md-6\"><button type=\"button\" class=\"btn btnmio btn-primary navbar-btn\"><big><big>inviti</big></big></button></div>"
			+ "</div>"
			+ "<div class=\"row\">"
			+ "<div class=\"col-md-6\"><a href=\"servletListaGruppi\" class=\"btn btnmio btn-primary navbar-btn\"><big><big>gruppi</big></big></a></div>"
			+ "</div>"
			+ "<div class=\"row\">"
			+ "<div class=\"col-md-6\"> <a href=\"servletEditGruppo\" class=\"btn btnmio btn-primary navbar-btn\"><big><big>crea gruppo</big></big></a></div>"
			+ "</div>"
			+ "<div class=\"row\">"
                        + "<div class=\"col-md-6\"><a href=\"servletDatiUtente\" class=\"btn btnmio btn-primary navbar-btn\"><big><big>Dati utente</big></big></a></div>"
			+ "</div>"
			+ "</div>"
			+ "<div class=\"col-md-6\">"
			+ "<img src=\"" + User.imageURL +"\" alt=\"cagna\" class=\"img-rounded\">"
			+ "</div>"
			+ "</div>"
		        + "</div>"
                        + "\n"    
                        + "<!-- Bootstrap core JavaScript"
                        + "================================================== -->"
                        + "<!-- Placed at the end of the document so the pages load faster -->"
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
