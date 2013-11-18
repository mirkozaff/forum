/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bho;

import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author giovanni
 */
public class WebappContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      String dburl = sce.getServletContext().getInitParameter("dburl");
      try{
          DBmanager manager = new DBmanager(dburl);
          sce.getServletContext().setAttribute("dbmanager", manager);
      } catch (SQLException ex) {
          Logger.getLogger(getClass().getName()).severe(ex.toString());
          throw new RuntimeException(ex);
      }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBmanager.shutdown();
    }
    
}
