package db_package;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import utility_package.Post;


public class DBmanager implements Serializable{
    
    private transient Connection con;

    public DBmanager () throws SQLException{
       try {
          Class.forName("org.apache.derby.jdbc.ClientDriver",true,getClass().getClassLoader());
           System.out.println("caricato driver");
       }catch(Exception e){
           throw new RuntimeException(e.toString(), e);
       }
       this.con = DriverManager.getConnection("jdbc:derby://localhost:1527/DBforum","forum","forum");
        System.out.println("connessione al db fatta");
    }
    public static void shutdown(){
        try{
            DriverManager.getConnection("jdbc:derby://localhost:1527/DBforum;shutdown=true");
        } catch (SQLException ex){
            Logger.getLogger(DBmanager.class.getName()).info(ex.getMessage());
        }
    }
    public boolean authenticate(String name, String password) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT * FROM utenti WHERE name= ? AND password = ?");
        try{
            stm.setString(1, name);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try{
            if(rs.next()){
                return true;
            } else {
                return false;
                }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }   
    }
     public void listagruppi(String name, ArrayList<String> listagruppi, ArrayList<String> listaadmin ) throws SQLException{
         
         // trovo i gruppi a cui l'utente è iscritto o di cui è amministratore
        PreparedStatement stm = con.prepareStatement("SELECT DISTINCT GNAME,GADMIN FROM gruppi where UTENTE=? AND INVITATO=false");
        try{
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            try{
            while(rs.next()){
                listagruppi.add(rs.getString(1));
                listaadmin.add(rs.getString(2));
            }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }
   }
    public void getImageURL(String name, HttpSession session) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT URL_IMAGE FROM utenti WHERE NAME= ?");
        try{
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    session.setAttribute("img",(rs.getString(1)));
                }
            }finally {
                rs.close();
            }
        }finally {
         stm.close();
        }    
    }
    
    public String getAvatar(String name) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT URL_IMAGE FROM utenti WHERE NAME= ?");
        try{
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    return rs.getString(1);
                }
            }finally {
                rs.close();
            }
        }finally {
         stm.close();
        }  
        return "noIMG";
    }
    
    public int getGroupID(String gname, String gadmin) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT ID FROM Gruppi WHERE GNAME=? AND GADMIN=?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    return rs.getInt(1);
                }
            }finally {
                rs.close();
            }
        }finally {
         stm.close();
        }  
        return 0;
    }
    
    public void setImageURL(String name, String imgURL, HttpSession session) throws SQLException{
        PreparedStatement stm = con.prepareStatement("UPDATE utenti SET URL_IMAGE= ? WHERE NAME= ?");
        
        try{
            stm.setString(1, imgURL);
            stm.setString(2, name);
            session.setAttribute("img", imgURL);
            stm.execute();
         }finally {
         stm.close();
        }    
    }
    
     public void listanomi(ArrayList<String> listanomi) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT NAME FROM utenti");
        try{
            ResultSet rs = stm.executeQuery();
            try{
            while(rs.next()){
                listanomi.add(rs.getString(1));
            }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }
     }
      public void listaiscritti(String gname, String gadmin, ArrayList<String> listanomi) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT DISTINCT UTENTE FROM gruppi WHERE GNAME=? AND GADMIN=?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
            while(rs.next()){
                listanomi.add(rs.getString(1));
            }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }
     }
      
      public boolean accessAllowed(String gname, String gadmin, String userName) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT DISTINCT UTENTE FROM gruppi WHERE GNAME=? AND GADMIN=?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    if(rs.getString(1).equals(userName)){
                        return true;
                    }
                }
            } finally {
                rs.close();
            }
        }finally {
            stm.close();
        }
        return false;
     }
      
      public int getMaxID() throws SQLException{
          int a = 0;
          PreparedStatement stm = con.prepareStatement("SELECT MAX(ID) FROM Gruppi"); 
          try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    a = rs.getInt(1);
                }
            }finally {
                rs.close();
            }
        }finally {
         stm.close();
        }  
        return a;
      }
      
      
     public void aggiornalistagruppi(String gname, String adminname, String[] utentiNuovoGruppo) throws SQLException{  
         
         int idValue = getMaxID();
         //cerco se esiste già un record nella tabella "gruppi" in cui il nome del gruppo e l'admin sono uguali a quelli che voglio creare
        PreparedStatement stm = con.prepareStatement("SELECT DISTINCT GNAME,GADMIN FROM gruppi where GNAME=? AND GADMIN=?"); 
        PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi(ID,GNAME,UTENTE,GADMIN,INVITATO) VALUES (?,?,?,?,?)");
        try{
            stm.setString(1, gname);
            stm.setString(2, adminname);
            ResultSet rs = stm.executeQuery();
            
            // se il gruppo che voglio creare è nuovo(ossia la coppia gname e gadmin che voglio creare non è gia presente) lo creo
            if(rs.next()==false){
                
                //aggiorno il db con il record riguardante l'admin (id,gname, admin, admin)
                stm2.setInt(1, idValue+1);
                stm2.setString(2, gname);
                stm2.setString(3, adminname);
                stm2.setString(4, adminname);
                stm2.setBoolean(5, false);
                stm2.execute();
                
                //aggiorno il db con il record riguardante gli invitati (gname, admin, admin)
                if(utentiNuovoGruppo!=null){
                for(int i=0; i<utentiNuovoGruppo.length;i++){
                stm2.setInt(1, idValue+1);
                stm2.setString(2, gname);
                stm2.setString(3, utentiNuovoGruppo[i]);
                stm2.setString(4, adminname);
                stm2.setBoolean(5, true);
                stm2.execute();
                }
                }
            }else{
                System.out.println(" gruppo e admin gia esistenti");
            }
        }finally {
         stm.close();
         stm2.close();
        }
    }
     public void getpost(String gname, String gadmin, ArrayList<Post> listapost) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT POST,UTENTE_POSTANTE,DATA FROM post WHERE GNAME=? AND GADMIN=?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
               while(rs.next()){
                String testo = rs.getString("POST");
                String utente_postante = rs.getString("UTENTE_POSTANTE");
                String data = rs.getString("DATA");
                Post post = new Post(testo,utente_postante,gname,gadmin,data);   
                listapost.add(post);
               }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }
     }
     public void getinviti(String name, ArrayList<String> listagname, ArrayList<String> listagadmin) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT GNAME,GADMIN FROM gruppi WHERE UTENTE=? AND INVITATO=true");
        try{
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            try{
               while(rs.next()){
                String gname = rs.getString("GNAME");
                String gadmin = rs.getString("GADMIN");  
                listagname.add(gname);
                listagadmin.add(gadmin);
               }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }
     }
     public void aggiornapost(String post, String utente_postante,String gname,String gadmin,String data) throws SQLException{
         
         //creo un record nella tabella post con il nuovo post creato
         PreparedStatement stm = con.prepareStatement("INSERT INTO post(POST,UTENTE_POSTANTE,GNAME,GADMIN,DATA) VALUES (?,?,?,?,?)");
         try{
             stm.setString(1, post);
             stm.setString(2, utente_postante);
             stm.setString(3, gname);
             stm.setString(4, gadmin);
             stm.setString(5, data);
             stm.execute();
             System.out.println("post aggiornato");
         }finally{
             stm.close();
         }
     }
     public void aggiornarecordinviti(String gname, String utente,String gadmin, String bottone) throws SQLException{
         
         
         System.out.println("sono nell'aggiornainviti");
         //creo un record nella tabella post con il nuovo post creato
         PreparedStatement stm = con.prepareStatement("UPDATE gruppi SET INVITATO=false WHERE GNAME=? AND UTENTE=? AND GADMIN=?");
         PreparedStatement stm2 = con.prepareStatement("DELETE FROM gruppi WHERE GNAME=? AND UTENTE=? AND GADMIN=?");
         if(bottone.toString().equals("accetta")){
         try{
             stm.setString(1, gname);
             stm.setString(2, utente);
             stm.setString(3, gadmin);
             stm.execute();
             System.out.println("record aggiornato con invitato=false");
         }finally{
             stm.close();
         }
         }
         if(bottone.toString().equals("rifiuta")){
          try{
             stm2.setString(1, gname);
             stm2.setString(2, utente);
             stm2.setString(3, gadmin);
             stm2.execute();
             System.out.println("record cancellato");
         }finally{
             stm2.close();
         }   
         }
     }

     public void modificagruppo(String gname, String newgname, String gadmin, String[] utentiNuovoGruppo) throws SQLException{
         
         //faccio l'update del nome del gruppo
         PreparedStatement stm = con.prepareStatement("UPDATE gruppi SET GNAME=? WHERE GNAME=? AND GADMIN=?");
         //aggiungo i nuovi utenti invitati
         PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi(ID,GNAME,UTENTE,GADMIN,INVITATO) VALUES (?,?,?,?,?)");
         PreparedStatement stm3 = con.prepareStatement("UPDATE post SET GNAME=? WHERE GNAME=?");
         
         try{
             stm.setString(1, newgname);
             stm.setString(2, gname);
             stm.setString(3, gadmin);
             stm.execute();
             System.out.println("nomegruppo aggiornato");
             stm3.setString(1, newgname);
             stm3.setString(2, gname);
             stm3.execute();
         }finally{
             stm.close();
         }
       if(utentiNuovoGruppo!=null){
         try
         {
                //aggiorno il db con il record riguardante gli invitati (gname, admin, admin)
                for(int i=0; i<utentiNuovoGruppo.length;i++){
                    stm2.setInt(1, getGroupID(newgname, gadmin));
                    stm2.setString(2, newgname);
                    stm2.setString(3, utentiNuovoGruppo[i]);
                    stm2.setString(4, gadmin);
                    stm2.setBoolean(5, true);
                    stm2.execute();
                    System.out.println("aggiunti i nuovi tizi invitati");
                }
         }finally {
            stm2.close();
         }
       }
     }
     
     public ArrayList<String> utentiPartecipantiPDF(String gname, String gadmin)throws SQLException{
        
        ArrayList <String> utentiPartecipanti = new ArrayList <String>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppi WHERE gname= ? AND gadmin = ?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    utentiPartecipanti.add(rs.getString("UTENTE"));
                }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        } 
        return utentiPartecipanti;
     }
     
     public int numeroPostPDF(String gname, String gadmin)throws SQLException{
        
        int numPost = 0;
        PreparedStatement stm = con.prepareStatement("SELECT * FROM post WHERE gname= ? AND gadmin = ?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    numPost++;
                }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        } 
        return numPost;
     }
     
     public String ultimaDataPDF(String gname, String gadmin)throws SQLException{
        
        String ultimaData = "";
        PreparedStatement stm = con.prepareStatement("SELECT data FROM post WHERE gname= ? AND gadmin = ?");
        try{
            stm.setString(1, gname);
            stm.setString(2, gadmin);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    ultimaData = rs.getString("DATA");
                }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        } 
        return ultimaData;
     }
}