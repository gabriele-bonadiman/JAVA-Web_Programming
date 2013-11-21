package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        
        
        Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            Boolean dateCookieBool=false;
            String dateToShow=null;

            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("dateToShow")) {
                    dateToShow = cookie.getValue();
                    dateCookieBool=true;
                }
            }
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
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
            if (dateCookieBool==true) {
                out.println(dateToShow);
            } else {
                out.println("                Non sono disponibili dati relativi all'ultimo accesso");
            }
            out.println("            </div>");
            out.println("            <div class=\"dummy\"></div>");
            out.println("            <li class=\"menu center\">");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/inviteicon.png\" alt=\"\" class=\"menuimg\"/><a href=\"listaInviti\">Inviti</a></ul>");
            out.println("                <ul class=\"menuitem\"><img src=\"Images/groupicon.png\" alt=\"\" class=\"menuimg\"/><a href=\"Gruppi\">Gruppi</a></ul>");
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
        processRequest(request, response);
    }

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
