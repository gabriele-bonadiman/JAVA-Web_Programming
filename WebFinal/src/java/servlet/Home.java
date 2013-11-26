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
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("           <div class=\"col-md-offset-2 col-md-6\">");
            out.println("                <div class=\"col-md-12\"><h1>Bentornato "+nome+"!</h1></div>");
            out.println("                <div class=\"col-md-12\"><h1><small>");
            if (dateCookieBool==true) {
                out.println(dateToShow);
            } else {
                out.println("                Non sono disponibili dati relativi all'ultimo accesso");
            }
            out.println("            </small></h1></div></div>");
            out.println("           <div class=\"col-md-4\"><img src=\"Images/singleicon.png\" class=\"img-rounded\" style=\"width:150px; height:150px;\"></div>");
            out.println("            <div style=\"margin-top:150px;\"></div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div class=\"col-md-6 col-md-offset-4\"><a href=\"listaInviti\"><img src=\"Images/inviteicon.png\" alt=\"\" id=\"inviti\" /><label for=\"inviti\">Inviti</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div class=\"col-md-6 col-md-offset-4\"><a href=\"Gruppi\"><img src=\"Images/groupicon.png\" alt=\"\" id=\"gruppi\" /><label for=\"gruppi\">Gruppi</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div class=\"col-md-6 col-md-offset-4\"><a href=\"creaGruppo\"><img src=\"Images/createicon.png\" alt=\"\" id=\"creagruppo\" /><label for=\"creagruppo\">Crea Gruppo</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div class=\"col-md-6 col-md-offset-4\"><a href=\"iMieiDati\"><img src=\"Images/singleicon.png\" alt=\"\" id=\"imieidati\" /><label for=\"imieidati\">I miei dati</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div class=\"col-md-6 col-md-offset-4\"><a href=\"Logout\"><img src=\"Images/logouticon.png\" alt=\"\" id=\"logout\" /><label for=\"logout\">Logout</label></a></div>");
            out.println("               </div>");
            out.println("            </div>");
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
