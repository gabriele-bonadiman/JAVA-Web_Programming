package servlet;

import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import classi.Gruppo;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class modificaGruppo extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
 

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
        String adminGruppo = (String) session.getAttribute("username");
        
        List<Utente> utentiMancanti = null;
        List<Utente> utentiPresenti = null;
        
        String paramName = null;
        Enumeration paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            paramName = (String)paramNames.nextElement();
        }
        Gruppo gr = DBManager.searchGruppoById(Integer.parseInt(paramName));
        session.setAttribute("gruppoCorrente", gr);
        
        try { 
            
            
            /**
             *  FARE QUELLA CAZZO DI FUNZIONE CHE ESCLUDE GLI UTENTI GIA PRESENTI
             */
            
            
            
            
            utentiMancanti = DBManager.listaPotenzialiIscritti(gr);
            utentiPresenti = DBManager.listaUtentiPresenti(gr);
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
        out.println("                    Utenti PRESENTI NEL DB:");
        out.println("                </div>");
        
        Iterator i = utentiMancanti.iterator(); 
        while(i.hasNext()) {
            Utente ute = (Utente) i.next();
            if(!ute.getUsername().equals(utenteLoggato.getUsername())){
                out.println("<input type=\"checkbox\" name=\""+ ute.getId() +"\" name=\"Gabri\" >"+ ute.getUsername() +"<br>");
            }
        }

                                            
                    /**
                     *      QUI AL POSTO DI UNA CHECKBOX CI CACCEREI UNA X DA PREMERE PER ELIMINARE 
                     */
        out.println("                <div  style=\"font-size: 40px;  float: left;\">");
        out.println("                    Utenti PRESENTI NEL GRUPPO:");
        out.println("                </div>");
     
        
        
        Iterator i2 = utentiPresenti.iterator(); 
        while(i2.hasNext()) {
            Utente ute2 = (Utente) i2.next();
            if(!ute2.getUsername().equals(utenteLoggato.getUsername())){
                out.println("<input type=\"checkbox\" name=\""+ ute2.getId() +"\" name=\"Gabri\" checked>"+ ute2.getUsername() +"<br>");
            }
        }
                                            
                                            
                                            
                                            
        out.println("<input type=\"submit\"/></br>");      

        
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
