package servlets;

import common.UserRole;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Receives a request from createAccount.jsp and creates an account
 * 
 * @author Michael O'Donnell
 */
@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {

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
		database.UserManager um = database.Database.getDatabaseManagement().getUserManager();
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/loginScreen.jsp");
                if(um.getUserByLoginName(request.getParameter("email")) != null) {
                    request.setAttribute("errorMessage", "There is already an account with that email."
                            + " Please login or click Forgot Password.");
                    if (requestDispatcher != null)
			requestDispatcher.forward(request, response);
                }
                else {
                    common.User user = new common.User();

                    user.setEmailAddress(request.getParameter("email"));
                    user.setSalt(security.Encryption.generateSalt());
                    user.setUserPassword(security.Encryption.hashString(request.getParameter("password") + user.getSalt()));
                    user.setFirstName(request.getParameter("firstName"));
                    user.setLastName(request.getParameter("lastName"));
                    user.setLoginName(user.getEmailAddress());
                    user.setUserRole(UserRole.Guest);
                    user.setLastLoginTime(LocalDateTime.now());
                    user.setLoginCount(0);

                    um.addUser(user);

                    String message = "Welcome to the Viewshed Viewer. Please click the following link to confirm the account for "
                                    + user.getLoginName() + ":\r\n\r\n" + request.getScheme()
                                    + "://" + request.getServerName() + ":"
                                    + request.getServerPort() + request.getContextPath()
                                    + "/ConfirmAccount?UID="
                                    + um.getUserByLoginName(user.getLoginName()).getUserNumber()
                                    + "\r\n\r\nIf you did not attempt to create an account at The "
                                    + "Viewshed Viewer, please notify your system administrator. "
                                    + "\r\n\r\nFor additional information on the project,"
									+ "simply reply to this email.";

                    utilities.EmailUtility.email(user.getEmailAddress(), message, "Please confirm your Viewshed Viewer account");

                    request.setAttribute("errorMessage", "Your account was successfully created. "
                            + "A confirmation email was sent to " + user.getEmailAddress() 
                            + ". ");
                    if (requestDispatcher != null)
                            requestDispatcher.forward(request, response);
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
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
