
package database;

import classi.Utente;
import classi.Gruppo;
import classi.Invito;
import classi.Lista;
import classi.Post;
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
import javax.xml.crypto.Data;


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
     * Preso in input un ID restituisco l'utente asso
     * @param id
     * @return
     * @throws SQLException 
     */
    public static Utente searchUtenteByID(int id) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT * FROM UTENTE WHERE ID = ?");
        Utente ute = new Utente();
             
        try {
            stm.setInt(1, id);
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
     *  Preso in input un utente, restituisce il suo ID
     */
    public static Utente idUtente(String nome) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT * FROM UTENTE WHERE username = ?");
        Utente ute = new Utente();
             
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
     *  Preso in input un id, restituisce l'oggetto gruppo al quale e' associato
     */
    public static Gruppo searchGruppoById(int ID) throws SQLException{
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
     * Ritorna la lista di utenti presenti nel DB
     * @return
     * @throws SQLException 
     */
    public static ArrayList<Utente> listaUtenti() throws SQLException {
       PreparedStatement stm = con.prepareStatement("SELECT * FROM UTENTE");
       ArrayList<Utente> utenti = new ArrayList<Utente>();
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
    public static ArrayList<Lista> listaGruppiUtente(int IDutente)throws SQLException {
        PreparedStatement stm = con.prepareStatement
        ("SELECT * FROM INVITO WHERE UTENTE = ?");
        ArrayList<Lista> listaGruppi = new ArrayList<Lista>();
        try {
            stm.setInt(1, IDutente);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()) {
                    Lista singleReport = new Lista();
                    singleReport.setUtente(rs.getInt("utente"));
                    singleReport.setGruppo(rs.getInt("gruppo"));
                    singleReport.setInvitato(rs.getInt("INVITATO"));
                    listaGruppi.add(singleReport);
                }
            } finally { rs.close();}
        } finally {stm.close();}
        return listaGruppi;
    } 
 
    /**
     * Ritorna i gruppi ai quali l'utente ha deciso di partecipare
     */
    public static List<Integer> listaGruppiIscritto(int IDutente)throws SQLException {
        PreparedStatement stm = con.prepareStatement
        ("SELECT * FROM INVITO WHERE UTENTE = ? AND INVITATO = ?");
        List<Integer> GruppiID = new ArrayList<Integer>();
        try {
            stm.setInt(1, IDutente);
            stm.setInt(2, 1);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()) {
                    Integer singleReport = rs.getInt("gruppo");
                    GruppiID.add(singleReport);
                }
            } finally { rs.close();}
        } finally {stm.close();}
        return GruppiID;
    }
    
    /**
     * Preso in input un gruppo, restituisce le persone che vi appartengono
     */
    public static ArrayList<Utente> listaUtentiPresenti(Gruppo gr)throws SQLException{
        
        PreparedStatement stm = con.prepareStatement
        ("SELECT * FROM INVITO WHERE GRUPPO = ? AND INVITATO = ?");
       ArrayList<Utente> utenti = new ArrayList<Utente>();
       try {
            stm.setInt(1,gr.getID());
            stm.setInt(2,1);
            ResultSet rs = stm.executeQuery();
           try {
               while(rs.next()) {
                   Utente p;
                   p = DBManager.searchUtenteByID(rs.getInt("UTENTE"));
                   utenti.add(p);
               }
           } finally { rs.close();}
       } finally {stm.close();}
       return utenti;
    }
    
    /**
     * Utenti presenti non ancora iscritti al gruppo G
     */
    public static ArrayList<Utente> listaPotenzialiIscritti(Gruppo gr) throws SQLException{
        PreparedStatement stm = con.prepareStatement("select id from utente except(select invito.utente from invito where gruppo = ?)");
        ArrayList<Utente> utenti = new ArrayList<Utente>();
       try {
            stm.setInt(1,gr.getID());
            ResultSet rs = stm.executeQuery();
           try {
               while(rs.next()) {
                   Utente p;
                   p = DBManager.searchUtenteByID(rs.getInt(1));
                   utenti.add(p);
               }
           } finally { rs.close();}
       } finally {stm.close();}
       return utenti;
    }
    
    
    
    
    /**
     * Preso in input gruppo e utente, resituisco #post singolo utente
     */
    public static int numeroPostSingoloUtente(Utente u, Gruppo gr) throws SQLException{   
        PreparedStatement stm = con.prepareStatement
            ("select * from POST where GRUPPO = ? AND UTENTE = ?");
        int num=0;
        try {
             stm.setInt(1,gr.getID());
             stm.setInt(2, u.getId());
             ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()) {
                    num++;
                }
            } finally { rs.close();}
        } finally {stm.close();}
        return num;
    }
 
    /**
     * Preso in input utente e gruppo restituisco data ultimo post utente
     */
    public static java.sql.Date dataUltimoPostUtente(Utente u, Gruppo gr) throws SQLException{
        
        java.sql.Date data = null;
        java.util.Date data2 = new java.util.Date();
        data = new java.sql.Date(data2.getTime());

          PreparedStatement stm = con.prepareStatement
            ("select * from POST where GRUPPO = ? AND UTENTE = ? ORDER BY id DESC");
        int num=0;
        try {
             stm.setInt(1,gr.getID());
             stm.setInt(2, u.getId());
             ResultSet rs = stm.executeQuery();
            try {
                if(rs.next()) {
                    data = rs.getDate("data");
                }else{
                    return null;
                }
            } finally { rs.close();}
        } finally {stm.close();}
        return data;
    }
    
    /**
     * Preso in input un gruppo restituisce una lista dei suoi post
     */
    public static ArrayList<Post> listaDeiPost(Gruppo g) throws SQLException{
        
        PreparedStatement stm = con.prepareStatement
        ("SELECT * FROM POST WHERE GRUPPO = ?");
        ArrayList<Post> postGr = new ArrayList<Post>();
       try {
            stm.setInt(1,g.getID());
            ResultSet rs = stm.executeQuery();
           try {
               while(rs.next()) {
                   Post p = new Post();
                    p.setData(rs.getDate("data"));
                    p.setGruppo(rs.getInt("gruppo"));
                    p.setTesto(rs.getString("testo"));
                    p.setUtente(rs.getInt("utente"));
                   postGr.add(p);
               }
           } finally { rs.close();}
       } finally {stm.close();}
       return postGr;
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
    public static void editIscrizione(int idUtente,int idGruppo,int choose) throws SQLException{

        PreparedStatement stm = con.prepareStatement("UPDATE " + Inviti + " SET invitato = ? WHERE UTENTE = ? AND GRUPPO = ?");
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
    public static int creaGruppo(String nomeGruppo ,Utente u) throws SQLException{
        
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
        return index;
    }
    
    /**
     * Una volta creato un gruppo manda a tutti i membri l'invito
     */
    public static void inserisciInInviti(Utente u, int IDgruppo,int choose) throws SQLException{
        System.out.println("INSERISCO IN INVITI" + u.getUsername() +IDgruppo +choose);
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO "+ Inviti + " (UTENTE,GRUPPO,INVITATO) VALUES (?,?,?)");
        try {
            stm.setInt(1, u.getId());
            stm.setInt(2, IDgruppo);
            stm.setInt(3, choose);
            stm.executeUpdate();
        }finally {stm.close();}
    }
    
    /**
     * Inserimento nella lista che collega l'utente con il gruppo
     */
    public static void inserisciInLista(Utente u, int IDgruppo) throws SQLException{
    
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO "+ Lista + " (UTENTE,GRUPPO) VALUES (?,?)");
        try {
            stm.setInt(1, u.getId());
            stm.setInt(2, IDgruppo);
            stm.executeUpdate();
        } finally {stm.close();}
    }
    
    /**
     * Se l'utente modifica il nome prendo in input l'utente e il nuovo nome e UPDATE
     */
    public static void editNomeUtente(Utente u, String newName) throws SQLException{
        PreparedStatement stm = con.prepareStatement
            ("UPDATE " + Utenti + " SET USERNAME = ? WHERE ID = ?");
        try {
            stm.setString(1, newName);
            stm.setInt(2, u.getId());
            stm.executeUpdate();
        } finally {stm.close();}
    }
    
    /**
     * Se l'utente modifica la password, prendo l'utente e la nuova password e UPDATE
     */
    public static void editPasswordUtente(Utente u, String newPassword) throws SQLException{
        PreparedStatement stm = con.prepareStatement
            ("UPDATE " + Utenti + " SET PASSWORD = ? WHERE ID = ?");
        try {
            stm.setString(1, newPassword);
            stm.setInt(2, u.getId());
            stm.executeUpdate();
        } finally {stm.close();}
    }
    
    /**
     * Se l'utente modifica l'avatar, prendo l'utente e la nuova path e UPDATE
     */
    public static void editAvatarUtente(Utente u, String newPath) throws SQLException{
        PreparedStatement stm = con.prepareStatement
            ("UPDATE " + Utenti + " SET AVATAR = ? WHERE ID = ?");
        try {
            stm.setString(1, newPath);
            stm.setInt(2, u.getId());
            stm.executeUpdate();
        } finally {stm.close();}
    }
    
    /**
     *  Modifica del nome del gruppo
     */
    public static void editNomeGruppo(Gruppo g, String nuovoNome) throws SQLException{
     PreparedStatement stm = con.prepareStatement
            ("UPDATE " + Gruppo + " SET NOME = ? WHERE ID = ?");
        try {
            stm.setString(1, nuovoNome);
            stm.setInt(2, g.getID());
            stm.executeUpdate();
        } finally {stm.close();}
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
    
    
    /**
     * Preso in input utente,gruppo e testo, inserisce il post nel db
     */
    public static void insertPost(Gruppo g, Utente u, String testo) throws SQLException{
        java.sql.Date data = null;
        java.util.Date data2 = new java.util.Date();
        data = new java.sql.Date(data2.getTime());
        
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO "+ Post + " (DATA,TESTO,UTENTE,GRUPPO) VALUES (?,?,?,?)");

        try {
            stm.setDate(1, data);
            stm.setString(2, testo);
            stm.setInt(3, u.getId());
            stm.setInt(3, g.getID());
            stm.executeUpdate();
        }finally {stm.close();}
    }


}
