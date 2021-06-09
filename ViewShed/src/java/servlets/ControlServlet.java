package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.Debug;
import utilities.WebErrorLogger;

/**
 * <code>ControlServlet</code> is the main servlet that processes most 
 * navigation requests. This servlet will redirect to other servlets depending 
 * on the attributes passed and page directed from.
 * UPDATE: LoginServlet now handles all login processing. ControlServlet is
 * now exclusively for redirection.
 * @author Joseph Picataggio
 * @author Karissa Jelonek (2021)
 * @author Schyler Kelsch (2021)
 */
@WebServlet(name = "ControlServlet", urlPatterns = {"/ControlServlet"})
public class ControlServlet extends HttpServlet {
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
        
        HttpSession session = request.getSession(true);//Create a new session if one does not exists
        final Object lock = session.getId().intern();//To synchronize the session variable
        database.UserManager um = database.Database.getDatabaseManagement().getUserManager();
        common.User user = (common.User) session.getAttribute("user");
        String action = request.getParameter("control");
        
        log("The control parameter action in Control Servlet is "+action) ;  //testing  message if (action == null) return; 
        if(action == null)   {
            utilities.WebErrorLogger.log(Level.SEVERE, "The control parameter is null in ControlServlet");
            return; 
        }
        //This case is for a successful login
        //We need to update the user table and see if it was their first login
        if(action.trim().equalsIgnoreCase("login")){
          // Update the information in the user table for this user  
            boolean firstLogin = user.getLoginCount() == 0;
            user.setLoginCount(user.getLoginCount()+1);
            LocalDateTime now = LocalDateTime.now();
            user.setLastLoginTime(now);
            user.setAttemptedLoginCount(0);
            user.setLastAttemptedLoginTime(now);
            um.updateUser(user);
        
// Always lock a session variable to be thread safe.
            synchronized(lock){
                session.setAttribute("user", user);//update information in the session attribute for this user
            }

            if(user.getUserRole().getRoleName().equals("SystemAdmin") ||
                    user.getUserRole().getRoleName().equals("WebcamUser")) {
                request.getServletContext()
//                .getRequestDispatcher("/LogoutServlet") //page we want after successful login
                .getRequestDispatcher("/DisplayWebcams") //admins forwarded to admin options page
                .forward(request, response);
            } else if(user.getUserRole().getRoleName().equals("Guest")) {
                request.setAttribute("errorMessage", "You need to validate your account.\nClick the link in your email.");
				request.getServletContext()
                //.getRequestDispatcher("/LogoutServlet")
                .getRequestDispatcher("/jsp/loginScreen.jsp") //guest logins forward to login screen with message
                .forward(request, response);
            }  
        }//end of  code for login action
            // The next code we will write is for the resetpassword action
            if(action.trim().equalsIgnoreCase("resetpassword")){
                user =um.getUserByID(Integer.parseInt(request.getParameter("UID")));
                synchronized(lock){
                    session.setAttribute("user", user);//update information in the session attribute
                }
                if(!user.getUserPassword().equalsIgnoreCase(request.getParameter("token"))){
                    //We have a problem, the url does not have the correct token, reject the attempt
                     //We should contact an admin to state what happened
                   log(user.getLoginName() +" tried to reset a password using the wrong token in the url");
                   log("user id was "+ request.getParameter("UID") );
                   WebErrorLogger.log(Level.SEVERE,user.getLoginName() +" tried to reset a password using the wrong token in the url. "+
                           "user id was "+ request.getParameter("UID"));
                   
                   response.sendRedirect(request.getContextPath() + "/jsp/loginScreen.jsp");
            
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/html/ResetPassword.html"); 
                }
                return;    // return is needed becvause it is a redirect
                          //The difference between a redrect and a forward is important
                        //Look at the URL in the browswer bar and notice a redirect changes it
             }
            
            //Our next action to process is add
             if(action.trim().equalsIgnoreCase("add")){
                 //This is not a valid processing of add, it just shows how to forward to a html file
                //response.sendRedirect(request.getContextPath() + "/html/javascriptDisabled.html");
                request.getServletContext()
                .getRequestDispatcher("/html/javascriptDisabled.html") 
                .forward(request, response);
                return;    // return is needed

             }

            //Other actions go here, this can get long if everything goes to the  control servlet
             
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
        return "ControlServlet is the main Control object for this web application";
    }// </editor-fold>
    
        public void log(String msg) {
        if(Debug.isEnabled()){ 
            super.log(msg);   //Shows in the Tomcat logs    
            Debug.println(msg);
        }
    }
}
