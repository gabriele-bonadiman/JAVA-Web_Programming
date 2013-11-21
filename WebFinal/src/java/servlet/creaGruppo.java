package servlet;

import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class creaGruppo extends HttpServlet {

        List<Utente> utenti;
        public static boolean errorName = false;
    
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        

        
    }
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
            //param
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession();
            Utente utenteLoggato = (Utente) session.getAttribute("utente");
            String adminGruppo = (String) session.getAttribute("username");
            
            
            try { utenti = DBManager.listaUtenti(); 
            } catch (SQLException ex) {Logger.getLogger(creaGruppo.class.getName()).log(Level.SEVERE, null, ex);}

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Creazione del Gruppo</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("           <form action=\"creaGruppoAppoggio\" method=\"POST\">");
            out.println("                <div style=\"font-size: 40px; margin-top: 40px\">");

            // ALTRO FORM DA CONTROLLARE RISPETTO A QEULLO DI GIANNI
            out.println("NOME GRUPPO: ");                                  
            out.println("<input name=\"nomeGruppo\" /><br>");
            
            out.println("                </div>");
            out.println("                <div  style=\"font-size: 40px;  float: left;\">");
            out.println("                    Utenti presenti:");
            out.println("                </div>");
            out.println("                <div style=\"font-size: 30px; float: left; margin-left: 20px; margin-top: 10px;\">");
            
                                            Iterator i = utenti.iterator(); 
                                            int indiceCheck = 0;
                                            while(i.hasNext()) {
                                                Utente ute = (Utente) i.next();
                                                if(!ute.getUsername().equals(utenteLoggato.getUsername())){
                                                    out.println("<input name=gruppo value=\""+ ute.getId() +"\" />"+ ute.getUsername() +"<br>");
                                                    indiceCheck++;
                                                }
                                            }
            out.println("                    <input type=\"submit\" value=\"Aggiungi\" class=\"stdsbmt\">");
            out.println("                </div>");
            out.println("            </form>");
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try { processRequest(request, response);
            } catch (SQLException ex) {Logger.getLogger(creaGruppo.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
