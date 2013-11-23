
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
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        
        int stop = 0;
        int id_group = 0;
        
        //String paramName;
        //Enumeration paramNames = request.getParameterNames();
        
            Enumeration paramNames = request.getParameterNames();
            
            try{
                while(paramNames.hasMoreElements()) {
                    String paramName = (String)paramNames.nextElement();
                    String[] paramValues = request.getParameterValues(paramName);

                    if(stop==0){
                            String paramValue = paramValues[0];
                            System.out.print("creo il gruppo con il nome= " + paramValue);
                            id_group = MetodiGruppi.creaGruppo(paramValue, ute);
                            MetodiGruppi.inserisciInLista(ute, id_group);
                            MetodiGruppi.inserisciInInviti(ute, id_group,1);
                            stop++;
                    }
                    if(!paramName.equals("nomeGruppo")){
                            System.out.println("UTENTE NUMERO = " + paramName);
                            Utente u = MetodiUtenti.searchUtenteByID(Integer.parseInt(paramName));
                            MetodiGruppi.inserisciInLista(u, id_group);
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