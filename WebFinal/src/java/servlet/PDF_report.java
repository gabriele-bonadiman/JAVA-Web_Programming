
package servlet;

import classi.Gruppo;
import classi.Utente;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DBManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PDF_report extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String numeroGruppoString = request.getParameter("nomeGruppo");
        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");
        String nomeUtente = ute.getUsername();
        String avatarUtente = ute.getAvatar();
        Gruppo gruppo= new Gruppo();
        int numeroGruppo=Integer.parseInt(numeroGruppoString);
        gruppo= services.servicesGruppi.searchGruppoById(numeroGruppo);
        String nomeGruppo = gruppo.getNome();
        ArrayList utentiIscritti= new ArrayList();
        
        utentiIscritti = services.servicesGruppi.listaUtentiPresenti(gruppo);
        
        String uploadAvatarPathAssoluta =request.getServletContext().getRealPath("/UploadedAvatar");
        
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
        
        Image userJpg = Image.getInstance(uploadAvatarPathAssoluta + "/" + avatarUtente);
        userJpg.scaleToFit(100,100);
        userJpg.setAlignment(Element.ALIGN_CENTER);
        document.add(userJpg);
        
        Paragraph p2 = new Paragraph(new Phrase(" ",
                FontFactory.getFont(FontFactory.HELVETICA, 30)));
        document.add(p2);
        
        PdfPTable table = new PdfPTable(4);
        
        PdfPCell cell= new PdfPCell(new Paragraph("Avatar",FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.RED)));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Nome utente",FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.RED)));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Numero post",FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.RED)));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Data ultimo post",FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.RED)));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        while (!utentiIscritti.isEmpty()) {
            int index= utentiIscritti.size()-1;
            Utente myUser=(Utente)utentiIscritti.get(index);
            String name= myUser.getUsername();
            String avatar= myUser.getAvatar();
            
            Image myAvatar = Image.getInstance(uploadAvatarPathAssoluta + "/" + avatar);
            myAvatar.scaleToFit(50,50);
            myAvatar.setAlignment(Element.ALIGN_CENTER);
            
            PdfPCell myCell= new PdfPCell();
            myCell.addElement(myAvatar);
            myCell.setColspan(1);
            myCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            myCell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(myCell);
            
            myCell= new PdfPCell(new Paragraph (name));
            myCell.setColspan(1);
            myCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(myCell);
            
            myCell= new PdfPCell(new Paragraph ("Numero post"));
            myCell.setColspan(1);
            myCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(myCell);
            
            myCell= new PdfPCell(new Paragraph ("11-12-13"));
            myCell.setColspan(1);
            myCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(myCell);
            utentiIscritti.remove(index);
        }
        
        document.add(table);
        
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
