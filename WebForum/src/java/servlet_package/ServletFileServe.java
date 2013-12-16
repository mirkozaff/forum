package servlet_package;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility_package.Functions;
import utility_package.Variabili;


public class ServletFileServe extends HttpServlet {

    private String filePath;
    private String gname;
    private String gadmin;
    private String userAvatar;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {   
        
        gadmin = request.getParameter("gadmin");
        gname = request.getParameter("gname"); 
        userAvatar = request.getParameter("avatar");
        
        //setta il path
        if(request.getParameter(Variabili.OP).equals(Variabili.PROFILE_IMG)){
            filePath = Variabili.PATH_PROFILE_IMG + Functions.getUserName(request);
        }
        else if(request.getParameter(Variabili.OP).equals(Variabili.AVATAR_IMG)){
            filePath = Variabili.PATH_PROFILE_IMG + userAvatar;
        }
        else if(request.getParameter(Variabili.OP).equals(Variabili.PDF)){
            filePath = Variabili.PATH_GROUPS + gname + "_" + gadmin;
        }
        else if(request.getParameter(Variabili.OP).equals(Variabili.ALLEGATO)){
            filePath = Variabili.PATH_GROUPS + gname + "_" + gadmin;
        }
        else{
            // error 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        // prende il fiel richiesto dalle path info
        String requestedFile = request.getPathInfo();
        // verifica che il file sia supportato dalla requested URI
        if (requestedFile == null) {
            // error 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Fa il decode del file name poiche può contenere spazi
        File file = new File(filePath, URLDecoder.decode(requestedFile, "UTF-8"));

        // cerca se il file esiste
        if (!file.exists()) {
            //error 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //prende il content type dal file name
        String contentType = getServletContext().getMimeType(file.getName());

        // Se il content type è sconosciuto setta il valore di default
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Inizializza servlet response
        response.reset();
        response.setBufferSize(Variabili.DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // Prepara gli streams
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            //apre gli streams
            input = new BufferedInputStream(new FileInputStream(file), Variabili.DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), Variabili.DEFAULT_BUFFER_SIZE);

            //Scrive il contenuto dei file nel response
            byte[] buffer = new byte[Variabili.DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // chiusura stream
            Functions.close(output);
            Functions.close(input);
        }
    }
}