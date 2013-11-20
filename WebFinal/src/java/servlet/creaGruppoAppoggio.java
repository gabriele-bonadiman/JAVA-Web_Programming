
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

                // creazione del gruppo. Prendo il nome(il primo parametro)
                if(stop==0){
                    out.println("GRUPPO --- " + paramValues[0]);
                    if(paramValues[0].equals("")){
                        creaGruppo.errorName=true;
                        response.sendRedirect("creaGruppo");
                        return;
                    }
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
       
            response.sendRedirect("Home");
       
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}