package servlet;

import classi.Gruppo;
import classi.Post;
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
import services.MetodiPost;
import services.MetodiUtenti;

public class Forum extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
       
            String grid = request.getParameter("id");
            ArrayList<Post> listaPost = new ArrayList<Post>();
            HttpSession session = request.getSession();
            Utente ute = (Utente) session.getAttribute("utente");
            Gruppo g = MetodiGruppi.searchGruppoById((Integer.parseInt(grid)));
            Utente u = MetodiUtenti.searchUtenteByID(g.getProprietario());

            listaPost = MetodiPost.listaDeiPost(g);
            
            out.println("<!DOCTYPE html>");
            out.println("<html> ");
            out.println("    <head> ");
            out.println("        <title>Forum</title> ");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"Css/bootstrap.css\" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("           <div class=\"col-md-offset-2 col-md-8\" style='margin-bottom:30px;'>");
            out.println("               <h1>"+g.getNome()+"</h1>");
            out.println("           </div>");
            out.println("           <div class=\"col-md-4\">");
            out.println("               <div class=\"col-md-12\">");
            out.println("                   <img src=\"UploadedAvatar/"+u.getAvatar()+"\" class=\"img-rounded\" style=\"height:150px; width:150px;\">");
            out.println("               </div>");
            out.println("               <div class=\"col-md-12\">");
            
            
            if(ute.getId() == g.getProprietario()){
                out.println("            <form action=\"modificaGruppo\" method=\"POST\">"); 
                out.println("               <input class=\"btn btn-default \" style=\"margin-top:20px;\" name = \""+g.getID()+"\"type=\"submit\" value=\"Modifica Gruppo\">");
                out.println("            </form>");
                out.println("            <form action=\"eliminaGruppo\" method=\"POST\">"); 
                out.println("               <input class=\"btn btn-default \" style=\"margin-top:20px; \" name = \""+g.getID()+"\"type=\"submit\" value=\"Elimina Gruppo\">");
                out.println("            </form>");
            }
            
            
            out.println("               <a href=\"Gruppi\"><button style='margin-top:20px;' class=\"btn btn-primary \">Indietro</button></a>");
            out.println("               </div>");
            out.println("           </div>");
            out.println("           <div class=\"col-md-8\">");
            out.println("           <div class=\"col-md-12 box-shadows\" style=\"overflow-y:auto; height:400px;\">");

            Iterator i = listaPost.iterator(); 
            while(i.hasNext()) {
                Post p = (Post) i.next();
                Utente utentePost =  MetodiUtenti.searchUtenteByID(p.getUtente());
                out.println("                <div class=\"row row-striped-me\">");
                out.println("                    <div class=\"col-md-4\">");
                out.println("                        <p style='margin-bottom:0px;'><b>"+utentePost.getUsername()+"</b></p>");
                out.println("                          <img class=\"img-rounded\" src=\"" +"UploadedAvatar/" + utentePost.getAvatar()+ "\" style='height:50px; width:50px;'>");
                out.println("                        <p>"+p.getData()+"</p>");
                out.println("                    </div>");
                out.println("                    <div class=\"col-md-8\" style='margin-top:10px;'>");
                out.println("                        "+p.getTesto()+"");
                out.println("                    </div>");
                out.println("                </div>");
            }
            out.println("            </div>");
            out.println("            <form action=\"addPost\" style=\"margin-top:30px; margin-bottom:20px;\">"); 
            out.println("               <input name = \""+g.getID()+"\" type=\"submit\" class=\"btn btn-default\" value=\"Crea Post\">");
            out.println("            </form>");
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");   
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
