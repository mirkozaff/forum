package db_package;

import utility_package.User;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;
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
                User.name = name;
                User.password = password;
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
        PreparedStatement stm = con.prepareStatement("SELECT DISTINCT GNAME,GADMIN FROM gruppi where UTENTE=?");
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
    public void getImageURL(String name) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT URL_IMAGE FROM utenti WHERE NAME= ?");
        try{
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    User.imageURL = (rs.getString(1));
                }
            }finally {
                rs.close();
            }
        }finally {
         stm.close();
        }    
    }
    
    public void setImageURL(String name, String imgURL) throws SQLException{
        PreparedStatement stm = con.prepareStatement("UPDATE utenti SET URL_IMAGE= ? WHERE NAME= ?");
        
        try{
            stm.setString(1, imgURL);
            stm.setString(2, name);
            User.imageURL = imgURL;
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
     public void aggiornalistagruppi(String gname, String adminname, String[] utentiNuovoGruppo) throws SQLException{  
         
         //cerco se esiste già un record nella tabella "gruppi" in cui il nome del gruppo e l'admin sono uguali a quelli che voglio creare
        PreparedStatement stm = con.prepareStatement("SELECT DISTINCT GNAME,GADMIN FROM gruppi where GNAME=? AND GADMIN=?"); 
        PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi(GNAME,UTENTE,GADMIN) VALUES (?,?,?)");
        try{
            stm.setString(1, gname);
            stm.setString(2, adminname);
            ResultSet rs = stm.executeQuery();
            
            // se il gruppo che voglio creare è nuovo(ossia la coppia gname e gadmin che voglio creare non è gia presente) lo creo
            if(rs.next()==false){
                
                //aggiorno il db con il record riguardante l'admin (gname, admin, admin)
                stm2.setString(1, gname);
                stm2.setString(2, adminname);
                stm2.setString(3, adminname);
                stm2.execute();                
                
                //aggiorno il db con il record riguardante gli invitati (gname, admin, admin)
                for(int i=0; i<utentiNuovoGruppo.length;i++){
                stm2.setString(1, gname);
                stm2.setString(2, utentiNuovoGruppo[i]);
                stm2.setString(3, adminname);
                stm2.execute();
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
}