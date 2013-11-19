package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Forum extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            
            
                        out.println("<!DOCTYPE html>");
            out.println("<!--");
            out.println("To change this license header, choose License Headers in Project Properties.");
            out.println("To change this template file, choose Tools | Templates");
            out.println("and open the template in the editor.");
            out.println("-->");
            out.println("<html> ");
            out.println("    <head> ");
            out.println("        <title>Gruppo</title> ");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"Css/style.scss\" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <div class=\"maintitle\">");
            out.println("                Nome Gruppo");
            out.println("            </div>");
            out.println("            <div class=\"imgdiv\">");
            out.println("                <img class=\"adminimg\" src=\"Images/singleicon.png\">");
            out.println("                <div class=\"imgdescriptor\">");
            out.println("                    <b>Admin</b><br>");
            out.println("                    Gianfranco");
            out.println("                </div>");
            out.println("                <div>");
            out.println("                    <button class=\"custombtn\">Fine</button>");
            out.println("                </div>");
            out.println("            </div>");
            out.println("            <div class=\"newscontainer\">");
            out.println("                <div class=\"post\">");
            out.println("                    <div class=\"postinfo\">");
            out.println("                        <p>Tizio</p>");
            out.println("                        <img src=\"Images/singleicon.png\">");
            out.println("                        <p>12:34 13/11/2013</p>");
            out.println("                    </div>");
            out.println("                    <div class=\"posttext\">");
            out.println("                        qua va il testo blablalblalblalbl lblsl lgjerlgperjg ijeorijgoiej ijero joerjgoejrotejri joeij goejrtijer");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("                <div class=\"post\">");
            out.println("                    <div class=\"postinfo\">");
            out.println("                        <p>Tizio</p>");
            out.println("                        <img src=\"Images/singleicon.png\">");
            out.println("                        <p>12:34 13/11/2013</p>");
            out.println("                    </div>");
            out.println("                    <div class=\"posttext\">");
            out.println("                        qua va il testo blablalblalblalbl lblsl lgjerlgperjg ijeorijgoiej ijero joerjgoejrotejri joeij goejrtijer");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("                <div class=\"post\">");
            out.println("                    <div class=\"postinfo\">");
            out.println("                        <p>Tizio</p>");
            out.println("                        <img src=\"Images/singleicon.png\">");
            out.println("                        <p>12:34 13/11/2013</p>");
            out.println("                    </div>");
            out.println("                    <div class=\"posttext\">");
            out.println("                        qua va il testo blablalblalblalbl lblsl lgjerlgperjg ijeorijgoiej ijero joerjgoejrotejri joeij goejrtijer");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("                <div class=\"post\">");
            out.println("                    <div class=\"postinfo\">");
            out.println("                        <p>Tizio</p>");
            out.println("                        <img src=\"Images/singleicon.png\">");
            out.println("                        <p>12:34 13/11/2013</p>");
            out.println("                    </div>");
            out.println("                    <div class=\"posttext\">");
            out.println("                        qua va il testo blablalblalblalbl lblsl lgjerlgperjg ijeorijgoiej ijero joerjgoejrotejri joeij goejrtijer");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("            </div>");
            out.println("            <button class=\"retbtn\" style=\"width: 200px;\">Aggiungi Post</button>");
            out.println("        </div>");
            out.println("    </body>");
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
