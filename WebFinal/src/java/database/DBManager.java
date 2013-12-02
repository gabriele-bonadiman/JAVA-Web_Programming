
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


public class DBManager {
    
    public static transient Connection con;
    
    /**
     * Costruttore di default che prende in input l'indirizzo contentente il db
     * @param dburl
     * @throws SQLException 
     */
    public DBManager(String dburl) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver", true, getClass().getClassLoader());
        } catch (Exception e) {throw new RuntimeException(e.toString(), e);}
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;
    }
    
    /**
     * Metodo per chiudere la connessione con il DB
     */
    public static void dbOff() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());}
    }
}
