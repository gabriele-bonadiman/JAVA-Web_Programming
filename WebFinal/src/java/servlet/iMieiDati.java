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


public class iMieiDati extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        //session
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
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
            out.println("            <form action=\"iMieiDatiAppoggio\" method=\"POST\">");
            out.println("                <div style=\"font-size: 40px; margin-top: 40px;\">");
            out.println("                    Modifica Nome: <input name=\"nomeModificato\" placeholder="+ utente.getUsername()+" type=\"text\" class=\"stdinput\">");
            out.println("                </div>");
            out.println("                <div style=\"font-size: 40px;\">");
            out.println("                    <div style=\"float: left;\">Modifica Password:</div>");
            out.println("                    <div style=\"float: left; width: 310px;\">");
            out.println("                        <input name=\"vecchiaPassword\" type=\"password\" class=\"stdinput\">");
            out.println("                        <input name=\"nuovaPassword\" type=\"password\" class=\"stdinput\">");
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
        }    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
