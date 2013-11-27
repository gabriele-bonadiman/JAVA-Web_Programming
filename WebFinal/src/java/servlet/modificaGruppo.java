package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
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

        String paramName = null;
        //String paramName = (String )request.getParameter("nomeGruppo");
        Enumeration paramNames = request.getParameterNames();
        
        while(paramNames.hasMoreElements()) {
            paramName = (String)paramNames.nextElement();
        }
        
        System.err.println(paramName);
        
        Gruppo gr = MetodiGruppi.searchGruppoById(Integer.parseInt(paramName));
        session.setAttribute("gruppoCorrente", gr);
        
        try { 
            utentiNonIscritti = MetodiGruppi.listaUtentiNonIscritti(gr);            
            utentiIscritti = MetodiGruppi.listaUtentiIscritti(gr);
            utentiInvitati = MetodiGruppi.listaUtentiSenzaAccettazione(gr);
        } catch (SQLException ex) {Logger.getLogger(creaGruppo.class.getName()).log(Level.SEVERE, null, ex);}

        
        out.println("<!DOCTYPE html>");
        out.println("<!--");
        out.println("To change this license header, choose License Headers in Project Properties.");
        out.println("To change this template file, choose Tools | Templates");
        out.println("and open the template in the editor.");
        out.println("-->");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>Modifica Gruppo</title>");
        out.println("        <meta charset=\"UTF-8\">");
        out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
        out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <div class=\"container\">");
        out.println("            <form  action=\"modificaGruppoAppoggio\" method=\"POST\">");
        out.println("                <div style=\"font-size: 40px; margin-top: 40px\">");
        out.println("                    Modifica Nome: "
                + "                     <input name=nuovoNome type=\"text\" placeholder="+gr.getNome()+" class=\"stdinput\" maxlength=\"20\">");
        out.println("                </div>");
        
        
        
        out.println("                <div  style=\"font-size: 40px;  float: left;\">");
        out.println("                    Utenti NON ISCRITTI AL GRUPPO:<br/>");

        Iterator i = utentiNonIscritti.iterator(); 
        while(i.hasNext()) {
            Utente ute = (Utente) i.next();
            if(!utentiIscritti.contains(ute)){
                if(!ute.getUsername().equals(utenteLoggato.getUsername())){
                    out.println("<input type=\"checkbox\" name=\""+ ute.getId() +"\" name=\"Gabri\" checked>"+ ute.getUsername() +"<br>");
                }
            }
        }
        out.println("                </div><br/><br/>");
        
        
        
        
        out.println("                <div  style=\"font-size: 40px;  float: left;\">");
        out.println("                    UTENTI ISCRITTI AL GRUPPO:<br/>");
        if(utentiIscritti.isEmpty()){
            out.println("nessuno ha ancora accettato l'invito a questo gruppo");
        }else{
            Iterator i2 = utentiIscritti.iterator(); 
            while(i2.hasNext()) {
                Utente ute2 = (Utente) i2.next();
                if(!ute2.getUsername().equals(utenteLoggato.getUsername())){
                    out.println("<input type=\"checkbox\" name=\""+ ute2.getId() +"\" name=\"Gabri\">"+ ute2.getUsername() +"<br>");
                }
            }
        }
        
        
        out.println(" <br/><br/>                   UTENTI che non HANNO HANNO ANCORA ACCETTATO L'INVITO:<br/>");
        if(utentiInvitati.isEmpty()){
            out.println("nessuno ha ancora accettato l'invito a questo gruppo");
        }else{
            Iterator i3 = utentiInvitati.iterator(); 
            while(i3.hasNext()) {
                Utente ute3 = (Utente) i3.next();
                if(!ute3.getUsername().equals(utenteLoggato.getUsername())){
                    out.println("<input type=\"checkbox\" name=\""+ ute3.getId() +"\" name=\"Gabri\" >"+ ute3.getUsername() +"<br>");
                }
            }
        }       
        
        
        out.println("<br/><br/><input type=\"submit\" value=\"conferma\"/></br>");      
        out.println("                </div>");
        out.println("            </form>");
        out.println("        </div>");
        out.println("    </body>");
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
