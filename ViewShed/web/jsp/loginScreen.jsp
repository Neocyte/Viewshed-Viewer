<!DOCTYPE html>
<%--LoginScreen.jsp--%>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${HttpServletRequest.req}" />
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url">${req.requestURL}</c:set>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/ViewShed/" />
        <link rel="stylesheet" href="styles/generalStyles.css" type="text/css">
        <link rel="stylesheet" href="styles/navigation.css" type="text/css">
        <script src="javascripts/navigation.js"></script>
        <script src="javascripts/logout.js"></script>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
        <noscript>
            <meta http-equiv="refresh" content="0; URL=/html/javascriptDisabled.html">
        </noscript>
    </head>
    <body>
        <div id="container"> 
            <div class="top-container">
                <h1 class="header">Login</h1>

                <nav class="nav">
                <div class="nav-container">
                    <a href="" class="nav-4">
                        <i class="fa fa-home fa-2x"></i>
                        <div class="tooltip">Home</div>
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
        <img id="backPhoto" src="images/backgroundmap.jpg">
        
        <%--Container for the login menu--%>
        <section class = "content_container" id = "small_container">

            <header class = "content_title_bar" id="login_header"> 
                <div class = "title" >
                    Login
                </div> 
            </header>

            <form id="login_form" action="LoginServlet" method = "POST">   
                <div id="image_message_container">
                    ${errorMessage}
                    <c:if test="${not empty errorMessage}">
                    <script>
                        var message = "${errorMessage}";
                        alert(message);
                    </script>
                    </c:if>
                </div>

                <div id = "login_text_container">
                    <input type="hidden" name="control" value="login">
                    <span style="padding-right: 40px;"> Email:</span><input type = "text" name="username"  placeholder="Enter Email Address" value="${username}"/> <br>
                    Password: <input id="loginPassword" type="password" name="password"  placeholder="Enter Password"/><br>
                </div>

                <input type ="submit" name = "login" value="Login" class="submit_button"/>
                <br>
                <br>
            </form>
                <div id="create_account_link">New User? <a href="jsp/createAccount.jsp">Create An Account</a></div>
                <div id="forgot_password">Forgot Password?<a href="jsp/ForgotPassword.jsp" id ="forgot_password_link">Click Here</a></div> <br>
                <br />
        </section>   
        </div>
    </body>
</html>
