package servlets;

import common.User;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.WebErrorLogger;

/**
 * <code>ResetPasswordServlet</code> handles requests to reset a user's password.
 * 
 * @author tll92617
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPasswordServlet"})
public class ResetPasswordServlet extends HttpServlet {

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
        
//        WebErrorLogger.log(Level.SEVERE, "Start of ResetPasswordServlet");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user"); 
         if(user == null ){ //If no user then send to login page
//             WebErrorLogger.log(Level.SEVERE, "user was null");
            response.sendRedirect(request.getContextPath() + "/jsp/loginScreen.jsp");
            return;
        }
        int userNumber = user.getUserNumber();

        database.UserManager um = database.Database.getDatabaseManagement().getUserManager();
       
        String newpassword = (String) request.getParameter("newpassword");
        String confirmpassword = (String) request.getParameter("confirmpassword");
        
        if(newpassword != null && confirmpassword != null) {
            newpassword = newpassword.trim();
            confirmpassword =  confirmpassword.trim();
            
            if(!newpassword.equals(confirmpassword)){//should never happen with our Javascript
                getServletContext()
                    .getRequestDispatcher("/html/ResetPassword.html")
                    .forward(request, response);
                return;
            }
            
            String salt = security.Encryption.generateSalt();
            user.setSalt(salt);
            user.setUserPassword(security.Encryption.hashString(newpassword+salt));
            if(user.getLoginCount() <= 0)
                user.setLoginCount(1);
            um.updateUser(user);
            
            emailConfirmation(user);
            request.setAttribute("errorMessage", "Password Reset!");        
            getServletContext()
                    .getRequestDispatcher("/jsp/loginScreen.jsp")
                    .forward(request, response);
            return;
        }
        
WebErrorLogger.log(Level.SEVERE, "about to go to reset passowrd.html"); 
            getServletContext()
                    .getRequestDispatcher("/html/ResetPassword.html")
                    .forward(request, response);
    
    }
    
    private void emailConfirmation(common.User user)
    {
        String message = "Your password for '" + user.getLoginName() + "' has been reset succesfully.\r\n\r\n";
        message += "If you did not reset your password, please contact your system administrator.";
        
        utilities.EmailUtility.email(user.getEmailAddress(), message, "Password Reset Success");
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
