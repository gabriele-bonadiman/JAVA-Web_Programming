/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listener;

import database.DBManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Babol
 */
public class contextListener implements ServletContextListener{
    public static String uploadFilePathAssoluta;
    public static String uploadAvatarPathAssoluta;
    
 @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");
        try {
            DBManager manager = new DBManager(dburl);
            sce.getServletContext().setAttribute("dbmanager", manager);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
        
        uploadFilePathAssoluta =sce.getServletContext().getRealPath("/UploadedFile");
        
        uploadAvatarPathAssoluta =sce.getServletContext().getRealPath("/UploadedAvatar");
}
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Il database Derby deve essere "spento" tentando di connettersi al database con shutdown=true
        DBManager.dbOff();
    }
}
