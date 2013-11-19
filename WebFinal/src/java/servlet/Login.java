package servlet;

import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Login extends HttpServlet {

    DBManager dbManager;
    
    @Override
    public void init() throws ServletException {
        this.dbManager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         /**
         * PARAMETERS
         */
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        /**
         *  PARAMETERS
         */
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        
        if(username != null){
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            RequestDispatcher rd = request.getRequestDispatcher("/LoginAppoggio");
            rd.forward(request, response);
            return;
        }
            
        out.println("<!DOCTYPE html>");
        out.println("<!--");
        out.println("To change this license header, choose License Headers in Project Properties.");
        out.println("To change this template file, choose Tools | Templates");
        out.println("and open the template in the editor.");
        out.println("-->");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>Login</title>");
        out.println("        <meta charset=\"UTF-8\">");
        out.println("        <meta name=\"viewport\" content=\"width=device-width\"  >");
        out.println("        <link rel=\"stylesheet\" type=\"text/css\" href= \"Css/style.scss \" media=\"screen\" />");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <div class=\"container\">");
        out.println("            <div class=\"logform\">");
        
        //HO SOSTITUITO IL VECCHIO FORM CON UNO NUOVO CHE MI INSERISCA USERNAME E PASSWORD SE HO DEI COOKIE SALVATI
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        Boolean usernameCookieBool=false;
        Boolean passwordCookieBool=false;
        String usernameCookie = " ";
        String passwordCookie = " ";

        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals("username")) {
                usernameCookie = cookie.getValue();
                usernameCookieBool=true;
            }
            if (cookie.getName().equals("password")) {
                passwordCookie = cookie.getValue();
                passwordCookieBool=true;
            }
                }
        //HttpSession sessions = request.getSession(true);
        if (usernameCookieBool && passwordCookieBool) {
            
            
        //}
        
        //if (cookies != null) {
            out.println("<form align=\"center\" action= \"LoginAppoggio \" method=\"POST\">");
            out.println("Username: <input type=\"text\" name=\"username\" value=" + usernameCookie + " /><br>");
            out.println("Password: <input type=\"password\" name=\"password\" value=" + passwordCookie + " /><br>");
            out.println("<input type=\"submit\" value=\"Login\"/> </form> ");
        } else {
            out.println("<form  align=\"center\" action= \"LoginAppoggio \" method=\"POST\">");
            out.println("Username: <input type=\"text\" name=\"username\"/><br>");
            out.println("Password: <input type=\"password\" name=\"password\"/><br>");
            out.println("<input type=\"submit\" value=\"Login\"/> </form> ");
        }
        
        out.println("            </div>");
        out.println("        </div>");
        out.println("    </body>");
        out.println("</html>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               processRequest(request, response);

            }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
