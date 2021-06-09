package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.Debug;

/**
 * Handles search requests from the webcam search on index.html
 * 
 * Note: This currently only handles similar name search.
 *
 * @author Michael O'Donnell
 */
@WebServlet(name = "SearchWebcams", urlPatterns = {"/SearchWebcams"})
public class SearchWebcams extends HttpServlet {

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
		// more database stuff
		Collection<common.Webcam> webcams = null;
		
		if (Boolean.parseBoolean(request.getParameter("submitted"))) {
			// get result
			webcams = new java.util.ArrayList<>();
			webcams.add(wm.getWebcamByID(Integer.parseInt(request.getParameter("query"))));
		}
		else {
			// get suggestions
			webcams = wm.getWebcamsBySimilarName(request.getParameter("query"),
					Integer.parseInt(request.getParameter("limit")));
		}
		
		//String search = request.getParameter("search");
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
//		Debug.setEnabled(true);
		try {
			String json = buildWebcamJsonArray(webcams);
			Debug.println(json);
			out.print(json);
		}
		finally {
			out.flush();
			out.close();
		}
		
		//Debug.setEnabled(true);
		
		request.getParameterMap().forEach((key, value) -> {
			Debug.print(key + ": ");
			Debug.println(Arrays.toString(value));
		});
		
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
