/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import classi.Gruppo;
import classi.Utente;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import database.DBManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FMalesani
 */
public class PDF_report extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String numeroGruppoString = request.getParameter("nomeGruppo");
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        String nomeUtente = ute.getUsername();
        Gruppo gruppo= new Gruppo();
        int numeroGruppo=Integer.parseInt(numeroGruppoString);
        gruppo=DBManager.searchGruppoById(numeroGruppo);
        String nomeGruppo = gruppo.getNome();
        
        //PrintWriter out = response.getWriter();
        //response.setContentType("application/pdf");
        // step 1: creation of a document-object
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.addTitle("ReportByMyServletPDF");
        document.addAuthor("myProject");
        
        document.open();
        Paragraph p0 = new Paragraph(new Phrase("GROUP REPORT",
                FontFactory.getFont(FontFactory.HELVETICA, 24, BaseColor.RED)));
        p0.setAlignment(Element.ALIGN_CENTER);
        document.add(p0);
        
        Paragraph p01 = new Paragraph(new Phrase("Report del Gruppo " +nomeGruppo,
                FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.RED)));
        p01.setAlignment(Element.ALIGN_CENTER);
        document.add(p01);
        
        Paragraph p02 = new Paragraph(new Phrase("Gruppo amministrato da " +nomeUtente,
                FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK)));
        p02.setAlignment(Element.ALIGN_CENTER);
        document.add(p02);
        
        Image userJpg = Image.getInstance("/Users/FMalesani/NetBeansProjects/Steeeee/web/images/user_icon.jpg");
        userJpg.scaleToFit(100,100);
        userJpg.setAlignment(Element.ALIGN_CENTER);
        document.add(userJpg);
        
        Paragraph p1 = new Paragraph(new Phrase("This is my first paragraph. It's only a try. I don't have anything to say.",
                FontFactory.getFont(FontFactory.HELVETICA, 10)));
        /*
        p1.add("The leading of this paragraph is calculated automagically. ");
        p1.add("The default leading is 1.5 times the fontsize. ");
        p1.add(new Chunk("You can add chunks "));
        p1.add(new Phrase("or you can add phrases. "));
        p1.add(new Phrase("Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.",
                FontFactory.getFont(FontFactory.HELVETICA, 18)));*/
        document.add(p1);
        Paragraph p2 = new Paragraph(new Phrase("This is the second paragraph. Now we'll start with the enumeration of our users:",
                FontFactory.getFont(FontFactory.HELVETICA, 10)));
        document.add(p2);
        /*Paragraph p2 = new Paragraph(new Phrase(
                "This is my second paragraph. ", FontFactory.getFont(
                        FontFactory.HELVETICA, 12)));
        p2.add("As you can see, it started on a new line.");
        document.add(p2);
        Paragraph p3 = new Paragraph("This is my third paragraph.",
                FontFactory.getFont(FontFactory.HELVETICA, 12));
        document.add(p3);*/
        List list=new List(false, 20);
        list.setListSymbol(new Chunk("\u2022", FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD)));
        for (int i=0; i<5; i++) {
            String name= ("User"+i);
            ListItem listItem = new ListItem (name);
            list.add(listItem);
        }
        document.add(list);
        // step 5: we close the document
        document.close();

        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(PDF_report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PDF_report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(PDF_report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PDF_report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
