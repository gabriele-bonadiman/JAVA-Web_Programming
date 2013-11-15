 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import classi.Utente;
import classi.Gruppo;
import classi.Invito;
import classi.Lista;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Babol
 */
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
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    
    
    /**********************************************
     *                    GETTER                  *
     **********************************************/
    
    /**
     * Metodo che controlla la presenza o meno dell'utente all'interno del DB
     * @param username
     * @param password
     * @return
     * @throws SQLException 
     */
    public static Boolean checkUtente(String username,String password) throws SQLException{
    
        PreparedStatement stm = 
                con.prepareStatement("SELECT * FROM utente WHERE username = ? AND password = ?");
        
        System.out.println("entrato nel metodo");

        try {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    System.out.println("utenteTrovato");
                    Utente utente = new Utente();
                    utente.setUsername(username);
                    utente.setPassword(password);
                    return true;
                } else { return false;}
            } finally {rs.close();}
        } finally {stm.close();}
    }
    
    /**
     *  Preso in input un utente, restituisce il suo ID
     */
    public static Utente idUtente(String nome) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT * FROM UTENTE WHERE username = ?");
        Utente ute = new Utente();
        
        System.out.println("UTENTE PASSATO AL DB " + nome);
        
        try {
            stm.setString(1, nome);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                ute.setId(rs.getInt("ID"));
                ute.setUsername(rs.getString("USERNAME"));
                ute.setPassword(rs.getString("PASSWORD"));
                ute.setAvatar(rs.getString("AVATAR"));
            }
        } finally {stm.close();}
        return ute;
    }

    
    /**
     *  Preso in input un gruppo, restituisce il suo ID
     */
    public static Gruppo idGruppo(int ID) throws SQLException{
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM GRUPPO WHERE ID = ?");
        Gruppo g = new Gruppo();

        try {
            stm.setInt(1, ID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {        
                g.setID(rs.getInt("ID"));
                g.setNome(rs.getString("NOME"));
                g.setProprietario(rs.getInt("PROPRIETARIO"));
                g.setData_creazione((java.sql.Date)rs.getDate("DATACREAZIONE"));
            }
        } finally {stm.close();}
        return g;
    }

    /**
     * Preso in input un gruppo, restituisce l'ID dell'amministratore
     * @param p
     * @return
     * @throws SQLException 
     */    
    public int adminGruppo (Gruppo p) throws SQLException{
        return 0;
        
      /*  PreparedStatement stm = con.prepareStatement("SELECT proprietario FROM GRUPPO WHERE ID = ?");
        int ID = idGruppo(p);
        try {
            stm.setInt(1, ID);
            ResultSet rs = stm.executeQuery();
            ID = rs.getInt("proprietario");
        } finally {stm.close();}
        return ID;*/
    }
    
    
    /**
     *  Preso in input un utente, restituisce i gruppi al quale l'utente e' stato invitato
     */
    public List<Invito> listaInviti (Utente u) throws SQLException{
        return null;
/*        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM INVITO WHERE UTENTE = ?");
        int ID = idUtente(u.getUsername());
        List<Invito> listaInviti = new ArrayList<Invito>();

       try {
            stm.setInt(1,ID);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()) {
                    Invito in = new Invito();
                    in.setGruppo(rs.getInt("gruppo"));
                    in.setUtente(rs.getInt("utente"));
                    in.setFlag_invito(rs.getInt("invitato"));
                    listaInviti.add(in);
                }
            } finally { rs.close();}
        } finally {stm.close();}
        
        return listaInviti;
  */
        }
    
    /**
     * Ritorna la lista di utenti presenti nel DB
     * @return
     * @throws SQLException 
     */
    public static List<Utente> listaUtenti() throws SQLException {
       PreparedStatement stm = con.prepareStatement("SELECT * FROM UTENTE");
       List<Utente> utenti = new ArrayList<Utente>();
       try {
           ResultSet rs = stm.executeQuery();
           try {
               while(rs.next()) {
                   Utente p = new Utente();
                   p.setId(rs.getInt("id"));
                   p.setUsername(rs.getString("username"));
                   p.setPassword(rs.getString("password"));
                   utenti.add(p);
               }
           } finally { rs.close();}
       } finally {stm.close();}
       return utenti;
    }
    
    /**
     *  Ritorna i gruppi al quale un utente e' invitato a partecipare
     */
    public static List<Lista> listaGruppiUtente(int IDutente)throws SQLException {
        PreparedStatement stm = con.prepareStatement("SELECT * FROM INVITO WHERE UTENTE = ?");
        List<Lista> listaGruppi = new ArrayList<Lista>();
        try {
            stm.setInt(1, IDutente);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()) {
                    Lista singleReport = new Lista();
                    singleReport.setUtente(rs.getInt("utente"));
                    singleReport.setGruppo(rs.getInt("gruppo"));
                    singleReport.setInvitato(0);
                    listaGruppi.add(singleReport);
                }
            } finally { rs.close();}
        } finally {stm.close();}
        return listaGruppi;
    } 
 
    
    
    
    
    /**********************************************
     *                    SETTER                  *
     **********************************************/
    
    /**
     *  Preso in input id utente,idGruppo e scelta
     * o meno ad un gruppo.
     *  
     * 0 = sono iscritto
     * 1 = non sono iscritto
     */
    public void iscrizione(int idUtente,int idGruppo,int choose) throws SQLException{

        PreparedStatement stm = con.prepareStatement("UPDATE" + Inviti + " SET invitato = ? WHERE UTENTE = ? AND GRUPPO = ?");
        try {
            stm.setInt(1, choose);
            stm.setInt(2, idUtente);
            stm.setInt(3, idGruppo);
            stm.executeUpdate();
        } finally {stm.close();}
        
    }
    
    /**
     *  Preso in input l'utente che lo crea e i parametri del gruppo
     * e la lista degli utenti di appartenenza,
     * retiruisce un booleano se tutto va bene
     */
    public static boolean creaGruppo(String nomeGruppo ,Utente u, List<Utente> utenti) throws SQLException{
        
        java.sql.Date data = null;
        java.util.Date data2 = new java.util.Date();
        data = new java.sql.Date(data2.getTime());
        
        int index = 0;
        ResultSet  key = null;
        
        //creo il gruppo
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO "+ Gruppo + " (NOME,PROPRIETARIO,DATACREAZIONE) VALUES (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
        
        try {
            stm.setString(1, nomeGruppo);
            stm.setInt(2, u.getId());
            stm.setDate(3, data);
            stm.executeUpdate();
            
            key = stm.getGeneratedKeys();
            if(key.next()){
                index = key.getInt(1);
            }            
        } finally { 
            key.close();
            stm.close();
        }
        
        //associo ad ogni utente la lista degli inviti
        Iterator i = utenti.iterator(); 
        while(i.hasNext()) {
            Utente ute = (Utente) i.next();
            PreparedStatement stm2 = con.prepareStatement
            ("INSERT INTO "+ Lista + " (UTENTE,GRUPPO) VALUES (?,?)");
            try {
                stm2.setInt(1, ute.getId());
                stm2.setInt(2, index);
                stm2.executeUpdate();
            } finally {stm.close();}
        }
        
        //inserisco nella lista degli inviti il nome del gruppo, l'utente e il flag invitato
        //associo ad ogni utente la lista degli inviti
        Iterator iterator = utenti.iterator(); 
        while(iterator.hasNext()) {
            Utente ute = (Utente) iterator.next();
            PreparedStatement stm3 = con.prepareStatement
            ("INSERT INTO "+ Inviti + " (UTENTE,GRUPPO,INVITATO) VALUES (?,?,?)");
        try {
                stm3.setInt(1, ute.getId());
                stm3.setInt(2, index);
                stm3.setInt(3, 0);
                stm3.executeUpdate();
            } finally {stm3.close();}
        }
        
        
        
        utenti.clear();
        return true;
    }
    
    
    /**
     * Preso in input un utente, ne modifica i parametri
     */
    public boolean editUtente(Utente ute) throws SQLException{
    
        PreparedStatement stm = con.prepareStatement
        ("ALTER TABLE "+ Utenti + " (USERNAME,PASSWORD,AVATAR) VALUES (?,?,?)");
        try {
            stm.setString(1, ute.getUsername());
            stm.setString(2, ute.getPassword());
            stm.setString(3, ute.getAvatar());
            stm.executeUpdate();
        } finally {stm.close();}
        
        return true;
    }
    
    
    
}
