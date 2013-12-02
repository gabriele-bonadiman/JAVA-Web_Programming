package listener;

import database.DBManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class contextListener implements ServletContextListener{
    
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
}
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBManager.dbOff();
    }
}
