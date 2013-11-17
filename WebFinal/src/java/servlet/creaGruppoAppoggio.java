
package servlet;

import classi.Gruppo;
import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class creaGruppoAppoggio extends HttpServlet {

    List<Utente> utenti;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        
        int stop = 0;
        int id_group = 0;
        String paramName;
        
        Enumeration paramNames = request.getParameterNames();
        try {
            while(paramNames.hasMoreElements()) {

                paramName = (String)paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);

                // creazione del gruppo
                if(stop==0){
                    out.println("GRUPPO --- " + paramValues[0]);
                    id_group = DBManager.creaGruppo(paramValues[0], ute);
                    DBManager.inserisciInLista(ute, id_group);  //per inserire chi crea
                    DBManager.inserisciInInviti(ute, id_group,1);
                    stop++;
                }else{
                    out.println("ID UTENTE --- " + paramName);
                    Utente u = DBManager.searchUtenteByID(Integer.parseInt(paramName));
                    DBManager.inserisciInLista(u, id_group);
                    DBManager.inserisciInInviti(u, id_group,0);
                }
            }
        } catch (SQLException ex) {Logger.getLogger(creaGruppoAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
    }


        
        /*
        ArrayList<Utente> utenti = (ArrayList<Utente>) request.getAttribute("listaUtenti");
        String nomeGruppo = request.getParameter("nomeGruppo");
        String admin = (String) session.getAttribute("username");

        Iterator i = utenti.iterator(); 
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet creaGruppoAppoggio</title>");            
        out.println("</head>");
        out.println("<body>");
        
        while(i.hasNext()) {
           Utente ute = (Utente) i.next();
           out.println("<input type=\"checkbox\" name=\"invites\" value=\"Gabri\">"+ ute.getUsername() +"<br>");
        }
        
        out.println("</body>");
        out.println("</html>");
            
        
        //ricavo l'ID amministratore e creo il gruppo
        try {
                Utente amministratore = DBManager.idUtente(admin);
                DBManager.creaGruppo(nomeGruppo, amministratore,utenti);
            }catch (SQLException ex) {Logger.getLogger(creaGruppoAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
        */


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
