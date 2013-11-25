package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
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
        
        String opsw = request.getParameter("oldPassword");
        session.setAttribute("opsw", opsw);
        
        try (PrintWriter out = response.getWriter()) {
           out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Miei dati</title>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <form action=\"iMieiDatiAppoggio\" method=\"POST\" enctype='multipart/form-data'>");
            out.println("              <div class=\"center\" style=\"width:150px;\">");
            out.println("                   <image src=\"UploadedAvatar/"+utente.getAvatar()+"  \" style=\"height:150px; width: 150px;\" >");
            out.println("              </div>");
            out.println("              <div style=\"font-size: 40px; margin-top: 40px; position:relative;\" >");
            out.println("                    <label class=\"stdlabel\">Modifica Nome:</label><input name=\"username\" placeholder="+ utente.getUsername()+" type=\"text\" class=\"stdinput\">");
            out.println("              </div>");
            out.println("              <div>");
            out.println("                  <label class=\"stdlabel\">Vecchia password:</label><input name=\"oldPassword\" type=\"password\" class=\"stdinput\"></br>");
            out.println("                  <label class=\"stdlabel\">Nuova password:</label><input name=\"newPassword\" type=\"password\" class=\"stdinput\">");
            out.println("              </div>");
            out.println("               <div style=\"width:600px;\"> ");
            out.println("                   <label class=\"stdlabel\" for=\"avatar\">Modifica avatar:</label><input id=\"avatar\" style=\"width:400px;\"class=\"stdinput\" type=\"file\" name=\"avatar\">  ");
            out.println("               </div> ");
            out.println("               <div class=\"center\" style=\"width:200px;\">   ");
            out.println("                   <input type=\"submit\" class=\"stdinput\" value=\"Salva\">");
            out.println("               </div>   ");
            out.println("            </form>");
            out.println("            ");
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");
            out.println("");
        }    
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
