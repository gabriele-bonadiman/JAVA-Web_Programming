package servlet;

import classi.Gruppo;
import classi.Utente;
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

public class modificaGruppoAppoggio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
            
        try (PrintWriter out = response.getWriter()) {
        
        
        
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet modificaGruppoAppoggio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet modificaGruppoAppoggio at " + request.getContextPath() + "</h1>");
            
            out.println("</body>");
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
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
    
        Utente ute = (Utente) session.getAttribute("utente");
        Gruppo gr = (Gruppo) session.getAttribute("gruppoCorrente");
        String nuovoNome = (String) request.getParameter("nuovoNome");
        
        try {
            //controllo che il nuovo nome e quello vecchio siano diversi
            if(!nuovoNome.equals(gr.getNome()))            
                    DBManager.editNomeGruppo(gr, nuovoNome);
        }
        catch (SQLException ex) {Logger.getLogger(modificaGruppoAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
        session.removeAttribute("gruppoCorrente");
        response.sendRedirect("Gruppi");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
