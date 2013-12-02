package filter;

import classi.Utente;
import classi.Gruppo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
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

public class GroupFilter implements Filter {
    
    private static final boolean debug = false;
    private FilterConfig filterConfig = null;
    
    public GroupFilter() {    }    

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        Utente utente = (Utente)((HttpServletRequest)request).getSession().getAttribute("utente");
        String groupID = ((HttpServletRequest)request).getParameter("id");
        Gruppo gruppo = null;
        
        if (groupID==null) {
            Enumeration paramNames = request.getParameterNames();

            while(paramNames.hasMoreElements()) {
                groupID = (String)paramNames.nextElement();
            }
        }
        
        try {
            gruppo=MetodiGruppi.searchGruppoById(Integer.parseInt(groupID));
        } catch (SQLException ex) {
            Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

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
