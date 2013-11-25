package servlet;

import classi.Gruppo;
import classi.Utente;
import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiPost;

public class addPostAppoggio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Gruppo gr = (Gruppo) session.getAttribute("gruppo");
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<head> "
                    + "<meta http-equiv=\"refresh\" "
                    + "content=\"3;url=Forum?id="+gr.getID()+"\" >"
                    + "<h1>UPLOAD DONE!</h1>"
                    + "</head>"
                    + "<body>"
                    + "<div class=\"user_register border_y\"><div>"
                    + "<span style=\"color: green;\"> After 3 seconds you will be redirected to the previous page!</span>"
                    + "<br><br>"
                    + "</body>");
                    System.err.println("url=Forum?id=\""+gr.getID()+"\">");
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
        
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        Gruppo gr = (Gruppo) session.getAttribute("gruppo");
        Utente ute = (Utente) session.getAttribute("utente");
        
        MultipartRequest multi=new MultipartRequest(request,".");
        
        String testoPost = (String) multi.getParameter("text");
        if(testoPost!=null && !testoPost.equals(""))
            testoPost = services.ParsingText.parsing(testoPost);
        
        //come gestiamo le stringhe nulle?
        
        try {
            MetodiPost.insertPost(gr, ute, testoPost);   
        } catch (SQLException ex) {Logger.getLogger(addPostAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
        
        Enumeration files= multi.getFileNames();

        while (files.hasMoreElements()) {
            String name = (String) files.nextElement();

            File f = multi.getFile(name);
            String fileName = multi.getFilesystemName(name);
            if (f != null) {
                String pathUpload = request.getServletContext().getRealPath("/UploadedFile");
                pathUpload = request.getServletContext().getRealPath("UploadedFile/");
                File file = new File(pathUpload);
                if (!file.exists()) {
                    file.mkdir();
                }

                pathUpload = request.getServletContext().getRealPath("UploadedFile/" + gr.getID() + "/");
                File file2 = new File(pathUpload);
                if (!file2.exists()) {
                    file2.mkdir();
                }
                
                //QUI BISOGNA AGGIUNGERE AL DB IL FILE, PER ORA IO LO CARICO SOLO NELLA CARTELLA
                //MA BISOGNA INSERIRNE IL NOME ECC ECC NEL DB

                File fOUT = new File(pathUpload, fileName);
                FileInputStream fIS = new FileInputStream(f);
                FileOutputStream fOS = new FileOutputStream(fOUT);
                while (fIS.available() > 0) {
                    fOS.write(fIS.read());
                }
                fIS.close();
                fOS.close();
            }
        }

        processRequest(request, response);
        
    } 

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
