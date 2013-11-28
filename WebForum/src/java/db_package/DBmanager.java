package db_package;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;


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
     public void listagruppi(String name, ArrayList<String> listagruppi,ArrayList<String> listadate) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT GNAME FROM gruppi WHERE ADMINNAME= ?");
        PreparedStatement stm2 = con.prepareStatement("SELECT DATA FROM gruppi WHERE ADMINNAME= ?");
        try{
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            try{
            while(rs.next()){
                listagruppi.add(rs.getString(1));
            }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }
        try{
            stm2.setString(1, name);
            ResultSet rs2 = stm2.executeQuery();
            try{
            while(rs2.next()){
                listadate.add(rs2.getString(1));
            }
            } finally {
            rs2.close();
            }
        }finally {
         stm2.close();
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
}