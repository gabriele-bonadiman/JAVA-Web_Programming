/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import classi.Gruppo;
import classi.Lista;
import classi.Utente;
import database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.MetodiGruppi;

/**
 *
 * @author FMalesani
 */
public class listaInvitiAppoggio extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Utente ute = (Utente) session.getAttribute("utente");

        List<Lista> listaInviti = MetodiGruppi.listaGruppiUtente(ute.getId());

        if (!listaInviti.isEmpty()) {

            Iterator i = listaInviti.iterator();
            
            while (i.hasNext()) {
                Lista lista = (Lista) i.next();
                Gruppo gr = MetodiGruppi.searchGruppoById(lista.getGruppo());
                System.err.println(request.getParameter(gr.getNome()));
                
                if (gr.getProprietario() != ute.getId()) {
                    if ("on".equals(request.getParameter(gr.getNome()))) {
                        MetodiGruppi.editIscrizione(ute.getId(), gr.getID(), 1);
                        System.err.println("DBManager.editIscrizione("+ute.getId()+", "+gr.getID()+", 1);");
                    } else {
                        MetodiGruppi.editIscrizione(ute.getId(), gr.getID(), 0);
                        System.err.println("DBManager.editIscrizione("+ute.getId()+", "+gr.getID()+", 0);");
                    }
                }
            }
            response.sendRedirect("Home");
        } else{ 
            response.sendRedirect("Home");
        }
//        response.sendRedirect("Home");
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
        } catch (SQLException ex) {
            Logger.getLogger(listaInvitiAppoggio.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(listaInvitiAppoggio.class.getName()).log(Level.SEVERE, null, ex);
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
