package filter;

import classi.Gruppo;
import classi.Utente;
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

public class GroupAdminFilter implements Filter {
    
    private static final boolean debug = false;
    private FilterConfig filterConfig = null;
    
    public GroupAdminFilter() {
    }    

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        Utente utente = (Utente)((HttpServletRequest)request).getSession().getAttribute("utente");
        
        Gruppo gruppo = null;
        
        String paramName = null;
        paramName = (String)request.getParameter("nomeGruppo");
        
        if (paramName==null) {
            Enumeration paramNames = request.getParameterNames();

            while(paramNames.hasMoreElements()) {
                paramName = (String)paramNames.nextElement();
            }
        }
        
        try {
            gruppo = MetodiGruppi.searchGruppoById(Integer.parseInt(paramName));
        } catch (SQLException ex) {
            Logger.getLogger(GroupAdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean userIsAdminOfTheGroup=false;
        
        try {
            int idAdmin= MetodiGruppi.idAdminOfTheGroup(gruppo);
            if (idAdmin==utente.getId()) {
                userIsAdminOfTheGroup=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (userIsAdminOfTheGroup == false){
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
"                               <h1><small>Non sei l' admin di questo gruppo.</small></h1>\n" +
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
