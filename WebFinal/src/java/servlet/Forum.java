package servlet;

import classi.Gruppo;
import classi.Post;
import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Forum extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
       
            String grid = request.getParameter("id");
            ArrayList<Post> listaPost = new ArrayList<Post>();
            
            Gruppo g = DBManager.searchGruppoById(Integer.parseInt(grid));
            Utente u = DBManager.searchUtenteByID(g.getProprietario());
            listaPost = DBManager.listaDeiPost(g);
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html> ");
            out.println("    <head> ");
            out.println("        <title>Gruppo</title> ");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"Css/style.scss\" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <div class=\"maintitle\">");
            out.println("                "+g.getNome()+"");
            out.println("            </div>");
            out.println("            <div class=\"imgdiv\">");
            out.println("                <img class=\"adminimg\" src=\"Images/singleicon.png\">");
            out.println("                <div class=\"imgdescriptor\">");
            out.println("                    <b>Admin</b><br>");
            out.println("                    "+u.getUsername()+"");
            out.println("                </div>");
            out.println("                <div>");
            out.println("                    <button class=\"custombtn\">Fine</button>");
            out.println("                </div>");
            out.println("            </div>");
            out.println("            <div class=\"newscontainer\">");

            
            
            
            
            
            
            

            Iterator i = listaPost.iterator(); 
            while(i.hasNext()) {
                Post p = (Post) i.next();
                Utente utentePost =  DBManager.searchUtenteByID(p.getUtente());
                out.println("                <div class=\"post\">");
                out.println("                    <div class=\"postinfo\">");
                out.println("                        <p>"+utentePost.getUsername()+"</p>");
                out.println("                        <img src=\"Images/singleicon.png\">");
                out.println("                        <p>"+p.getData()+"</p>");
                out.println("                    </div>");
                out.println("                    <div class=\"posttext\">");
                out.println("                        "+p.getTesto()+"");
                out.println("                    </div>");
                out.println("                </div>");
            }
            
            out.println("            </div>");
            out.println("            <button class=\"retbtn\" style=\"width: 200px;\">Aggiungi Post</button>");
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
