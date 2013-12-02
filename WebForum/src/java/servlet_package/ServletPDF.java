package servlet_package;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import db_package.DBmanager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility_package.Report;
import utility_package.Variabili;


@WebServlet(name = "ServletPDF", urlPatterns = {"/ServletPDF"})
public class ServletPDF extends HttpServlet {
    
    DBmanager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException, SQLException {
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String gname = request.getParameter("gname");
        String gadmin = request.getParameter("gadmin");
        Report rep = new Report(gname, gadmin, manager.utentiPartecipantiPDF(gname, gadmin).toString(), manager.ultimaDataPDF(gname, gadmin), manager.numeroPostPDF(gname, gadmin));
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Variabili.FILENAME + gname + ".pdf"));
        document.open();
        Paragraph inizio = new Paragraph("Report gruppo \"" + rep.getGname() + "\" di \"" + rep.getGadmin() + "\"\n");
        Paragraph utentiPartecipanti = new Paragraph("Utenti Partecipanti: " + rep.getUtentiPartecipanti() + "\n");
        Paragraph dataUltimoPost = new Paragraph("Data ultimo post: " + rep.getDataUltimoPost() + "\n");
        Paragraph numeroPost = new Paragraph("Numero post fatti: " + rep.getNumeroPost());
        document.add(inizio);
        document.add(utentiPartecipanti);
        document.add(dataUltimoPost);
        document.add(numeroPost);        
        document.close();
        
        out.println("<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>pdf</title>"
                + "<link href=\"bootstrapCSS/bootstrap.css\" rel=\"stylesheet\">"
                + "<link href=\"forumCSS/pdfcreato.css\" rel=\"stylesheet\">"
                + "</head>"
                + "<body>"
                + "<div id=\"wrap\">"
                + "<div class=\"container\">"
                + "<div class=\"page-header\">"
                + "<h1>Il PDF del gruppo è stato creato!</h1>"
                + "</div>"
                + "<p class=\"lead\">puoi trovare il documento pdf sul tuo desktop</p>"
                + "<form action=\"servletVisualizzaPost\" method=POST>"
                + "<button type=\"submit\" class=\"btn btn-primary\">torna al gruppo</button>"
                + "<input type=\"hidden\" name=\"gname\" value=\""+gname+"\">"
                + "<input type=\"hidden\" name=\"gadmin\" value=\""+gadmin+"\">"
                + "</form>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>");
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
        } catch (DocumentException ex) {
            Logger.getLogger(ServletPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletPDF.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (DocumentException ex) {
            Logger.getLogger(ServletPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletPDF.class.getName()).log(Level.SEVERE, null, ex);
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
