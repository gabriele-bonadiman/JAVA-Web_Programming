/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

import classi.Utente;
import classi.Gruppo;
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

/**
 *
 * @author FMalesani
 */

public class UploadedFileFilter implements Filter {
    
    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public UploadedFileFilter() {
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
        
        Gruppo gruppo=null;
        boolean userIsInTheGroup =false;
        Utente utente = null;
        System.err.println("filtro1");
        utente = (Utente)((HttpServletRequest)request).getSession().getAttribute("utente");
        System.err.println("filtro2");
        if (utente!=null) {

            System.err.println(utente.getUsername() + " " + utente.getId());

            String myURL = ((HttpServletRequest)request).getRequestURI();

            int secondToLastSlashIndex=23;
            int lastSlashIndex=myURL.lastIndexOf("/");

            String groupIDstring= myURL.substring(secondToLastSlashIndex, lastSlashIndex);
            System.err.println("groupIDstring= " + groupIDstring);

            int groupIDint = Integer.parseInt(groupIDstring);
            System.err.println("groupIDint= " + groupIDint);

            

            try {
                gruppo = (Gruppo)MetodiGruppi.searchGruppoById(groupIDint);
            } catch (SQLException ex) {
                Logger.getLogger(UploadedFileFilter.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (gruppo!=null) {
                try {
                    userIsInTheGroup = MetodiGruppi.uteIntoTheGroup(utente, gruppo);
                } catch (SQLException ex) {
                    Logger.getLogger(UploadedFileFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (userIsInTheGroup == false){  
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\"3; /WebFinal/Home \">");
            out.println("<div align=’center’><H3>IMPOSSIBILE ACCEDERE: NON PUOI VISUALIZZARE QUESTO FILE<br><br>");
            out.println("Redirezione alla pagina di principale in corso</H3></div>");
            out.close();
        } else {
            chain.doFilter(request, response);
        }
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
