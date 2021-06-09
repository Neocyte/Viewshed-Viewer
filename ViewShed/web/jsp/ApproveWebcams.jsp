<%-- 
    Document   : ApproveWebcams
    Created on : Mar 13, 2021, 3:44:05 PM
    Author     :  Karissa Jelonek (2021)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${HttpServletRequest.req}" />
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url">${req.requestURL}</c:set>
<%@page language="java" import="java.util.*" %>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/ViewShed/" />
        <link rel="stylesheet" href="styles/generalStyles.css" type="text/css">
        <link rel="stylesheet" href="styles/navigation.css" type="text/css">
        <script src="javascripts/navigation.js"></script>
        <script src="javascripts/logout.js"></script>
        <script src="javascripts/approveWebcam.js"></script>
        <title>Approve Webcams</title>
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body style='background-color: #909090; width: 100%'>
        <div id="approve-webcams-page-container">
            <div class="top-container">
                <h1 class="header" style="text-align: center;">Approve Webcams</h1>

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
                        <a href="DisplayWebcams" class="nav-5">
                            <i class="fa fa-camera fa-2x"></i>
                            <div class="tooltip">My Webcams</div>
                        </a>
                        <a href="html/About.html" class="nav-7">
                            <i class="fa fa-info-circle fa-2x"></i>
                            <div class="tooltip">About Us</div>
                        </a>
                        <a href="javascript:void(0);" class="icon" onclick="addHamburgerMenu()">
                            <i class="fa fa-bars"></i>
                        </a>
                    </div>
                </nav>
            </div>
            <input onclick="window.location.href='DisplayWebcams'" type ="button" id="cancel_button" value="Back" class="cancel_button" style="margin-left: 20px"/>
            <button type="button" id="open-button" onclick="openForm()">Search</button>
            
            <div id="admin-display-webcams">
                <form method="POST" id="display-webcam-form" action="DisplayAllWebcams" >
                    <input  type="hidden" name="DisplayWebcamsValue" value="Display All Webcams" />
                    <input type="submit" value="Display All Webcams" class="admin-webcam-btn" id="display-admin-webcams-btn" />
                </form>
                <form method="POST" id="display-webcam-form" action="DisplaySearchedWebcams" >
                    <input type="hidden" name="DisplayWebcamsSearchCriteria" value="Display Webcams By Admin Approval" />
                    <input type="hidden" name="currentPageNum" value="1" />
                    <input type="hidden" name="adminApproved" value="submitted" />
                    <input type="submit" name="DisplaySearchedWebcams"  value="Display Submitted Webcams" class="admin-webcam-btn" id="display-submitted-webcams-btn" />
                </form>
                <form method="POST" id="display-webcam-form" action="DisplayUsers" >
                    <input type="hidden" name="DisplayUsersValue" value="Display Users" />
                    <input type="submit" name="DisplayUsers"  value="Display Users" class="admin-webcam-btn" id="display-users-btn" />
                </form>
            </div>
            
            <div id="displayTable">${data}</div>
        
            
        <div class="form-popup" id="myForm">
          <form  action="DisplaySearchedWebcams" id="search-webcam-form">
            <input type="hidden" name="currentPageNum" id="currentPageNum" value="1" />
            <h1>Search</h1>

            <b>Search By:</b></label>
            <br />
            <select id="searchCriteriaOptions" name="searchCriteriaOptions" onchange="displaySearchCriteriaValues()">
                <option value=""></option>
                <option value="adminApproved">Admin Approved?</option>
                <option value="userId">User ID</option>
            </select>
            <br /><br />
            
            <div id="searchCriteriaUserNumber">
                User Number: <br />
                <input type="text" name="userNumber" pattern="[0-9]{1,}">
                <br /><br />
                    <button type="submit" value="Display Webcams By User ID" name="DisplayWebcamsSearchCriteria" id="PopupSubmitBtn">Submit</button>
                    <br />
            </div>
            <div id="searchCriteriaAdminApproved">
                Admin Approved?
                <br />
                <select name="adminApproved">
                    <option value="submitted">Submitted</option>
                    <option value="approved">Approved</option>
                    <option value="revoked">Revoked</option>
                </select>
                <br /><br />
                <button type="submit" value="Display Webcams By Admin Approval" name="DisplayWebcamsSearchCriteria" id="PopupSubmitBtn">Submit</button>
                <br />
                
            </div>
            
            <button  type="button" id="PopupCancelBtn" onclick="closeForm()">Close</button>
          </form>
            

        </div>
            
        </div>
    </body>
</html>

