package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.WebcamUtil;

/**
 * Receives a request from approveWebcamForm.jsp and approves, denies, deletes,
 * or modifies a webcam.
 * 
 * @author Michael O'Donnell
 */
@WebServlet(name = "ApproveWebcam", urlPatterns = {"/ApproveWebcam"})
public class ApproveWebcam extends HttpServlet {

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
		common.User user = (common.User) session.getAttribute("user");
		database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
		common.Webcam webcam = wm.getWebcamByID(Integer.parseInt(request.getParameter("hiddenIDValue")));
		common.User webcamOwner = database.Database.getDatabaseManagement().getUserManager().getUserByID(webcam.getUserNumber());
		String url = "/DisplayAllWebcams";
                if(request.getParameter("display") != null && request.getParameter("display").equals("DisplaySearchedWebcams"))
                {
                    url = "/DisplaySearchedWebcams";
                }

		String message = "Dear " + webcamOwner.getFirstName() + " " + webcamOwner.getLastName() + ",\r\n\r\nYour webcam: " + webcam.getWebcamName() + " (ID No. " + webcam.getWebcamID() + ") ";
		String reason = "";
		
		if (user.getUserRole() == common.UserRole.SystemAdmin) {
			switch (request.getParameter("submitWebcam")) {
                                case "Cancel":
                                    break;
				case "Revoke Approval":
					webcam.setApprovalStatus("revoked");
					webcam.setDateApproved(null);
					wm.updateWebcam(webcam);
					message += "has been denied.";
					reason = "WEBCAM DENIED";
					break;
				case "Approve Webcam":
					webcam.setWebcamName(request.getParameter("cameraName"));
					webcam.setDescription(request.getParameter("cameraDescription"));
					webcam.setUrl(request.getParameter("url"));
					webcam.setCountry(request.getParameter("country"));
					webcam.setStateProvinceRegion(request.getParameter("state"));
					webcam.setCity(request.getParameter("city"));
					webcam.setPurpose(request.getParameter("cameraPurpose"));
					if (request.getParameter("latFormat").equals("dmsFormat")) {
						webcam.setLatitude(WebcamUtil.dmsToDecimal(
								Integer.parseInt(request.getParameter("cameraLatitudeDegrees")),
								Integer.parseInt(request.getParameter("cameraLatitudeMinutes")),
								Integer.parseInt(request.getParameter("cameraLatitudeSeconds")),
								request.getParameter("cameraLatitudeDirection")
						));
					}
					else {
							webcam.setLatitude(Double.parseDouble(request.getParameter("cameraLatitude")));
					}
					if (request.getParameter("longFormat").equals("dmsFormat")) {
						webcam.setLongitude(WebcamUtil.dmsToDecimal(
								Integer.parseInt(request.getParameter("cameraLongitudeDegrees")),
								Integer.parseInt(request.getParameter("cameraLongitudeMinutes")),
								Integer.parseInt(request.getParameter("cameraLongitudeSeconds")),
								request.getParameter("cameraLongitudeDirection")
						));
					}
					else {
						webcam.setLongitude(Double.parseDouble(request.getParameter("cameraLongitude")));
					}
					webcam.setHeight(Integer.parseInt(request.getParameter("heightAboveGround")));
					webcam.setHeightUnits(WebcamUtil.units(request.getParameter("HAGUnits")));
					webcam.setRotating(request.getParameter("cameraMotion"));
					if (webcam.getRotating().equals("static")) {
						if (request.getParameter("azimuthFormat").equals("selectFormat")) {
							webcam.setAzimuth(WebcamUtil.azimuth(request.getParameter("azimuthCheckbox")));
						}
						else {
							webcam.setAzimuth(Integer.parseInt(request.getParameter("azimuth")));
						}
					}
					else {
						webcam.setAzimuth(-1);
					}
					webcam.setFieldOfView(Integer.parseInt(request.getParameter("fieldOfView")));
					
					webcam.setRotatingFieldOfView(Integer.parseInt(request.getParameter("rotatingFieldOfView")));
					webcam.setVerticalViewAngle(Integer.parseInt(request.getParameter("verticalViewAngle")));
					webcam.setVerticalFieldOfView(Integer.parseInt(request.getParameter("verticalFieldOfView")));
					webcam.setMinViewRadius(Integer.parseInt(request.getParameter("minViewRadius")));
					webcam.setMinViewRadiusUnits(WebcamUtil.units(request.getParameter("minVRUnits")));
					webcam.setMaxViewRadius(Integer.parseInt(request.getParameter("maxViewRadius")));
					webcam.setMaxViewRadiusUnits(WebcamUtil.units(request.getParameter("maxVRUnits")));
					webcam.setWebcamColor(request.getParameter("webcamColor"));
					webcam.setViewshedColor(request.getParameter("viewshedColor"));
					webcam.setActive(request.getParameter("isActive").equals("yes"));
					webcam.setApprovalStatus("approved");
					webcam.setDateApproved(LocalDateTime.now());
					wm.updateWebcam(webcam);
					message += "has been approved.";
					reason = "WEBCAM APPROVED";
				default:
					break; // unreachable
			}
		}
		
		message += "\r\n\r\nIf this webcam is not yours, or if you do not have "
				+ "a ViewShed account, please contact your System Administrator."
				+ "\r\n\r\nIf you have any questions, simply reply to this email.";
                
                utilities.EmailUtility.email(webcamOwner.getEmailAddress(), message, reason);
		
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
