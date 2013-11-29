package servlet;

import classi.Gruppo;
import classi.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class modificaGruppoElimina extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        int idu = 0;
        int idg = 0;
        int i=0;
    
        Enumeration paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if(i==0){
                idu = Integer.parseInt(paramValues[0]);
            }
            else{
                idg = Integer.parseInt(paramValues[0]);
            }
            i++;
        }
        Utente u = services.MetodiUtenti.searchUtenteByID(idu);
        Gruppo g = services.MetodiGruppi.searchGruppoById(idg);
        System.err.println("utente " + u.getUsername()  + " dal gruppo " + g.getNome());
                
        services.MetodiUtenti.deleteUtenteFromGroup(u, g);
        response.sendRedirect("Gruppi");

    
    }
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(modificaGruppoElimina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(modificaGruppoElimina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
