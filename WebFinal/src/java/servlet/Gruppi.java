
package servlet;

import classi.Gruppo;
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
import org.jboss.weld.context.http.Http;

public class Gruppi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession();
            Utente ute = (Utente) session.getAttribute("utente");
            List<Integer> gruppi = DBManager.listaGruppiIscritto(ute.getId());
            
            out.println("<!DOCTYPE html>");
            out.println("<!--");
            out.println("To change this license header, choose License Headers in Project Properties.");
            out.println("To change this template file, choose Tools | Templates");
            out.println("and open the template in the editor.");
            out.println("-->");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Gruppi</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <table class=\"grouptable\"  cellspacing=\"0\">");
            out.println("                <tbody>");
            out.println("                    <tr class=\"titlerow\">");
            out.println("                        <th>Gruppi</th>");
            out.println("                        <th>Data</th>");
            out.println("                        <th>Link</th>");
            out.println("                        <th class=\"nbr\"><img src=\"Images/downloadicon.png\"></th> ");
            out.println("                    </tr>");
            
            
            
            
            
            
            
            Iterator i = gruppi.iterator(); 
                    while(i.hasNext()) {
                        int ID = (int) i.next();
                        Gruppo g = DBManager.searchGruppoById(ID);
                        out.println("                    <tr>");
                        
                        
                        //solo se sono il proprietario risulta clicckabil eil link per andare a modifica gruppo
                        if(g.getProprietario() == ute.getId()){
                            out.println("");
                            out.println("<td>"+ g.getNome()+ "");
                            out.println("<form  action=\"modificaGruppo\" >");
                            out.println("<input type=\"submit\" value=\"\" name=\""+g.getID()+"\" style=\" float:left; background-image: url(Images/miniediticon.png); height: 30px; width: 30px; background-repeat: no-repeat; border-style: none;\"></br>");      
                            out.println("</form></td>");

                        }
                        else{
                            out.println("                        <td>"+ g.getNome()+ "</td>");
                        }
                        
                        out.println("                        <td>"+g.getData_creazione()+"</td>");
                        out.println("                        <td> LINK PORCA TROIA</td>");
                        out.println("                        <td class=\"nbr\">PDF</td>");
                        out.println("                    </tr>");
                    }
            
            
            out.println("                </tbody>");
            out.println("            </table>");
            out.println("            <button class=\"retbtn\"><a href=\"Home\">Fine</a></button>");
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");
            out.println("");

        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Gruppi.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Gruppi.class.getName()).log(Level.SEVERE, null, ex);
        }
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
