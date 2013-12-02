package filter;

import classi.Utente;
import classi.Gruppo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UploadedAvatarFilter implements Filter {
    
    private static final boolean debug = false;
    private FilterConfig filterConfig = null;
    
    public UploadedAvatarFilter() {    }    
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        Gruppo gruppo=null;
        boolean isMyAvatar =false;
        Utente utente = null;
        
        utente = (Utente)((HttpServletRequest)request).getSession().getAttribute("utente");
        
        if (utente!=null) {
            
            String userAvatar = null;
            userAvatar = utente.getAvatar();

            String myURL = ((HttpServletRequest)request).getRequestURI();

            int lastCharIndex = myURL.length();
            int lastSlashIndex=myURL.lastIndexOf("/")+1;

            String avatarString= myURL.substring(lastSlashIndex, lastCharIndex);
            System.err.println("groupIDstring= " + avatarString);
            
            userAvatar= userAvatar.replaceAll(" ", "%20");
            

            if (userAvatar.equals(avatarString)) {
                isMyAvatar=true;
            }
        }
        
        if (!isMyAvatar){  
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\"3; /WebFinal/Home \">");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href= \"Css/bootstrap.css \" media=\"screen\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">\n" +
"                           <div class=\"col-md-6 col-md-offset-3\" style=\"margin-top:40px;margin-bottom:10px; text-align:center;\">\n" +
"                               <h1>Impossibile Accedere. </h1>\n" +
"                           </div>\n" +
"                           <div class=\"col-md-6 col-md-offset-3\" style=\"margin-bottom:30px; text-align:center;\">\n" +
"                               <h1><small>Non puoi visualizzare questo file.</small></h1>\n" +
"                           </div>"
                    + "  </div>");
            out.println("</body>");
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
