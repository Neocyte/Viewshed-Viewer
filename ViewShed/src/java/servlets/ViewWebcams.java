package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.Debug;

/**
 * Sends webcam data to index.html to display on the map.
 * 
 * @author Michael O'Donnell
 */
@WebServlet(name = "ViewWebcams", urlPatterns = {"/ViewWebcams"})
public class ViewWebcams extends HttpServlet {

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
		database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
		Collection<common.Webcam> webcams = null;
		String req = request.getParameter("hi");
//		Debug.setEnabled(true);
		Debug.println(req);
		/*
		if (req.equals("rect")) {
			webcams = wm.getWebcamsByLocation(
					Float.parseFloat(request.getParameter("west")),
					Float.parseFloat(request.getParameter("south")),
					Float.parseFloat(request.getParameter("east")),
					Float.parseFloat(request.getParameter("north"))
			);
		}
		*/
		webcams = wm.getAllActiveAndApprovedWebcams();
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			out.print(buildWebcamJsonArray(webcams));
		}
		finally {
			out.flush();
			out.close();
		}
	}
	
	private String buildWebcamJsonArray(Collection<common.Webcam> webcams) {
		StringBuilder json = new StringBuilder("{\"data\":[");
		
		for (common.Webcam webcam : webcams) {
			json.append(webcam.toJsonString()).append(',');
		}
		
		json.deleteCharAt(json.length() - 1); // Remove last comma
		json.append("]}");
		
		return json.toString();
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
