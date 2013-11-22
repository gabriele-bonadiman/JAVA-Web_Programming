package services;

import classi.Gruppo;
import classi.Post;
import classi.Utente;
import static database.DBManager.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MetodiPost {
    

    
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
     * Preso in input un gruppo, restituisce il numero di post associato
     */
    public static int numeroPostGruppo(Gruppo g) throws SQLException{
       PreparedStatement stm = con.prepareStatement
            ("select * from POST where GRUPPO = ?");
        int num=0;
        try {
             stm.setInt(1,g.getID());
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
                    p.setData(rs.getString("data"));
                    p.setGruppo(rs.getInt("gruppo"));
                    p.setTesto(rs.getString("testo"));
                    p.setUtente(rs.getInt("utente"));
                   postGr.add(p);
               }
           } finally { rs.close();}
       } finally {stm.close();}
       return postGr;
    }
    
    /**
     * Preso in input utente,gruppo e testo, inserisce il post nel db
     */
    public static void insertPost(Gruppo g, Utente u, String testo) throws SQLException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO POST (DATA,TESTO,UTENTE,GRUPPO) VALUES (?,?,?,?)");

        try {
            stm.setString(1, dateFormat.format(date));
            stm.setString(2, testo);
            stm.setInt(3, u.getId());
            stm.setInt(4, g.getID());
            stm.executeUpdate();
                    System.out.println("INSERITO IL TESO");
        }finally {stm.close();}
    }
}
