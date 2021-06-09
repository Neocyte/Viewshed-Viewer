package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Receives a request to delete a user and deletes that user unless the deleter
 * is attempting to delete themself (only System Admins may access this
 * functionality). This also deletes any webcams associated with the deleted
 * user.
 * 
 * @author Michael O'Donnell (2021)
 */
@WebServlet(name = "DeleteUser", urlPatterns = {"/DeleteUser"})
public class DeleteUser extends HttpServlet {

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
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		database.UserManager um = database.Database.getDatabaseManagement().getUserManager();
		common.User user = (common.User) session.getAttribute("user");
		
		common.User userToDelete = um.getUserByID(Integer.parseInt(request.getParameter("hiddenUserNumber")));
		
		if (!user.equals(userToDelete)) {
			// Delete all webcams associated with the user about to be deleted.
			// These webcams need to be deleted due to a constraint in the
			// database.
			database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
			wm.deleteWebcamsByUserNumber(userToDelete.getUserNumber());
			
			um.deleteUser(userToDelete);
			
			String subject = "ACCOUNT DELETED";
			String message = "Your account has been deleted by a System "
					+ "Administrator, and all webcams associated with your "
					+ "account have been removed from our system.";
			utilities.EmailUtility.email(userToDelete.getEmailAddress(), message, subject);
		}
		
		response.sendRedirect(request.getContextPath() + "/DisplayUsers");
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
