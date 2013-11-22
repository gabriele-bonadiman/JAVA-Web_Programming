package servlet;

import classi.Gruppo;
import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class addPostAppoggio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        Gruppo gr = (Gruppo) session.getAttribute("gruppo");
        Utente ute = (Utente) session.getAttribute("utente");
        String text = (String) request.getParameter("post");
        
        out.println(text);
        
        try {
            services.servicesPost.insertPost(gr, ute, text);
            response.sendRedirect("Forum?id="+gr.getID());        
        } catch (SQLException ex) {Logger.getLogger(addPostAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
    
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
