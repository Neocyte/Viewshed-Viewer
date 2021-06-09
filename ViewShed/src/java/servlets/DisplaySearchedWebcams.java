package servlets;
// Import required java libraries
import common.Webcam;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Returns all unapproved webcams in a string that creates an HTML table of
 * the data
 * 
 * @author Karissa Jelonek (2021)
 */

// Extend HttpServlet class
@WebServlet(name = "DisplaySearchedWebcams", urlPatterns = {"/DisplaySearchedWebcams"})
public class DisplaySearchedWebcams extends HttpServlet {
 
    private static final int NUM_WEBCAMS_PER_PAGE = 20; 

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   public void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        String returnString;
        //String requestValue = request.getParameter("DisplayWebcamsValue");
        HttpSession session = request.getSession(true);
        //Setting JSP page
        String page = "/jsp/ApproveWebcams.jsp";
        common.User user = (common.User) session.getAttribute("user");
        // Set response content type
        response.setContentType("text/html");
        int cpn = Integer.parseInt(request.getParameter("currentPageNum"));
        database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
        String searchCriteria = request.getParameter("DisplayWebcamsSearchCriteria");       
        Collection<Webcam> c = wm.getAllWebcams();
        int offset; 
        if(request.getParameter("pageAction") == null && cpn == 1) 
        {
            offset = 0; //page 1
        }
        else if(request.getParameter("pageAction") == null)  {
            offset = (cpn - 1) * NUM_WEBCAMS_PER_PAGE;
        }               
        else if(request.getParameter("pageAction").equals("Next"))
        {
            offset = (cpn) * NUM_WEBCAMS_PER_PAGE; 
        }
        else {
            offset = (cpn - 2) * NUM_WEBCAMS_PER_PAGE;
        }

        int userNumber = 0;
        String adminApproved = "true";
        if(searchCriteria.equals("Display Webcams By User ID"))
        {
            userNumber =  Integer.parseInt(request.getParameter("userNumber"));
            c = wm.getWebcamsByUserNumber(userNumber, offset);
        }
        else if(searchCriteria.equals("Display Webcams By Admin Approval")) {
            adminApproved = request.getParameter("adminApproved");
            c = wm.getWebcamsByAdminApproved(adminApproved, offset);
        }
        else if(searchCriteria.equals("Display Webcams By Active")) {
            //TODO
        }

        
        returnString = getAdminHtmlTableFromWebcamCollection(c, request, cpn, searchCriteria, userNumber, adminApproved);

        request.setAttribute("data", returnString);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null){
                dispatcher.forward(request, response);
        }

   }

   
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
       processRequest(request, response);
    }
    
 
        
        /**
        * Returns an HTML table displaying data of the webcams in the given
        * <code>Collection</code> in sets of 20 with each webcamId linking to
        * the approve webcam form
        * @param c a <code>Collection</code> of <code>common.Webcam</code> objects
        * @param request the <code>HttpServletRequest</code> object
        * @param cpn the current page number of the webcam list
        * @param searchCriteria the category the webcams are being searched by
        * @param userNumber the user number being searched for, used only if 
        * searchCriteria is "Display Webcams By User ID"
        * @param adminApproved the approval status being searched for, used only
        * if searchCriteria is "Display Webcams by Admin Approval"
        * @return htmlTable a string that creates an html table of the 
        *  webcam data
        * @return htmlTable a string that creates an html table of the 
        *  webcam data
        */  
        public static String getAdminHtmlTableFromWebcamCollection(Collection<Webcam> c,
                HttpServletRequest request, int cpn, String searchCriteria, int userNumber,
                        String adminApproved)
        {    
            int npn; //next page number
            if(request.getParameter("pageAction") == null) {
                npn = cpn;
            }
            else if(request.getParameter("pageAction").equals("Next")) {
                npn = cpn + 1;
            }
            else {
                npn = cpn - 1;
            }
            int results = 0; //number of results returned
            StringBuilder htmlTable = new StringBuilder();
            common.Webcam cw;
            Iterator it = c.iterator();
            String formAction = "ApproveWebcamForm";
            htmlTable.append("<table>");

            // add header row
            htmlTable.append("<tr>");
            htmlTable.append("<th>Webcam ID</th>");
            htmlTable.append("<th>Webcam Name</th>");
            htmlTable.append("<th>Latitude</th>");
            htmlTable.append("<th>Longitude</th>");
            htmlTable.append("<th>Active?</th>");
            htmlTable.append("<th>Date Submitted</th>");
            htmlTable.append("<th>Approval Status</th>");
            htmlTable.append("</tr>");

            // add all other rows
            while (it.hasNext()) {
                results++;
                cw = (common.Webcam)it.next();
                    htmlTable.append("<tr>");
                    htmlTable.append("<td>");
                    htmlTable.append("<form method='POST' action='");
                    htmlTable.append(formAction);
                    htmlTable.append("' >");
                    htmlTable.append("<input type='hidden' name='display' value='DisplaySearchedWebcams' />");
                    htmlTable.append("<input type='hidden' name='searchCriteria' value='");
                    htmlTable.append(searchCriteria);
                    htmlTable.append("' />");
                    htmlTable.append("<input type='hidden' name='userNumber' value='");
                    htmlTable.append(userNumber);
                    htmlTable.append("' />");
                    htmlTable.append("<input type='hidden' name='adminApproved' value='");
                    htmlTable.append(adminApproved);
                    htmlTable.append("' />");
                    htmlTable.append("<input type='hidden' name='currentPageNum' value='");
                    htmlTable.append(npn);
                    htmlTable.append("' />");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append("<input type='submit' class='btns' name='editWebcamID' value='");
                    htmlTable.append(cw.getWebcamID());
                    htmlTable.append("' />");
                    htmlTable.append("</span>");
                    htmlTable.append("</form>");
                    htmlTable.append("</td>");  
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cw.getWebcamName());
                     htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cw.getLatitude());
                     htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cw.getLongitude());
                     htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    if(cw.isActive() == true) {
                        htmlTable.append("Yes");
                    }
                    else {
                        htmlTable.append("No");
                    }
                     htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cw.getDateSubmitted());
                     htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("<td>");
                    htmlTable.append("<span id='tableData'>");
                    htmlTable.append(cw.getApprovalStatus());
                    htmlTable.append("</span>");
                    htmlTable.append("</td>");
                    htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");
            String returnString = htmlTable.toString();
            //Form to display next or previous page of webcams
            returnString += "<form style='text-align: center; width: 100%; box-sizing: border-box;' method='POST' action='DisplaySearchedWebcams'>";
            if(npn != 1) {
                returnString += "<input type='submit' name='pageAction' id='previousPage' value='Previous' />";
            }
            returnString += "<SPAN style='margin: 5px;'>Page " + npn + "</SPAN>";
            returnString += "<input type='hidden' name='currentPageNum' id='currentPageNum' value='" + npn + "' />";
            returnString += "<input type='hidden' name='DisplayWebcamsSearchCriteria' value='" + searchCriteria + "' >"; 
            if(searchCriteria.equals("Display Webcams By User ID")) {
                returnString += "<input type='hidden' name='userNumber' value='" + userNumber + "' >";
            }
            else if(searchCriteria.equals("Display Webcams By Admin Approval")) {
                returnString += "<input type='hidden' name='adminApproved' value='" + adminApproved + "' >";   
            }
            if(results == NUM_WEBCAMS_PER_PAGE)
            {
                returnString += "<input type='submit' name='pageAction' id='nextPage' value='Next' />";
            }
            returnString += "</form>";

            return returnString;
        }
        
}

