package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiUtenti;

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
            
            
            try { utenti = MetodiUtenti.listaUtenti();
            } catch (SQLException ex) {Logger.getLogger(creaGruppo.class.getName()).log(Level.SEVERE, null, ex);}

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Creazione del Gruppo</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("           <form action=\"creaGruppoAppoggio\" method=\"POST\" class=\"form-horizontal\">");
            out.println("               <div class=\"col-md-offset-2 col-md-8\">");
            out.println("                   <h1>Crea Nuovo Gruppo</h1>");
            out.println("               </div>");
            out.println("               <div class=\"col-md-offset-3 col-md-8\" style=\"margin-top:40px;\">");
            out.println("                <div class=\"form-group\">");
            out.println("                   <label for=\"nomegruppo\" class=\"col-md-3\">Nome Gruppo:</label> ");                       
            out.println("                   <div class=\"col-md-3\"><input name=\"nomeGruppo\" id=\"nomegruppo\" class=\"form-control\"/></div>");
            out.println("                </div>");
            out.println("                <div  class=\"col-md-3\" style=\"margin-left:-15px;\">");
            out.println("                    <label>Utenti presenti:</label>");
            out.println("                </div>");
            out.println("                <div class=\"col-md-9\" style=\"margin-bottom:20px;\">");
            
                                            Iterator i = utenti.iterator(); 
                                            int indiceCheck = 0;
                                            while(i.hasNext()) {
                                                Utente ute = (Utente) i.next();
                                                if(!ute.getUsername().equals(utenteLoggato.getUsername())){
                                                    out.println("<input type=\"checkbox\" name=\""+ ute.getId() +"\" id=\""+ ute.getId() +"\" /><label for=\""+ ute.getId() +"\" style=\"margin-left:10px;\">"+ ute.getUsername() +"</label><br>");
                                                    indiceCheck++;
                                                }
                                            }
            out.println("                       </div>");
            out.println("                   <div class=\"form-group\">");
            out.println("                       <input type=\"submit\" value=\"Aggiungi\" class=\"btn btn-default\">");
            out.println("                   </div>");
            out.println("               </div>");
            out.println("            </form>");
            out.println("          <a href=\"Home\"><button action=\"Home\" class=\"btn btn-default col-md-offset-3\">Home</button></a>");

            
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
