/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Curt Jones
 */
@WebServlet(name = "Options", urlPatterns = {"/Options"})
public class Options extends HttpServlet {

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
            throws ServletException, IOException {   
        String url;
        database.DatabaseManagement dm = database.Database.getDatabaseManagement();
        String action = request.getParameter("options");
        String table=action;
        if(action==null) url = "/CloudExpert" ;
        else if(action.equals("Display Error Logs")){
            database.DatabaseErrorLogManager databaseErrorLogManager = dm.getDatabaseErrorLogManager();
            table = databaseErrorLogManager.getAllErrorLogsAsHTMLTable();
            request.setAttribute("table", table);
            String title = "Error Log Display";
            request.setAttribute("title", title);
            url = "/jsp/tableDisplay.jsp";
        }else if(action.equals("Display Properties")){
            database.DatabasePropertyManager databasePropertyManager = dm.getDatabasePropertyManager();
            table = databasePropertyManager.getAllDatabasePropertiesAsHTMLTable();
            request.setAttribute("table", table);
            String title = "Property Display";
            url = "/jsp/properties.jsp";
        }else if(action.equals("Manage Webcams & Users")){
            String title = "Approve Webcams";
            url = "/DisplayAllWebcams";
        } else if(action.equals("Add Webcam Purpose")) {
           url = "/jsp/editWebcamPurposes.jsp";
        } 
        else{
            url = "/OutsideLoginsOnlyTemplate";
        }
                                
        request.getRequestDispatcher(url).forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
