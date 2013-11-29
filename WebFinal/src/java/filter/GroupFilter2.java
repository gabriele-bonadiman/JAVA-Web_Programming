/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

import classi.Gruppo;
import classi.Utente;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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

/**
 *
 * @author FMalesani
 */
public class GroupFilter2 implements Filter {
    
    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public GroupFilter2() {
    }    

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        Utente utente = (Utente)((HttpServletRequest)request).getSession().getAttribute("utente");
        Gruppo gruppo = (Gruppo) ((HttpServletRequest)request).getSession().getAttribute("gruppo");
        
        boolean userIsInTheGroup=false;

        // qui al posto di uteIntoTheGroup userò il metodo che controlla che l'utente sia admin del gruppo
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
            out.println("<div align=’center’><H3>IMPOSSIBILE ACCEDERE: NON FAI PARTE DI QUESTO GRUPPO<br><br>");
            out.println("Redirezione alla pagina di principale in corso</H3></div>");
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
