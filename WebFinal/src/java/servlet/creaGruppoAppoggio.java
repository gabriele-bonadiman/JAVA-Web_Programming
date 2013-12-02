
package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;
import services.MetodiUtenti;

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
        
        //parameters
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        int stop = 0;
        int id_group = 0;
        
        request.setCharacterEncoding("UTF-8");
        Enumeration paramNames = request.getParameterNames();
        try{
            while(paramNames.hasMoreElements()) {
                String paramName = (String)paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);

                //il primo parametro che ricevo e' il nome del gruppo
                if(stop==0){
                    String paramValue = paramValues[0];
                    if(paramValue.equals("") || paramValue.equals(" ")){
                        response.sendRedirect("GruppoVuoto");
                        return;
                    }
                    id_group = MetodiGruppi.creaGruppo(paramValue, ute);
                    MetodiGruppi.inserisciInLista(ute, id_group);
                    MetodiGruppi.inserisciInInviti(ute, id_group,1);
                    stop++;
                }
                if(!paramName.equals("nomeGruppo")){
                    Utente u = MetodiUtenti.searchUtenteByID(Integer.parseInt(paramName));
                    MetodiGruppi.inserisciInInviti(u, id_group,0);
                }
            } 
        }catch (SQLException ex) {Logger.getLogger(creaGruppoAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
        response.sendRedirect("Home");
       
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}