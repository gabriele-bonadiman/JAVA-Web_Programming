package servlet;

import classi.Gruppo;
import classi.Lista;
import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class listaInviti extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String utente = (String) session.getAttribute("username");
        Utente ute = (Utente) session.getAttribute("utente");

        List<Lista> listaInviti = DBManager.listaGruppiUtente(ute.getId());
        
        try (PrintWriter out = response.getWriter()) {
             out.println("<!DOCTYPE html>");
            out.println("<!--");
            out.println("To change this license header, choose License Headers in Project Properties.");
            out.println("To change this template file, choose Tools | Templates");
            out.println("and open the template in the editor.");
            out.println("-->");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Inviti</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <table class=\"grouptable\"  cellspacing=\"0\">");
            out.println("                <tbody>");
            out.println("                    <tr class=\"titlerow\">");
            out.println("                        <th>Gruppo</th>");
            out.println("                        <th>Proprietario</th>");
            out.println("                        <th class=\"nbr\">Iscritto</th>");
            out.println("                    </tr>");
            out.println("                    <tr>");
            
            Iterator i = listaInviti.iterator(); 
            while(i.hasNext()) {
                
                Lista lista = (Lista) i.next();
                Gruppo gr = DBManager.searchGruppoById(lista.getGruppo());
                
                if (gr.getProprietario() != ute.getId()){
                    out.println("                    <tr>");
                    out.println("                        <td>"+gr.getNome()+"</td>");
                    out.println("                        <td>"+gr.getProprietario()+"</td>");
                    out.println("                        <td class=\"nbr\">");
                    if(lista.getInvitato() == 1){
                            out.println("<div class=\"custom-container\">\n" +
"                                           <input type=\"checkbox\" class=\"custom-checkbox\" id=\"" +lista.getID()+ "\"  checked />\n" +
"                                           <label class=\"custom-label\" for=\"" +lista.getID()+ "\" > </label>\n" +
"                                       </div>");
                    }else{
                            out.println("<div class=\"custom-container\">\n" +
"                                           <input type=\"checkbox\" class=\"custom-checkbox\" id=\"" +lista.getID()+ "\" />\n" +
"                                           <label class=\"custom-label\" for=\"" +lista.getID()+ "\" > </label>\n" +
"                                       </div>");
                    }
                    
                    out.println("                    </td></tr>");
                }
            }
            
            out.println("                    <tr>");
            out.println("                        <td class=\"endtd\"></td>");
            out.println("                        <td class=\"endtd\"></td>");
            out.println("                        <td class=\"nbr endtd\"></td>");
            out.println("                    </tr>");
            out.println("                </tbody>");
            out.println("            </table>");
            out.println("            <button class=\"retbtn\"><a href=\"Home\"> TORNA ALLA HOME </a></button>");
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");
            out.println("");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(listaInviti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(listaInviti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
