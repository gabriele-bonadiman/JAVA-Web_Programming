package servlet;

import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginAppoggio extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {processRequest(request, response);    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext context = getServletContext();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Utente ute = null;
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>LOGIN APPOGGIO</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //prendo la session e i parametri dalla request
            HttpSession session = request.getSession();
            String username = (String) request.getParameter("username");
            String password = (String) request.getParameter("password");
            boolean check=false;
            
            /**
             * COOKIES
             * Mi crea i cookies per l'username e la password dell'utente
             * Tali cookies durano 24 ore
             */
            Cookie usernameCookie = new Cookie("username", username);
            Cookie passwordCookie = new Cookie("password", password);
            usernameCookie.setMaxAge(60 * 60 * 24);
            passwordCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);

            //controllo che l'utente sia presente nel DB con il seguente metodo
            try {
                check = DBManager.checkUtente(username, password);
            } catch (SQLException ex) {Logger.getLogger(LoginAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
            
            //se e' presente l'utente, metto in session l'username e vado alla Home
            if(check==true){
                try {
                    ute = DBManager.idUtente(username);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginAppoggio.class.getName()).log(Level.SEVERE, null, ex);
                }
                session.setAttribute("utente", ute);
                session.setAttribute("username", username);
                RequestDispatcher rd = request.getRequestDispatcher("/Home");
                rd.forward(request, response);
            }else{ response.sendRedirect("Login");}
        } finally {out.close();}
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
