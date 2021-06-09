
package servlets.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.Debug;
import utilities.PropertyManager;

/**
 * The <code>LoginFilter</code> 
 * verifies all web requests are from a valid user.
 * 
 * @author Curt Jones (2018)
 * @author Karissa Jelonek (2021)
 */
public class LoginFilter implements Filter {
    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;   
        HttpServletResponse res = (HttpServletResponse) response;
        String file = req.getServletPath();
        /*
        String queryString = req.getQueryString();
        if(queryString !=null) {
            queryString="?".concat(queryString);
            file = file.concat(queryString);
        }
        */
        String action = request.getParameter("control");
        if(action !=null){
            if(action.equalsIgnoreCase("resetpassword") ||
                    action.equalsIgnoreCase("login") ||
                    action.equalsIgnoreCase("forgotpassword")){
                       chain.doFilter(req, res);// Do the other filters in the chain and return
                       return;
            }
        }
            
        HttpSession session = req.getSession(true); 
        common.User user = (common.User) session.getAttribute("user");
        //We do not check for a user object in the session for these requests
        if (file.contains("Login")|| file.contains("login") || 
            file.contains("Logout")|| file.contains("logout")||
            file.contains("forgotpassword")||file.contains("ForgotPassword")||
            file.contains("resetpassword")||file.contains("ResetPassword")) {
            log("1 LoginFilter.doFilter fired for: [" + file+"]");
            chain.doFilter(req, res);// Do the other filters in the chain and return
            return;
        }
        else if (file.contains("Servlet") || file.contains("addWebcam") || 
                file.contains("userWebcams") || file.contains("editWebcam") || file.contains("Display")
                || file.contains("EditWebcam") || file.contains("AddWebcam")){
            log(" 2 LoginFilter.doFilter fired for: [" + file+"]");// This goes into the appache log and can be used for debugging
            if (user == null || user.getUserRole().getRoleName().equals("Guest")) { //Only admins and webcamUsers can access webcam pages
                log(" 3 LoginFilter user is null on " + file);
                String errorMessage = "You must login to access this page.";
                if(user != null) {
                    errorMessage = "You must verify your account to access this page.";
                }
                request.setAttribute("errorMessage", errorMessage);//Set an error message for this request
 
                request.getServletContext()
                //.getRequestDispatcher("/LogoutServlet") //page we want after successful login. 
                .getRequestDispatcher("/jsp/loginScreen.jsp")
                .forward(request, response);  //forward this request to login screen
                return; //We already did the filter chain
            }
            //This is our desired result. We have a valid user for this session and request
            //We pass the request on to the next flter or the request web resource
            chain.doFilter(req, res);
        }
        else if(file.contains("ApproveWebcam") || file.contains("Options")
                || file.contains("approveWebcam") || file.contains("DisplayUsers") || file.contains("editWebcamPurposes")
                || file.contains("DisplayAllWebcams") || file.contains("DisplaySearchedWebcams") 
                || file.contains("EditWebcamPurpose")) {
            log(" 2 LoginFilter.doFilter fired for: [" + file+"]");// This goes into the appache log and can be used for debugging
            if (user == null || user.getUserRole().getRoleName().equals("Guest") 
                    || user.getUserRole().getRoleName().equals("WebcamUser")) {  //Only admins can access ApproveWebcams and Options pages
                log(" 3 LoginFilter user is null on " + file);
                String errorMessage = "You must login as admin to access this page.";
                request.setAttribute("errorMessage", errorMessage);//Set an error message for this request

                request.getServletContext()
                //.getRequestDispatcher("/LogoutServlet") //page we want after successful login. 
                .getRequestDispatcher("/jsp/loginScreen.jsp")
                .forward(request, response);  //forward this request to login screen
                return; //We already did the filter chain
            }
            //This is our desired result. We have a valid user for this session and request
            //We pass the request on to the next flter or the request web resource
            chain.doFilter(req, res);
        }
        else {
            //This section of code is for any other request -- for example --> index.html. 
            //We do not check for valid user. 
            chain.doFilter(req, res); // Do the other filters in the chain
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {        
        this.filterConfig = filterConfig;
        //log("LoginFilter: Initializing filter");
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
    
    public void log(String msg) {
        if(Debug.isEnabled()){ 
            filterConfig.getServletContext().log(msg);   //Shows in the Tomcat logs    
            Debug.println(msg);
        }
    }

}
