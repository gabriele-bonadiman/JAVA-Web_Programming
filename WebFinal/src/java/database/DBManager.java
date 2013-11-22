
package database;

import classi.Utente;
import classi.Gruppo;
import java.util.Date;
import classi.Lista;
import classi.Post;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DBManager {
    
    public static transient Connection con;
    private static final String Utenti = "UTENTE";
    private static final String Gruppo = "GRUPPO";
    private static final String Inviti = "INVITO";
    private static final String Lista = "LISTA";
    private static final String Post = "POST";
    private static final String Fpost = "FILE_POST";
    
    /**********************************************
     *                  DATABASE                  *
     **********************************************/
    
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
     * Metodo per settare OFF il db
     */
    public static void dbOff() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());}
    }
}
