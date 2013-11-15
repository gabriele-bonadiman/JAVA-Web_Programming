/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Babol
 */
public class iMieiDati extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        //session
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        //request
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String nomeModificato = request.getParameter("nomeModificato");

        
        if( nomeModificato!=null && password.equals(passwordConfirm)){
            request.setAttribute("nomeModificato", nomeModificato);
            request.setAttribute("password", password);
            RequestDispatcher rd = request.getRequestDispatcher("/iMieiDatiAppoggio");
            rd.forward(request, response);
            return;
        }
        
        
        try (PrintWriter out = response.getWriter()) {
           out.println("<!DOCTYPE html>");
            out.println("<!--");
            out.println("To change this license header, choose License Headers in Project Properties.");
            out.println("To change this template file, choose Tools | Templates");
            out.println("and open the template in the editor.");
            out.println("-->");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Miei dati</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <form>");
            out.println("                <div style=\"font-size: 40px; margin-top: 40px;\">");
            out.println("                    Modifica Nome: <input name=\"nomeModificato\" placeholder="+ utente.getUsername()+" type=\"text\" class=\"stdinput\">");
            out.println("                </div>");
            out.println("                <div style=\"font-size: 40px;\">");
            out.println("                    <div style=\"float: left;\">Modifica Password:</div>");
            out.println("                    <div style=\"float: left; width: 310px;\">");
            out.println("                        <input name=\"password\" type=\"password\" class=\"stdinput\">");
            out.println("                        <input name=\"passwordConfirm\" type=\"password\" class=\"stdinput\">");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("                <div style=\"width: 800px;\">");
            out.println("                    <div style=\"font-size: 40px; float:left;\">");
            out.println("                        Modifica Avatar:");
            out.println("                    </div>");
            out.println("                    <div  style=\"float: left;\">");
            out.println("                        <image src=\"Images/singleicon.png\" style=\"height:150px; width: 150px; margin-left: 30px\">");
            out.println("                        <button class=\"stdsbmt\">Carica</button>");
            out.println("                    </div>    ");
            out.println("                </div>");
            out.println("                <input type=\"submit\" class=\"retbtn\" value=\"Salva\">");
            out.println("            </form>");
            out.println("            ");
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
