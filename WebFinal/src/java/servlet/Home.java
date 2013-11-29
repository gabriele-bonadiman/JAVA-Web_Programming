package servlet;

import classi.Gruppo;
import classi.Post;
import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;
import services.MetodiPost;
import services.MetodiUtenti;

public class Home extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        Utente ute = (Utente) session.getAttribute("utente");
        String nome = ute.getUsername();
        
        
        
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        Boolean dateCookieBool=false;
        String dateToShow=null;
        String lastFormatData=null;
        
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals("dateToShow")) {
                dateToShow = cookie.getValue();
                dateCookieBool=true;
            }
            if (cookie.getName().equals("correctDateFormat")) {
                lastFormatData = cookie.getValue();
            }
        }
            
            
        /**
         *   VISUALIZZAZIONE DEGLI EVENTI MENTRE ERO OFFLINE
         */
        
                                    /*************
                                     *  TIMELINE *
                                     *************/
        //calcolo la data del post
        Date dateAccess = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY).parse(dateToShow);
        ArrayList<Post> listaPost = MetodiPost.returnData(ute);
        
        
        
        
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
                dateToShow = dateToShow.replace(" ", " alle ");
                out.println(        "Ultimo accesso il giorno "+dateToShow);
            } else {
                out.println("                Non sono disponibili dati relativi all'ultimo accesso");
            }
            out.println("            </small></h1></div></div>");
            out.println("           <div class=\"col-md-4\" style='margin-top:10px;'><img src=\"UploadedAvatar/"+ute.getAvatar()+"\" class=\"img-rounded\" style=\"width:150px; height:150px;\"></div>");
            out.println("            <div style=\"margin-top:150px;\"></div>");
            out.println("            <div class='col-md-3 col-md-offset-4'>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div><a href=\"listaInviti\"><img src=\"Images/inviteicon.png\" alt=\"\" id=\"inviti\" /><label for=\"inviti\">Inviti</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div><a href=\"Gruppi\"><img src=\"Images/groupicon.png\" alt=\"\" id=\"gruppi\" /><label for=\"gruppi\">Gruppi</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div><a href=\"creaGruppo\"><img src=\"Images/createicon.png\" alt=\"\" id=\"creagruppo\" /><label for=\"creagruppo\">Crea Gruppo</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div><a href=\"iMieiDati\"><img src=\"Images/singleicon.png\" alt=\"\" id=\"imieidati\" /><label for=\"imieidati\">I miei dati</label></a></div>");
            out.println("               </div>");
            out.println("               <div class=\"row\" style=\"margin-top:20px;\">");
            out.println("                   <div><a href=\"Logout\"><img src=\"Images/logouticon.png\" alt=\"\" id=\"logout\" /><label for=\"logout\">Logout</label></a></div>");
            out.println("               </div>");
            out.println("             </div>");
            out.println("            <div class='col-md-3 col-md-offset-2 box-shadows' style='margin-top:20px; height:350px; overflow-y:scroll;'><br/><br/>");
            
            //Metti qui il codice per le notizie 
            
            
            
            
            if(listaPost.isEmpty()){            
                out.println("               <div class='row'>");
                out.println("                   <div style='margin-left:10px; margin-right:10px;'><h5>Niente di nuovo sui gruppi.</h5></div>");
                out.println("               </div>");
            }else{
                out.println("               <div class='row'>");
                out.println("                   <div style='margin-left:10px; margin-right:10px;'><h5>");
                Iterator i = listaPost.iterator(); 
                while(i.hasNext()) {
                    Post p = (Post) i.next();
                    Date datePost = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ITALY).parse(p.getData());

                    if(datePost.after(dateAccess) && p.getUtente()!=ute.getId()){
                        Utente ut = MetodiUtenti.searchUtenteByID(p.getUtente());
                        Gruppo gr = MetodiGruppi.searchGruppoById(p.getGruppo());
                        out.println("L'utente " + ut.getUsername() + " ha pubblicato qualcosa in " + gr.getNome());
                    }
                }
            }
        
            out.println("</h5></div>");
            out.println("               </div>");
            
            out.println("            </div>");
            
            
            
            
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
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
