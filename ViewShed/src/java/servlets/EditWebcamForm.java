package servlets;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Receives a request with a webcam ID and forwards the selected webcam
 * to the editWebcam page.
 * 
 * @author Karissa Jelonek (2021)
 */


@WebServlet(name = "EditWebcamForm", urlPatterns = {"/EditWebcamForm"})
public class EditWebcamForm extends HttpServlet {

    //Setting JSP page
    String page="/jsp/editWebcam.jsp";
   
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
            request.setAttribute("cpn", request.getParameter("currentPageNum"));
            response.setContentType("text/html");
            response.setContentType("text/html");
            int editWebcamID = Integer.parseInt(request.getParameter("editWebcamID"));
            database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
            common.Webcam w = wm.getWebcamByID(editWebcamID);

        request.setAttribute("data", w);
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