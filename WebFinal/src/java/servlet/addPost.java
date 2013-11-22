package servlet;

import classi.Gruppo;
import database.DBManager;
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
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet addPost</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>AGGIUNGI UN POST</h1>");

            
        out.println("<form action=\"addPostAppoggio\" method=\"POST\">");
        out.println("<input type=\"text\" class=\"stdinput\" name=\"post\"/>");
        out.println("<input type=\"submit\" value=\"Login\"/></div> </form> ");
        out.println("</form> ");
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
