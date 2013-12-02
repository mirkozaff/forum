package servlet_package;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import db_package.DBmanager;
import java.io.FileOutputStream;
import java.io.IOException;
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
        response.setContentType("text/html;charset=UTF-8");
        
        this.manager = (DBmanager)super.getServletContext().getAttribute("dbmanager");
        
        String gname = request.getParameter("gname");
        String gadmin = request.getParameter("gname");
        Report rep = new Report(gname, gadmin, manager.utentiPartecipantiPDF(gname, gadmin).toString(), manager.ultimaDataPDF(gname, gadmin), manager.numeroPostPDF(gname, gadmin));
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Variabili.FILENAME));
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
