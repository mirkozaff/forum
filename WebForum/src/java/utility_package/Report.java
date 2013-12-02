package utility_package;


public class Report {
    
    String gname;
    String gadmin;
    String utentiPartecipanti;
    String dataUltimoPost;
    int numeroPost;

    
    public Report(String gname, String gadmin, String utentiPartecipanti, String dataUltimoPost, int numeroPost) {
        this.gname = gname;
        this.gadmin = gadmin;
        this.utentiPartecipanti = utentiPartecipanti;
        this.dataUltimoPost = dataUltimoPost;
        this.numeroPost = numeroPost;
   }

    public String getGname() {
        return gname;
    }

    public String getGadmin() {
        return gadmin;
    }

    public String getUtentiPartecipanti() {
        return utentiPartecipanti;
    }

    public String getDataUltimoPost() {
        return dataUltimoPost;
    }

    public int getNumeroPost() {
        return numeroPost;
    }
 
}
