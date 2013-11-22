package servlet;

import classi.Utente;
import com.oreilly.servlet.MultipartRequest;
import database.DBManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
 
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        
        MultipartRequest multi = new MultipartRequest(request,"."); 
	String nuovoNome = (String) multi.getParameter("username"); 
	String vecchiaPassword = (String) multi.getParameter("oldPassword"); 
	String nuovaPassword = (String) multi.getParameter("newPassword");

	File f = multi.getFile("avatar"); 
	String fileName = multi.getFilesystemName("avatar");
	String uploadAvatarPathAssoluta =request.getServletContext().getRealPath("/UploadedAvatar");
        
        File file = new File(uploadAvatarPathAssoluta);
        if (!file.exists()){
            file.mkdir();
        }
        
        if (fileName!=null){
            try {
                MetodiUtenti.editAvatarUtente(ute, fileName);
                ute.setAvatar(fileName);
            } catch (SQLException ex) {
                Logger.getLogger(iMieiDatiAppoggio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
	if (f!=null) {
		File fOUT = new File(uploadAvatarPathAssoluta,fileName);
		FileInputStream fIS = new FileInputStream(f); 
		FileOutputStream fOS = new FileOutputStream(fOUT); 
		while (fIS.available()>0) 
			fOS.write(fIS.read());
		fIS.close(); 
		fOS.close(); 
	}
        
        /**
         * CAMBIARE IL CONTROLLO SULLA LUNGHEZZA DELLA PASSWORD!!!
         * HO DOVUTO METTERE 4 PER COMODITA' MA BISOGNA SOSTITUIRLO CON 8
         * 
         * STESSA IDENTICA COSA VALE PER IL CONTROLLO LUNGHEZZA DEL NUOVO NOME INSERITO
         * 
         * FRONTEND
         *      * RENDERE L'INSERIMENTO DELLA PASSWORD VECCHIA INDISPENSABILE
         *      * I CAMPI SI DOVREBBERO CHIAMARE VECCHIA PASSWORD E NUOVA PASSWORD (SENZA CONFIRM)
         * 
         * BACKEND
         *      * IMPLEMENTARE L'UPLOAD DELLA FOTO 
         */
             
        //primo controllo sulla password
        if(!vecchiaPassword.equals(ute.getPassword()) || nuovaPassword.length()<3){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet iMieiDatiAppoggio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> ERRORE NELL' INSERIMENTO DELLA PASSWORD</h1>");
            out.println("</body>");
            out.println("</html>");
            //response.sendRedirect("ERRORE PASSWORD");
        }else {
            try {
                MetodiUtenti.editPasswordUtente(ute, nuovaPassword);
                ute.setPassword(nuovaPassword);
            } catch (SQLException ex) {Logger.getLogger(iMieiDatiAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
            
            Cookie passwordCookie = new Cookie("password", nuovaPassword);
            passwordCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(passwordCookie);
        }
        
        //inserimento nome utente
        if(!nuovoNome.equals(ute.getUsername()) && nuovoNome!=null && nuovoNome.length()>4){
            try {
                MetodiUtenti.editNomeUtente(ute, nuovoNome);
                ute.setUsername(nuovoNome);
            } catch (SQLException ex) {Logger.getLogger(iMieiDatiAppoggio.class.getName()).log(Level.SEVERE, null, ex);}
            
            Cookie usernameCookie = new Cookie("username", nuovoNome);
            usernameCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(usernameCookie);
        }
        
        session.setAttribute("utente", ute);
        response.sendRedirect("Home");

    }
    
    
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
