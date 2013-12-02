package filter;

import classi.Gruppo;
import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import services.MetodiGruppi;

public class GroupFilter2 implements Filter {
    
    private static final boolean debug = false;
    private FilterConfig filterConfig = null;
    
    public GroupFilter2() {    }    

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        Utente utente = (Utente)((HttpServletRequest)request).getSession().getAttribute("utente");
        Gruppo gruppo = (Gruppo) ((HttpServletRequest)request).getSession().getAttribute("gruppo");
        
        boolean userIsInTheGroup=false;

        try {
            userIsInTheGroup = MetodiGruppi.uteIntoTheGroup(utente, gruppo);
        } catch (SQLException ex) {
            Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (userIsInTheGroup == false){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\"3; Gruppi\">");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">\n" +
"                           <div class=\"col-md-6 col-md-offset-3\" style=\"margin-top:40px;margin-bottom:10px; text-align:center;\">\n" +
"                               <h1>Impossibile Accedere. </h1>\n" +
"                           </div>\n" +
"                           <div class=\"col-md-6 col-md-offset-3\" style=\"margin-bottom:30px; text-align:center;\">\n" +
"                               <h1><small>Non fai parte di questo gruppo.</small></h1>\n" +
"                           </div>"
                    + "  </div>");
            out.println("</body>");
            out.close();
            
        } else 
            chain.doFilter(request, response);
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {  
        this.filterConfig = null;
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }
}
