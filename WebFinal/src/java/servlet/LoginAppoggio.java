package servlet;

import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiUtenti;


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
            
            //prendo la session e i parametri dalla request con encoding
            HttpSession session = request.getSession();
            
            String password = new String(request.getParameter("password")
                    .getBytes("iso-8859-1"), "UTF-8");
            String username = new String(request.getParameter("username")
                    .getBytes("iso-8859-1"), "UTF-8");

            boolean check=false;
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat correctFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            //String lastAccessDate= ("Ultimo accesso il "+dateFormat.format(date)+" alle ore "+hourFormat.format(date));
            String lastAccessDate= (dateFormat.format(date)+" "+hourFormat.format(date));
            String correctDate = correctFormat.format(date);

            
                            /************
                             *  COOKIES *
                             ************/            
            //System.err.println("STIRNGA IN LOGIN APPOGGIO "+username);
            String usernameEncoding = URLEncoder.encode(username, "UTF-8");
            
            Cookie usernameCookie = new Cookie("username", usernameEncoding);
            //System.err.println("cookie in LOGIN APPOGGIO "+usernameCookie.getValue());
            
            usernameCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(usernameCookie);


            Cookie passwordCookie = new Cookie("password", URLEncoder.encode(password, "UTF-8"));
            passwordCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(passwordCookie);
            
            Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            Boolean dateCookieBool=false;
            String dateCookieString="";
            String dateToShow="";

            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("lastAccessDate")) {
                    dateCookieString = cookie.getValue();
                    dateCookieBool=true;
                }
            }
            
            if (dateCookieBool==true) {
                dateToShow=dateCookieString;
            } else {
                dateToShow=lastAccessDate;
            }
            
            Cookie dateCookie = new Cookie("lastAccessDate", lastAccessDate);
            dateCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(dateCookie);
            
            Cookie dateToShowCookie = new Cookie("dateToShow", dateToShow);
            dateToShowCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(dateToShowCookie);
            
            Cookie dateFormatCookie = new Cookie("correctDateFormat", correctDate);
            dateFormatCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(dateFormatCookie);
            

            //controllo che l'utente sia presente nel DB con il seguente metodo
            try {
                check = MetodiUtenti.checkUtente(username, password);                
            } catch (SQLException ex) {Logger.getLogger(LoginAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
            
            //se e' presente l'utente, metto in session l'username e vado alla Home
            if(check==true){
                try {
                    ute = MetodiUtenti.idUtente(username);
                } catch (SQLException ex) {Logger.getLogger(LoginAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
                session.setAttribute("utente", ute);
                session.setAttribute("username", username);
                response.sendRedirect("Home");
            }else { 
                response.sendRedirect("Login");
            }
        } finally {out.close();}
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
