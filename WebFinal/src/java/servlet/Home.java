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

public class Home extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        Utente ute = (Utente) session.getAttribute("utente");
        String nome = ute.getUsername();
        
        
        
        /**
         * 
         * 
         * BACKEND
         *      * VERIFICARE CHE QUANDO CREO UN GRUPPO QUESTO SIA VISUALIZZATO ANCHE NEI MIEI GRUPPI
         * 
         * 
         * 
         * FRONTEND
         *      * INSERIRE DA QUALCHE PARTE LA SEZIONE "MODIFICA GRUPPO"
         * 
         */
        
        
        
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<!--");
            out.println("To change this license header, choose License Headers in Project Properties.");
            out.println("To change this template file, choose Tools | Templates");
            out.println("and open the template in the editor.");
            out.println("-->");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Home</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <div class=\"maintitle\">");
            out.println("                Bentornato "+nome+"!");
            out.println("            </div>");
            out.println("            <div class=\"subtitle\">");
            out.println("                Ultimo accesso il 12/11/2013 alle ore 12.34");
            out.println("            </div>");
            out.println("            <div class=\"dummy\"></div>");
            out.println("            <li class=\"menu\">");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/inviteicon.png\" alt=\"\" class=\"menuimg\"/><a href=\"listaInviti\">Inviti</a></ul>");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/groupicon.png\" alt=\"\" class=\"menuimg\"/>Gruppi</ul>");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/createicon.png\" alt=\"\" class=\"menuimg\"/><a href=\"creaGruppo\">Crea Gruppo</a></ul>");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/singleicon.png\" alt=\"\" class=\"menuimg\"/><a href=\"iMieiDati\">I miei dati</a></ul>");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/logouticon.png\" alt=\"\" class=\"menuimg\"/><a href=\"Logout\">Logout</a></ul>");
            out.println("            </li>");
            out.println("            ");
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("FACIO IL GET");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("FACIO IL POST");
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
