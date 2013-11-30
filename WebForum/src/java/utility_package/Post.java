

package utility_package;

public class Post {
    
    String testo;
    String utente_postante;
    String gname;
    String gadmin; 
    String data;

    public Post(String testo, String utente_postante,String gname,String gadmin, String data) {
        this.testo = testo;
        this.utente_postante = utente_postante;
        this.gname = gname;
        this.gadmin = gadmin;
        this.data = data;
    }

    public String getTesto() {
        return testo;
    }

    public String getUtente_postante() {
        return utente_postante;
    }

    public String getGname() {
        return gname;
    }

    public String getGadmin() {
        return gadmin;
    }

    public String getData() {
        return data;
    }


    
}
