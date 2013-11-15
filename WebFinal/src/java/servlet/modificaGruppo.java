package servlet;

import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
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

public class modificaGruppo extends HttpServlet {

    List<Utente> utenti;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        /**
         * 
         *          VEDI FOGLIO PER ISTRUZIONI DA FARE IN QUESTA PAGINA
         * 
         * 
         */
        
        
        
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
        String adminGruppo = (String) session.getAttribute("username");

        try { utenti = DBManager.listaUtenti(); 
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
        out.println("            <form>");
        out.println("                <div style=\"font-size: 40px; margin-top: 40px\">");
        out.println("                    Modifica Nome: <input type=\"text\" class=\"stdinput\" maxlength=\"20\">");
        out.println("                </div>");
        out.println("                <div  style=\"font-size: 40px;  float: left;\">");
        out.println("                    Utenti invitati:");
        out.println("                </div>");
        
                                        Iterator i = utenti.iterator(); 
                                            int indiceCheck = 0;
                                            while(i.hasNext()) {
                                                Utente ute = (Utente) i.next();
                                                if(!ute.getUsername().equals(utenteLoggato.getUsername())){
                                                    out.println("<input type=\"checkbox\" name=\""+ ute.getId() +"\" name=\"Gabri\" >"+ ute.getUsername() +"<br>");
                                                    indiceCheck++;
                                                }
                                            }
        
        
        out.println("                </div>");
        out.println("            </form>");
        out.println("        </div>");
        out.println("    </body>");
        out.println("</html>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
