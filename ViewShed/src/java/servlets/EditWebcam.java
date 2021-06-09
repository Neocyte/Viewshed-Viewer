package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.Debug;
import utilities.WebcamUtil;

/**
 * Receives a request from editWebcam.jsp and edits a webcam in the database.
 * 
 * @author Michael O'Donnell
 */
@WebServlet(name = "EditWebcam", urlPatterns = {"/EditWebcam"})
public class EditWebcam extends HttpServlet {

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
		// This servlet should not be called unless there is already a session.
		HttpSession session = request.getSession();
		database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
		common.User user = (common.User) session.getAttribute("user");
		
		common.Webcam webcam = wm.getWebcamByID(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("cpn", request.getParameter("currentPageNum"));
		if (request.getParameter("submitWebcam").equalsIgnoreCase("Delete Webcam")) {
			wm.deleteWebcam(webcam);
		}
		else {
			webcam.setUserNumber(user.getUserNumber());
			webcam.setWebcamName(request.getParameter("cameraName"));
			webcam.setDescription(request.getParameter("cameraDescription"));
			webcam.setPurpose(request.getParameter("cameraPurpose"));
			webcam.setUrl(request.getParameter("url"));
			if (request.getParameter("latFormat").equals("dmsFormat")) {
				webcam.setLatitude(WebcamUtil.dmsToDecimal(
						Integer.parseInt(request.getParameter("cameraLatitudeDegrees")),
						Integer.parseInt(request.getParameter("cameraLatitudeMinutes")),
						Float.parseFloat(request.getParameter("cameraLatitudeSeconds")),
						request.getParameter("cameraLatitudeDirection")));
			}
			else
				webcam.setLatitude(Float.parseFloat(request.getParameter("cameraLatitude")));
			if (request.getParameter("longFormat").equals("dmsFormat")) {
				webcam.setLongitude(WebcamUtil.dmsToDecimal(
						Integer.parseInt(request.getParameter("cameraLongitudeDegrees")),
						Integer.parseInt(request.getParameter("cameraLongitudeMinutes")),
						Float.parseFloat(request.getParameter("cameraLongitudeSeconds")),
						request.getParameter("cameraLongitudeDirection")));
			}
			else
				webcam.setLongitude(Float.parseFloat(request.getParameter("cameraLongitude")));
			webcam.setCity(request.getParameter("city"));
			webcam.setStateProvinceRegion(request.getParameter("state"));
			webcam.setCountry(request.getParameter("country"));
			webcam.setHeight(Integer.parseInt(request.getParameter("heightAboveGround")));
			webcam.setHeightUnits(WebcamUtil.units(request.getParameter("HAGUnits")));
			webcam.setRotating(request.getParameter("cameraMotion"));
			if (webcam.getRotating().equals("static")) {
				if (request.getParameter("azimuthFormat").equals("selectFormat"))
					webcam.setAzimuth(WebcamUtil.azimuth(request.getParameter("azimuthCheckbox")));
				else
					webcam.setAzimuth(Integer.parseInt(request.getParameter("azimuth")));
			}
			else {
				webcam.setAzimuth(-1);
			}
//			webcam.setFieldOfView(Integer.parseInt(request.getParameter("fieldOfView")));
//			webcam.setRotatingFieldOfView(Integer.parseInt(request.getParameter("rotatingFieldOfView")));
                        if(request.getParameter("inputVerticalViewAngle") != null) {
                            webcam.setVerticalViewAngle(Integer.parseInt(request.getParameter("verticalViewAngle"))); 
                        }
//                      webcam.setVerticalFieldOfView(Integer.parseInt(request.getParameter("verticalFieldOfView")));
                        if(request.getParameter("inputMinViewRadius") != null) {
                            webcam.setMinViewRadius(Integer.parseInt(request.getParameter("minViewRadius")));
                            webcam.setMinViewRadiusUnits(WebcamUtil.units(request.getParameter("minVRUnits")));
                        }
                        if(request.getParameter("inputMaxViewRadius") != null) {
                            webcam.setMaxViewRadius(Integer.parseInt(request.getParameter("maxViewRadius")));                
                            webcam.setMaxViewRadiusUnits(WebcamUtil.units(request.getParameter("maxVRUnits")));
                        }
//			webcam.setWebcamColor(request.getParameter("webcamColor"));
//			webcam.setViewshedColor(request.getParameter("viewshedColor"));
			webcam.setActive(request.getParameter("isActive").equals("yes"));
			if (user.getUserRole().getRoleName().equals("WebcamUser")) {
				webcam.setApprovalStatus("submitted");
				webcam.setDateApproved(null);
			}
			else { // Only other role that can access editWebcam.jsp is SystemAdmin
				webcam.setApprovalStatus("approved");
				webcam.setDateApproved(LocalDateTime.now());
			}

			common.Webcam result = wm.updateWebcam(webcam);
//			Debug.setEnabled(true);
			Debug.println(result.toString());
		}

		response.sendRedirect(request.getContextPath() + "/DisplayWebcams");
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
