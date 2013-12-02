package servlet;

import classi.Gruppo;
import classi.Lista;
import classi.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;

public class listaInvitiAppoggio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");

        //recupero la lista dei gruppi al quale l'ute e' iscritto
        List<Lista> listaInviti = MetodiGruppi.listaGruppiUtente(ute.getId());
        
        //se la lista non e' vuota stampo i riusltati trovati
        if (!listaInviti.isEmpty()) {
            Iterator i = listaInviti.iterator();
            while (i.hasNext()) {
                Lista lista = (Lista) i.next();
                //creo un gruppo in base al valore contenuto nella colonna GRUPPO di LISTA
                Gruppo gr = MetodiGruppi.searchGruppoById(lista.getGruppo());
                //se il proprietario del gruppo e' diverso dall'utente in sessione
                if (gr.getProprietario() != ute.getId()) {
                    if ("on".equals(request.getParameter(gr.getNome()))) {
                        MetodiGruppi.editIscrizione(ute.getId(), gr.getID(), 1);
                        MetodiGruppi.inserisciInLista(ute, gr.getID());
                    } else {
                        MetodiGruppi.editIscrizione(ute.getId(), gr.getID(), 0);
                        MetodiGruppi.eliminaDallaLista(ute, gr.getID());
                    }
                }
            }        } else{ 
            response.sendRedirect("Home");
        }
        response.sendRedirect("Home");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(listaInvitiAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(listaInvitiAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
    }

    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
