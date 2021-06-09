package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Receives a request to lock a user and locks that user unless the locker is
 * attempting to lock themself (only System Admins may access this
 * functionality). This sends an email as a notice to the user about their
 * account's status.
 *
 * @author Michael O'Donnell (2021)
 */
@WebServlet(name = "LockUser", urlPatterns = {"/LockUser"})
public class LockUser extends HttpServlet {

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
		
		common.User userToLock = um.getUserByID(Integer.parseInt(request.getParameter("hiddenUserNumber")));
		
		if (!user.equals(userToLock)) {
			String subject = "";
			String message = "";
			if (userToLock.isLocked()) {
				subject += "Account Unlocked";
				message += "Your account is no longer locked; it has been unlocked by a System Administrator.";
			}
			else {
				subject += "Account Locked";
				message += "Your account has been locked by a System Administrator.\r\n";
				message += "Please contact your System Administrator to unlock your account.";
			}
			userToLock.setLocked(!userToLock.isLocked());
			um.updateUser(userToLock);
			utilities.EmailUtility.email(userToLock.getEmailAddress(), message, subject);
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
