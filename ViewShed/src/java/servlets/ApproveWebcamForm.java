package servlets;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Receives a request with a webcam ID and forwards the selected webcam
 * to the approveWebcamForm page.
 * 
 * @author Karissa Jelonek (2021)
 * @author Michael O'Donnell (2021)
 */


@WebServlet(name = "ApproveWebcamForm", urlPatterns = {"/ApproveWebcamForm"})
public class ApproveWebcamForm extends HttpServlet {

    //Setting JSP page
    String page="/jsp/approveWebcamForm.jsp";
   
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occur
    */
   public void processRequest(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException
      {
            response.setContentType("text/html");
            response.setContentType("text/html");
			int editWebcamID;
			
			// If we throw a NumberFormatException, we came from the
			// edit-version of the approve webcam form.
			try {
				editWebcamID = Integer.parseInt(request.getParameter("editWebcamID"));
			} catch (NumberFormatException e) {
				editWebcamID = Integer.parseInt(request.getParameter("hiddenIDValue"));
			}
			
            database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
            common.Webcam w = wm.getWebcamByID(editWebcamID);

        request.setAttribute("data", w);
        request.setAttribute("cpn", request.getParameter("currentPageNum"));
        request.setAttribute("display", request.getParameter("display"));
        if(request.getParameter("display") != null && request.getParameter("display").equals("DisplaySearchedWebcams"))
        {
            request.setAttribute("searchCriteria", request.getParameter("searchCriteria"));
            if(request.getParameter("searchCriteria") != null && request.getParameter("searchCriteria").equals("Display Webcams By Admin Approval"))
            {
                request.setAttribute("adminApproved", request.getParameter("adminApproved"));              
            }
            else if(request.getParameter("searchCriteria") != null && request.getParameter("searchCriteria").equals("Display Webcams By User ID")) 
            {
                request.setAttribute("userNumber", request.getParameter("userNumber"));
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null){
                dispatcher.forward(request, response);
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
		processRequest(request, response);
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}


}