/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import common.User;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kmj83477
 */
public class DisplayUsers extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        String returnString;
        //String requestValue = request.getParameter("DisplayWebcamsValue");
        HttpSession session = request.getSession(true);
        //Setting JSP page
        String page = "/jsp/ApproveWebcams.jsp";
        common.User user = (common.User) session.getAttribute("user");

        

        // Set response content type
        response.setContentType("text/html");
        database.UserManager um = database.Database.getDatabaseManagement().getUserManager();

        
        Collection<User> c = um.getAllUsers();
        returnString = getAdminHtmlTableFromUserCollection(c);  

        request.setAttribute("data", returnString);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null){
                dispatcher.forward(request, response);
        }
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
        * Returns an HTML table displaying data of the webcams in the given
        * <code>Collection</code> with each webcamId linking to the approve
        * webcam form
        * @param c a <code>Collection</code> of <code>common.Webcam</code> objects
        * @return htmlTable a string that creates an html table of the 
        *  webcam data
        */  
        public static String getAdminHtmlTableFromUserCollection(Collection<User> c)
        {       
            StringBuilder htmlTable = new StringBuilder();
            common.User cu;
            Iterator it = c.iterator();
            String formAction = "";

            htmlTable.append("<table id='usersTable'>");

            // add header row
            htmlTable.append("<tr>");
            htmlTable.append("<th>User ID</th>");
            htmlTable.append("<th>Email</th>");
            htmlTable.append("<th>Last Name</th>");
            htmlTable.append("<th>First Name</th>");
            htmlTable.append("<th>User Role</th>");          
            htmlTable.append("<th>Account Locked?</th>"); 
            htmlTable.append("<th>Lock</th>"); 
            htmlTable.append("<th>Delete User</th>");
            htmlTable.append("</tr>");

            // add all other rows
            while (it.hasNext()) {
                cu = (common.User)it.next();
                    htmlTable.append("<tr>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cu.getUserNumber());
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");  
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cu.getEmailAddress());
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cu.getFirstName());
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cu.getLastName());
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cu.getUserRole().getRoleName());
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cu.isLocked());
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append("<form action='LockUser' onSubmit='return confirmLock(manageUsersSubmitBtn)' method='POST'>");
                    htmlTable.append("<input type='hidden' name='hiddenUserNumber' value='");
                    htmlTable.append(cu.getUserNumber());
                    htmlTable.append("'>");
                    if(cu.isLocked()) 
                    {
                        htmlTable.append("<span id='tableData'>");
                        htmlTable.append("<input style='padding: 3px' type='submit' name='manageUsersSubmitBtn' value='Unlock'>");
                        htmlTable.append("</span>");
                    }
                    else {
                        htmlTable.append("<span id='tableData'>");
                        htmlTable.append("<input style='padding: 3px' type='submit' name='manageUsersSubmitBtn' value='Lock'>");
                        htmlTable.append("</span>");
                    }
                    htmlTable.append("</form>");
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<form action='DeleteUser' method='POST' onSubmit='return confirmDelete()'>");
                    htmlTable.append("<input type='hidden' name='hiddenUserNumber' value='");
                    htmlTable.append(cu.getUserNumber());
                    htmlTable.append("'>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append("<input style='padding: 3px' type='submit' name='manageUsersSubmitBtn' value='Delete User'>");
                    htmlTable.append("</span>");
                    htmlTable.append("</form>");
                    htmlTable.append("</td>");
            }

            htmlTable.append("</table>");

            return htmlTable.toString();
        }
}