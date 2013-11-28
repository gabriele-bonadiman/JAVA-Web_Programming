package servlet;

import classi.Gruppo;
import classi.Lista;
import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;
import services.MetodiUtenti;
public class listaInviti extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String utente = (String) session.getAttribute("username");
        Utente ute = (Utente) session.getAttribute("utente");

        ArrayList<Lista> listaInviti = MetodiGruppi.listaGruppiUtente(ute.getId());
        
        try (PrintWriter out = response.getWriter()) {
        
            
            
            if(!listaInviti.isEmpty()){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <title>Inviti</title>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
                out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <div class=\"container\">");
                out.println("           <div class=\"col-md-offset-2 col-md-8\">");
                out.println("               <h1>Inviti</h1>");
                out.println("           </div>");
                out.println("           <div class=\"col-md-8 col-md-offset-2\" style=\"margin-top:50px; margin-bottom:20px; \">");
                out.println("               <form action= \"listaInvitiAppoggio\" method=\"POST\">");
                out.println("               <div class='col-md-12' style='overflow-y:scroll; max-height:400px;'>");
                out.println("               <table class=\"table table-striped\">");
                out.println("                   <thead>");
                out.println("                       <tr>");
                out.println("                           <th>Gruppo</th>");
                out.println("                           <th>Proprietario</th>");
                out.println("                           <th>Iscritto</th>");
                out.println("                       </tr>");
                out.println("                   </thead>");
                out.println("                   <tbody>");

                Iterator i = listaInviti.iterator(); 
                System.out.println("iteratore");
                while(i.hasNext()) {
                    Lista lista = (Lista) i.next();
                    Gruppo gr = MetodiGruppi.searchGruppoById(lista.getGruppo());
                    Utente pro = MetodiUtenti.searchUtenteByID(gr.getProprietario());
                    if (gr.getProprietario() != ute.getId()){
                        out.println("                    <tr>");
                        out.println("                        <td>"+gr.getNome()+"</td>");
                        out.println("                        <td>"+pro.getUsername()+"</td>");
                        out.println("                        <td>");     
                        
                        if(lista.getInvitato() == 1){
                                 out.println("<div class=\"custom-container\">\n" +
    "                                           <input type=\"checkbox\" name=\""+ gr.getNome() +"\" class=\"custom-checkbox\" id=\"" +gr.getID()+ "\"  checked />\n" +
    "                                           <label class=\"custom-label\" for=\"" +gr.getID()+ "\" > </label>\n" +
    "                                       </div>");
                        }else{
                                out.println("<div class=\"custom-container  \">\n" +
    "                                           <input type=\"checkbox\" name=\""+ gr.getNome() +"\" class=\"custom-checkbox\" id=\"" +gr.getID()+ "\" />" +
    "                                           <label class=\"custom-label\" for=\"" +gr.getID()+ "\" > </label>\n" +
    "                                       </div>");
                        }  

                        out.println("                   </td>");
                        out.println("               </tr> ");
                    }
                }

                out.println("                    <tr>");
                out.println("                    </tr>");
                out.println("                </tbody>");
                out.println("            </table>");
                out.println("     </div>");
                out.println("       <a href='Home'><button class='btn btn-primary col-md-offset-1'>Home</button></a>");
                out.println("       <input type=\"submit\" class=\"btn btn-default col-md-offset-3\" value=\"Conferma\" />");
                
                out.println("       </div>");
                out.println("       </form>");
                out.println("   </div>");
                out.println("</body>");
                out.println("</html>");
                out.println("");
            
            
            }else{
                out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("    <head>");
                    out.println("        <title>Inviti</title>");
                    out.println("        <meta charset=\"UTF-8\">");
                    out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
                    out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
                    out.println("    </head>");
                    out.println("    <body>");
                    out.println("    <div class=\"container\"><div class=\"col-md-6 col-md-offset-3\" style=\"margin-top:40px;margin-bottom:20px; text-align:center;\"><h1>La tua lista degli inviti è vuota!</h1></div>"
                            + "                             <div class=\"col-md-2 col-md-offset-4\" ><a href=\"Home\"<button class=\"btn btn-primary\">Home</button></a></div></div>");
                    out.println("    </body>");
                    out.println("  </html>");
            }
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
