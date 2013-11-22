package services;

import classi.Gruppo;
import classi.Lista;
import classi.Utente;
import database.DBManager;
import static database.DBManager.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class servicesGruppi {
    
    public servicesGruppi(){}
     
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
     *  Preso in input un id, restituisce l'oggetto gruppo al quale e' associato
     */
    public static Gruppo searchGruppoById(int ID) throws SQLException{
        
        System.out.println("  ENTRO NEL METODO BANDITO E TI ATTACCHI AL CAZZOOOOO ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        PreparedStatement stm = con.prepareStatement
            ("SELECT * FROM GRUPPO WHERE ID = ?");
        Gruppo g = new Gruppo();
        try {
            stm.setInt(1, ID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {        
                g.setID(rs.getInt("ID"));
                g.setNome(rs.getString("NOME"));
                g.setProprietario(rs.getInt("PROPRIETARIO"));
                g.setData_creazione(rs.getString("DATACREAZIONE"));
            }
        } finally {stm.close();}
        return g;
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
                   p = services.servicesUtenti.searchUtenteByID(rs.getInt("UTENTE"));
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
        PreparedStatement stm = con.prepareStatement
        ("select id from utente except(select invito.utente from invito where gruppo = ?)");
        ArrayList<Utente> utenti = new ArrayList<Utente>();
       try {
            stm.setInt(1,gr.getID());
            ResultSet rs = stm.executeQuery();
           try {
               while(rs.next()) {
                   Utente p;
                   p = services.servicesUtenti.searchUtenteByID(rs.getInt(1));
                   utenti.add(p);
               }
           } finally { rs.close();}
       } finally {stm.close();}
       return utenti;
    }
    
    /**
     *  Preso in input id utente,idGruppo e scelta
     * o meno ad un gruppo.
     *  
     * 0 = sono iscritto
     * 1 = non sono iscritto
     */
    public static void editIscrizione(int idUtente,int idGruppo,int choose) throws SQLException{

        PreparedStatement stm = con.prepareStatement("UPDATE INVITO SET invitato = ? WHERE UTENTE = ? AND GRUPPO = ?");
        try {
            stm.setInt(1, choose);
            stm.setInt(2, idUtente);
            stm.setInt(3, idGruppo);
            stm.executeUpdate();
        } finally {stm.close();}
        
    }
    
    /**
     * Una volta creato un gruppo manda a tutti i membri l'invito
     */
    public static void inserisciInInviti(Utente u, int IDgruppo,int choose) throws SQLException{
        System.out.println("INSERISCO IN INVITI" + u.getUsername() +IDgruppo +choose);
        PreparedStatement stm = con.prepareStatement
            ("INSERT INTO INVITO (UTENTE,GRUPPO,INVITATO) VALUES (?,?,?)");
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
    
}