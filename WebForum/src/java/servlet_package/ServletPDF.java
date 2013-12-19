package servlet_package;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import db_package.DBmanager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
        int gID = manager.getGroupID(gname, gadmin);
        Report rep = new Report(gname, gadmin, manager.utentiPartecipantiPDF(gname, gadmin).toString(), manager.ultimaDataPDF(gname, gadmin), manager.numeroPostPDF(gname, gadmin));
        
        //crea la folder del gruppo se non esiste con nome "gID"
        File grupFolder = new File(Variabili.PATH_GROUPS + String.valueOf(gID));
        grupFolder.mkdirs();
        
        Document document = new Document();
        FileOutputStream report = new FileOutputStream(Variabili.PATH_GROUPS + String.valueOf(gID) + "/" + "report.pdf");
        PdfWriter.getInstance(document,report );
        document.open();
        Paragraph inizio = new Paragraph("Report gruppo \"" + rep.getGname() + "\" di \"" + rep.getGadmin() + "\"\n");
        Paragraph utentiPartecipanti = new Paragraph("Utenti Partecipanti: " + rep.getUtentiPartecipanti() + "\n");
        Paragraph dataUltimoPost;
        if(rep.getNumeroPost() == 0){
            dataUltimoPost = new Paragraph("Data ultimo post: nessun post in questo gruppo\n");
        }
        else{
            dataUltimoPost = new Paragraph("Data ultimo post: " + rep.getDataUltimoPost() + "\n");
        }
        Paragraph numeroPost = new Paragraph("Numero post fatti: " + rep.getNumeroPost());
        document.add(inizio);
        document.add(utentiPartecipanti);
        document.add(dataUltimoPost);
        document.add(numeroPost);        
        document.close();
        
        request.setAttribute(Variabili.OP, Variabili.PDF);
        request.setAttribute(Variabili.GID, String.valueOf(gID));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/file/report.pdf");
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
