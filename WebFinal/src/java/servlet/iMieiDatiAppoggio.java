package servlet;

import classi.Utente;
import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiUtenti;

public class iMieiDatiAppoggio extends HttpServlet {

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
         System.err.println("ENTRATO NEL METODO APPOGGIo");

         
         //Paramter
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        
        MultipartRequest multi = new MultipartRequest(request,".","UTF-8"); 
        String vecchiaPassword = (String) multi.getParameter("oldPassword"); 
	String nuovaPassword = (String) multi.getParameter("newPassword");
	File f = multi.getFile("avatar"); 
	String fileName = multi.getFilesystemName("avatar");
	String uploadAvatarPathAssoluta = request.getServletContext().getRealPath("/UploadedAvatar");
        
        //creo cartella se non esiste
        File file = new File(uploadAvatarPathAssoluta);
        if (!file.exists()){
            file.mkdir();
        }
        
        //modifico avatar
        if (fileName!=null){
            try {
                MetodiUtenti.editAvatarUtente(ute, fileName);
                ute.setAvatar(fileName);
            } catch (SQLException ex) {Logger.getLogger(iMieiDatiAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        //se il file non e' nullo procedo all'upload
	if (f!=null) {
            File fOUT = new File(uploadAvatarPathAssoluta,fileName);
            FileInputStream fIS = new FileInputStream(f); 
            FileOutputStream fOS = new FileOutputStream(fOUT); 
            while (fIS.available()>0) 
                    fOS.write(fIS.read());
            fIS.close(); 
            fOS.close(); 
	}
             
        //modifico la password
        if(!vecchiaPassword.equals(ute.getPassword()) || nuovaPassword.length()<3){
            out.println("<h1> ERRORE NELL' INSERIMENTO DELLA PASSWORD</h1>");
        }else {
            try {
                MetodiUtenti.editPasswordUtente(ute, nuovaPassword);
                ute.setPassword(nuovaPassword);
            } catch (SQLException ex) {Logger.getLogger(iMieiDatiAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
            Cookie passwordCookie = new Cookie("password", URLEncoder.encode(nuovaPassword, "UTF-8"));
            passwordCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(passwordCookie);
        }
        
        session.setAttribute("utente", ute);
        response.sendRedirect("Home");
    }
    
    
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
