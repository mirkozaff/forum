/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listener_package;

import db_package.DBmanager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author giovanni
 */
@WebListener
public class listener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("IL LISTENER VA!!!!");
        try{
          DBmanager manager = new DBmanager();
          sce.getServletContext().setAttribute("dbmanager", manager);
      } catch (SQLException ex) {
          Logger.getLogger(getClass().getName()).severe(ex.toString());
          throw new RuntimeException(ex);
      }
        
    }  

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         DBmanager.shutdown();
         System.out.println("SHUTDOWN");
    }
}
