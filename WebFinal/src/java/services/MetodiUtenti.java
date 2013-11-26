package services;

import classi.Utente;
import static database.DBManager.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class MetodiUtenti {

    
    /**
     * Metodo che controlla la presenza o meno dell'utente all'interno del DB
     * @throws SQLException 
     */
    public static Boolean checkUtente(String username,String password) throws SQLException{
    
        PreparedStatement stm = 
                con.prepareStatement("SELECT * FROM utente WHERE username = ? AND password = ?");
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
     * Preso in input un utente, ne modifica i parametri
     */
    public boolean editUtente(Utente ute) throws SQLException{
    
        PreparedStatement stm = con.prepareStatement
        ("ALTER TABLE UTENTE (USERNAME,PASSWORD,AVATAR) VALUES (?,?,?)");
        try {
            stm.setString(1, ute.getUsername());
            stm.setString(2, ute.getPassword());
            stm.setString(3, ute.getAvatar());
            stm.executeUpdate();
        } finally {stm.close();}
        
        return true;
    }
    
    /**
     * Se l'utente modifica l'avatar, prendo l'utente e la nuova path e UPDATE
     */
    public static void editAvatarUtente(Utente u, String newPath) throws SQLException{
        PreparedStatement stm = con.prepareStatement
            ("UPDATE UTENTE SET AVATAR = ? WHERE ID = ?");
        try {
            stm.setString(1, newPath);
            stm.setInt(2, u.getId());
            stm.executeUpdate();
        } finally {stm.close();}
    }
    
    /**
     * Se l'utente modifica la password, prendo l'utente e la nuova password e UPDATE
     */
    public static void editPasswordUtente(Utente u, String newPassword) throws SQLException{
        PreparedStatement stm = con.prepareStatement
            ("UPDATE UTENTE SET PASSWORD = ? WHERE ID = ?");
        try {
            stm.setString(1, newPassword);
            stm.setInt(2, u.getId());
            stm.executeUpdate();
        } finally {stm.close();}
    }

    /**
     * Se l'utente modifica il nome prendo in input l'utente e il nuovo nome e UPDATE
     */
    public static void editNomeUtente(Utente u, String newName) throws SQLException{
        PreparedStatement stm = con.prepareStatement
            ("UPDATE UTENTE SET USERNAME = ? WHERE ID = ?");
        try {
            stm.setString(1, newName);
            stm.setInt(2, u.getId());
            stm.executeUpdate();
        } finally {stm.close();}
    }
    
    
    /**
     * preso in input un utente lo elimino da LISTA e da gruppo.
     * Inserire un post all'interno del gruppo che fa vedere che l'utente e' stato eliminato
     */
}
