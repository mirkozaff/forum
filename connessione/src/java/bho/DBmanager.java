/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bho;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;


/**
 *
 * @author giovanni
 */
public class DBmanager implements Serializable{
    private transient Connection con;

    public DBmanager(String dburl) throws SQLException{
       try {
          Class.forName("org.apache.derby.jdbc.EmbeddedDriver",true,getClass().getClassLoader());
       }catch(Exception e){
           throw new RuntimeException(e.toString(), e);
       }
       Connection con = DriverManager.getConnection(dburl);
       this.con = con;
    }
    public static void shutdown(){
        try{
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex){
            Logger.getLogger(DBmanager.class.getName()).info(ex.getMessage());
        }
    }
    public User authenticate(String name, String password) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT * FROM utenti WHERE name= ? AND password = ?");
        try{
            stm.setString(1, name);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try{
            if(rs.next()){
                User user = new User();
                user.setName(name);
                user.setPassword(password);
                return user;
            } else {
                return null;
                }
            } finally {
            rs.close();
            }
        }finally {
         stm.close();
        }   
    }
}
    

