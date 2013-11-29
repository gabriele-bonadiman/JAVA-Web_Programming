package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import classi.Gruppo;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;

public class modificaGruppo extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
 

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
        String adminGruppo = (String) session.getAttribute("username");
        
        ArrayList<Utente> utentiNonIscritti = null;
        ArrayList<Utente> utentiIscritti = null;
        ArrayList<Utente> utentiInvitati = null;

        //String paramName = null;
        String paramName = (String) request.getParameter("nomeGruppo");
        /*Enumeration paramNames = request.getParameterNames();
        
        while(paramNames.hasMoreElements()) {
            paramName = (String)paramNames.nextElement();
        }
        */
        System.err.println(paramName);
        
        Gruppo gr = MetodiGruppi.searchGruppoById(Integer.parseInt(paramName));
        session.setAttribute("gruppoCorrente", gr);
        
        try { 
            utentiNonIscritti = MetodiGruppi.listaUtentiNonIscritti(gr);            
            utentiIscritti = MetodiGruppi.listaUtentiIscritti(gr);
            utentiInvitati = MetodiGruppi.listaUtentiSenzaAccettazione(gr);
        } catch (SQLException ex) {Logger.getLogger(creaGruppo.class.getName()).log(Level.SEVERE, null, ex);}

        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>Modifica Gruppo</title>");
        out.println("        <meta charset=\"UTF-8\">");
        out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
        out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <div class=\"container\">");
        out.println("           <div class=\"col-md-offset-2 col-md-8\" style='margin-bottom:50px;'>");
        out.println("               <h1>Modifica Gruppo</h1>");
        out.println("           </div>");
        out.println("           <div class='col-md-10 col-md-offset-1'>");
        out.println("               <form  action=\"modificaGruppoAppoggio\" method=\"POST\">");
        out.println("                   <div class=\"col-md-offset-2 col-md-8\" style='margin-bottom:20px;'>");
        out.println("                       <label class=\"col-md-2 col-md-offset-1\" for=\"nome\">Nome:</label> <div class=\"col-md-3\"><input type=\"text\" id=\"nome\"  name=\"nuovoNome\" placeholder="+ gr.getNome()+"  class=\"form-control\"></div>");
        out.println("                   </div>");
        out.println("                   <div class='col-md-12'>");
        
        
        
        
        //Primo Blocco
        out.println("                       <div class='col-md-4'>");
        out.println("                           <div class='col-md-12'>");
        out.println("                               <label>Utenti non iscritti al gruppo:</label>");
        out.println("                           </div>");
        out.println("                           <div class='col-md-12'>");
        

        Iterator i = utentiNonIscritti.iterator(); 
        while(i.hasNext()) {
            Utente ute = (Utente) i.next();
            if(!utentiIscritti.contains(ute)){
                if(!ute.getUsername().equals(utenteLoggato.getUsername())){
                    out.println("<div class='row'><div class='col-md-2 col-md-offset-2'><input type=\"checkbox\" name=\""+ ute.getId() +"\" id=\""+ ute.getId() +"\" name=\"Gabri\" ></div><label for=\""+ ute.getId() +"\">"+ ute.getUsername() +"</label></div>");
                }
            }
        }
        out.println("                           </div>");
        out.println("                       </div>");
        
        
        //Secondo Blocco
        out.println("                       <div class='col-md-4'>");
        out.println("                           <div class='col-md-12'>");
        out.println("                               <label>Utenti iscritti al gruppo:</label>");
        out.println("                           </div>");
        out.println("                           <div class='col-md-12'>");
        out.println("                               <ul class='col-md-offset-1'>");
        if(utentiIscritti.isEmpty()){
            out.println("<div style='margin-top:10px;'>Nessuno è iscritto a questo gruppo</div>");
        }else{
            Iterator i2 = utentiIscritti.iterator(); 
            out.println("                           <ul class='col-md-offset-1'>");
            while(i2.hasNext()) {
                Utente ute2 = (Utente) i2.next();
                if(!ute2.getUsername().equals(utenteLoggato.getUsername())){
                    out.println("<a href='modificaGruppoElimina?id="+ ute2.getId() +"&gr="+gr.getID()+"' values=\""+ ute2.getId() +"\"><li name=\""+ ute2.getId() +"\">"+ ute2.getUsername() +"</li></a>");}
                    out.println("                           </ul>");
            }
        }	
        out.println("                               </ul>");
        out.println("                           </div>");
        out.println("                       </div>");
        
        
        
        //Terzo Blocco
        out.println("                       <div class='col-md-4'>");
        out.println("                           <div class='col-md-12'>");
        out.println("                               <label>Utenti che non hanno ancora accettato l'invito:</label>");
        out.println("                           </div>");
        out.println("                           <div class='col-md-12'>");
        if(utentiInvitati.isEmpty()){
            out.println("<div style='margin-top:10px;'>Nessuno ha ancora accettato l'invito a questo gruppo</div>");
        }else{
            Iterator i3 = utentiInvitati.iterator(); 
            out.println("                           <ul class='col-md-offset-1'>");
            while(i3.hasNext()) {
                Utente ute3 = (Utente) i3.next();
                if(!ute3.getUsername().equals(utenteLoggato.getUsername())){
                    out.println("                       <li  name=\""+ ute3.getId() +"\">"+ ute3.getUsername() +"</li>");
                }
            out.println("                           </ul>");
            }
        }
        out.println("                           </div>");
        out.println("                       </div>");
        
        
        
        
        
        out.println("                   </div>");
        
        out.println("                   <div class='col-md-3 col-md-offset-3' style='margin-top:40px;'><input type='submit' class='btn btn-default' value='Conferma'></div>");
        
        out.println("               </form>");
        out.println("           <div class='col-md-3 col-md-offset-3' style='margin-top:-33px;'><a href='Indietro'><button class='btn btn-primary'>Home</button></a></div>");
        out.println("           </div>");    
        //container
        out.println("       </div>");
        out.println("   </body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(modificaGruppo.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(modificaGruppo.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
