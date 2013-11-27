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
    public static String dataUltimoPostUtente(Utente u, Gruppo gr) throws SQLException{
        String data;
        PreparedStatement stm = con.prepareStatement
            ("select * from POST where GRUPPO = ? AND UTENTE = ? ORDER BY id DESC");
        int num=0;
        try {
             stm.setInt(1,gr.getID());
             stm.setInt(2, u.getId());
             ResultSet rs = stm.executeQuery();
            try {
                if(rs.next()) {
                    data = rs.getString("data");
                }else{
                    return null;
                }
            } finally { rs.close();}
        } finally {stm.close();}
        return data;
    }
    
    /**
     * Preso in input un gruppo restituisco data ultimo post in quel gruppo
     */
    public static String dataUltimoPost(Gruppo gr) throws SQLException{
        String data;
        PreparedStatement stm = con.prepareStatement
            ("select * from POST where GRUPPO = ? ORDER BY id DESC");
        try {
             stm.setInt(1,gr.getID());
             ResultSet rs = stm.executeQuery();
            try {
                if(rs.next()) {
                    data = rs.getString("data");
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
        ("SELECT * FROM POST WHERE GRUPPO = ? ORDER BY ID DESC");
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
        }finally {stm.close();}
    }
    
    public static boolean fileExist(String name) throws SQLException{
          PreparedStatement stm = con.prepareStatement
            ("select * from POST_FILE where path = ?");
        int num=0;
        try {
             stm.setString(1,name);
             ResultSet rs = stm.executeQuery();
             while(rs.next()) {
                    return true;
                }
        } finally {stm.close();}
        return false;
    }
    
    
    /**
     * Preso in input i parametri necessari, inserisce un file all'interno del DB
     */
    public static void insertFileIntoDB(String path,int utente, int gruppo) throws SQLException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO FILE_POST (PATH,UTENTE,GRUPPO,DATA) VALUES (?,?,?,?)");

        try {
            stm.setString(1, path);
            stm.setInt(2, utente);
            stm.setInt(3, gruppo);
            stm.setString(4, dateFormat.format(date));
            stm.executeUpdate();
        }finally {stm.close();}
    }
    
    public static ArrayList<Post> returnData(Utente u) throws SQLException{
        ArrayList<Post> listaPost = new ArrayList<Post>();
             PreparedStatement stm = con.prepareStatement
                ("select * from POST where gruppo IN (select gruppo from lista where utente = ?)");
        try {
            stm.setInt(1, u.getId());
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
               Post p = new Post();
               p.setUtente(rs.getInt("Utente"));
               p.setGruppo(rs.getInt("Gruppo"));
               p.setData(rs.getString("Data"));
               listaPost.add(p);
            }
        } finally {stm.close();}
        return listaPost;
    }
}
