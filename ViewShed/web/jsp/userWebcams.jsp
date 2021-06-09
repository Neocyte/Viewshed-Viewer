<%--
    Document   : userWebcams
    Created on : Mar 8, 2021, 1:55:03 AM
    Author     : Karissa Jelonek (2021)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <c:set var="req" value="${HttpServletRequest.req}" />
    <c:set var="uri" value="${req.requestURI}" />
    <c:set var="url">${req.requestURL}</c:set>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/ViewShed/" />
        <link rel="stylesheet" href="styles/navigation.css" type="text/css">
        <link rel="stylesheet" href="styles/userWebcamStyles.css" type="text/css">
        <script src="javascripts/navigation.js"></script>
        <script src="javascripts/logout.js"></script>
        <script>
            sessionStorage.setItem('logged_in_status', "user_is_logged_in"); 
        </script>
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
        <title>My Webcams</title>
    </head>
    <body style='background-color: #909090; width: 100%;'>
        <div class="top-container">
            <h1 class="header">My Webcams</h1>

            <nav class="nav">
                <div class="nav-container">

                    <a href="" class="nav-4">
                        <i class="fa fa-home fa-2x"></i>
                        <div class="tooltip">Home</div>
                    </a>
                    <form action="LogoutServlet" method="POST" class="logout-button" onclick="logout()">
                        <i class="fa fa-sign-out fa-2x"></i>
                        <div class="tooltip">Logout</div>
                    </form>
                    <a href="html/About.html" class="nav-6">
                        <i class="fa fa-info-circle fa-2x"></i>
                        <div class="tooltip">About Us</div>
                    </a>
                    <a href="javascript:void(0);" class="icon" onclick="addHamburgerMenu()">
                        <i class="fa fa-bars"></i>
                    </a>
                </div>
            </nav>
        </div>
        <div id="user_webcams_container">

            <!--Button displayed only to users logged in as admins-->
            <div id="admin-bar" style="display: ${user.userRole.roleName=='SystemAdmin'? 'flex' : 'none'};">
                <form id="login_form" action="Options" method = "POST">              
                    <input id="admin-function-btn" type ="submit" id="cancel_button" name = "options" value="Manage Webcams & Users" class="btn"/>
                    <input id="admin-function-btn" type ="submit" name = "options" value="Display Error Logs" class="btn"/>
                    <input id="admin-function-btn" type ="submit" name = "options" value="Display Properties" class="btn"/>
                    <input id="admin-function-btn" type ="submit" name = "options" value="Add Webcam Purpose" class="btn"/>
                </form>
            </div>
  
            <h2 id="user-webcams-msg">Below you will find a list of the webcams that you currently have registered on the Viewshed Viewer. 
            Click 'Add Webcam' to submit another webcam or click on the ID number of a previously submitted
            webcam to change its display properties. Please note that all webcam submissions and edits must be approved by an administrator before they
            take effect.</h2>
            <a id='add-a-webcam-btn' class="user-webcam-btn" href="jsp/addWebcam.jsp">Add A Webcam</a>

            <br />
            <div id="displayTable">${data}</div>
            
        </div>
    </body>
</html>
