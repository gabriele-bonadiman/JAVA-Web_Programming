package servlet;

import classi.Gruppo;
import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;
import services.MetodiUtenti;

public class modificaGruppoAppoggio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
        Gruppo gr = (Gruppo) session.getAttribute("gruppoCorrente");
        String nuovoNome = (String) request.getParameter("nuovoNome");
       
        try {
            if(nuovoNome.equals("") || nuovoNome==null){
                nuovoNome = gr.getNome();
            }
            MetodiGruppi.editNomeGruppo(gr, nuovoNome);
        }catch (SQLException ex) {Logger.getLogger(modificaGruppoAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
       
        
        //aggunta di nuove persone all'interno del gruppo
        Enumeration paramNames = request.getParameterNames();
        int index = 0;
        try {
            while(paramNames.hasMoreElements()) {
                String paramName = (String)paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if(index!=0){
                    Utente u = MetodiUtenti.searchUtenteByID(Integer.parseInt(paramName));
                    MetodiGruppi.inserisciInLista(u, gr.getID());
                    MetodiGruppi.inserisciInInviti(u, gr.getID(),0);
                }
                index++;
            }
        }catch (SQLException ex) {Logger.getLogger(modificaGruppoAppoggio.class.getName()).log(Level.SEVERE, null, ex);}

        session.removeAttribute("gruppoCorrente");
        response.sendRedirect("Gruppi");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
