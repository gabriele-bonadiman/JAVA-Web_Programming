package servlet;

import classi.Gruppo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;

public class addPost extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        
        try (PrintWriter out = response.getWriter()) {
            
        String idGruppo = null;
        Enumeration paramNames = request.getParameterNames();
       
        //recupero il numero del gruppo
       while(paramNames.hasMoreElements()) {
            idGruppo = (String)paramNames.nextElement();
        }
        Gruppo gr = MetodiGruppi.searchGruppoById(Integer.parseInt(idGruppo));
        session.setAttribute("gruppo", gr);
        
        out.println("<!DOCTYPE html>");
            out.println("<html> ");
            out.println("    <head> ");
            out.println("        <title>Aggiungi Post</title> ");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"Css/bootstrap.css\" media=\"screen\" />");
            out.println("    </head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("   <div class=\"col-md-offset-2 col-md-8\"><h1>Aggingi un post</h1></div>");
        out.println("       <div class=\"col-md-offset-2 col-md-8\" style=\"margin-top:50px;\">");
        out.println("   <form enctype='multipart/form-data' method='POST' action='addPostAppoggio' class=\"form-horizontal\"> "
                        + "     <div class=\"form-group\"><label class=\"col-md-3\" for=\"text\">Testo:</label><input type='textarea' id=\"text\" name='text' class=\"form-control col-md-8\"> </div>"
                        + "     <div class=\"col-md-3\" style='margin-top:20px;'>"
                        + "     <label>File:</label></div>"
                        + "     <div class=\"col-md-9\" style='margin-top:20px;'>"
                        + "         <div style='margin-bottom:20px;'><input type='file' name='file1'> </div>"
                        + "         <div style='margin-bottom:20px;'><input type='file' name='file2'> </div> "
                        + "         <div style='margin-bottom:20px;'><input type='file' name='file3'> </div> "
                        + "     </div>"
                        + "   <div class='col-md-offset-1'><input type='submit' value='Upload' style='margin-top:20px;' class='btn btn-default'> </div>");
        out.println("       </form> ");
        out.println("   </div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(addPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(addPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
