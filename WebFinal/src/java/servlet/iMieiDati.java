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
    public static boolean wrongPassword =false;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        //paramters
        response.setContentType("text/html;charset=UTF-8");
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
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("           <div class=\"col-md-offset-2 col-md-8\">");
            out.println("               <h1>I Miei Dati</h1>");
            out.println("           </div>");
            out.println("           <form action=\"iMieiDatiAppoggio\" method=\"POST\" enctype='multipart/form-data' class=\"form-horizontal\">");
            out.println("               <div class=\"col-md-8 col-md-offset-2\" style=\"margin-top:30px;\">");
            out.println("                   <div class=\"col-md-8 col-md-offset-4\" style=\"margin-bottom:30px;\">");
            out.println("                       <image src=\"UploadedAvatar/"+utente.getAvatar()+"  \" class=\"img-rounded\" style=\"height:150px; width: 150px;\" >");
            out.println("                   </div>");
            out.println("                   <div class=\"form-group\">");
            out.println("                       <label class=\"col-md-3 col-md-offset-1\" for=\"vecchia\">Vecchia password:</label> <div class=\"col-md-3\"><input type=\"password\" name=\"oldPassword\" id=\"vecchia\" class=\"form-control\"></div>");
            if (wrongPassword) {
                out.println("<div style=\"color:#FF0000\"> Password Errata</div>");
            }
            out.println("                   </div>");
            
            out.println("                   <div class=\"form-group\">");
            out.println("                       <label class=\"col-md-3 col-md-offset-1\" for=\"nuova\">Nuova password:</label> <div class=\"col-md-3\"><input type=\"password\" name=\"newPassword\" id=\"nuova\" class=\"form-control\"></div>");
            out.println("                   </div>");
            out.println("                   <div class=\"form-group\"> ");
            out.println("                       <label class=\"col-md-3 col-md-offset-1\" for=\"avatar\">Modifica avatar:</label> <div class=\"col-md-4\"><input id=\"avatar\" class=\"\" type=\"file\" name=\"avatar\"> </div> ");
            out.println("                   </div> ");
            out.println("                   <div class=\"form-group\">   ");
            out.println("                       <input type=\"submit\" class=\"btn btn-default col-md-offset-6 \" value=\"Salva\">");
            out.println("                   </div>   ");
            out.println("               </div>");
            out.println("            </form>");
            out.println("            <div class='col-md-3 col-md-offset-3 ' style='margin-top:-45px;'><a href='Home'><button class='btn btn-primary '>Home</button></a></div>");
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
