
package servlet;

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
import services.MetodiGruppi;

public class eliminaGruppo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
        PrintWriter out = response.getWriter();
        String idGruppo = null;
        Enumeration paramNames = request.getParameterNames();
        //recupero il numero del gruppo
        while(paramNames.hasMoreElements()) {
             idGruppo = (String)paramNames.nextElement();
         }
        try {MetodiGruppi.deleteGroup(Integer.parseInt(idGruppo));
        } catch (SQLException ex) {Logger.getLogger(eliminaGruppo.class.getName()).log(Level.SEVERE, null, ex);}
        response.sendRedirect("Home");        
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
