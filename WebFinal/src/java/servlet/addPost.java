package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class addPost extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
      
            
        String paramName;
        Enumeration paramNames = request.getParameterNames();
        System.out.println("dsdsaasd");
        
       
       while(paramNames.hasMoreElements()) {
            paramName = (String)paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            out.println("paramName =  " + paramName + "  paramValues = " + paramValues[0]);
        }
   
            
            
            
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet addPost</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>AGGIUNGI UN POST</h1>");

            
        out.println("<form action=\"addPost\">");
        out.println("<input type=\"text\" class=\"stdinput\" name=\"post\"/>");
        out.println("<input type=\"submit\" value=\"Login\"/></div> </form> ");
        out.println("</form> ");
        out.println("</body>");
        out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
