<!DOCTYPE html>
<%--ForgotPassword.jsp--%>
<html>
    <head>
        <title>Forgot Password</title> <%--Title Bar--%>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" href="../styles/generalStyles.css" type="text/css">
        <link rel="stylesheet" href="../styles/navigation.css" type="text/css">
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    </head>
    <body>
        <div id="container"> 
            <div class="top-container">
                <h1 class="header">Reset Password</h1>

                <nav class="nav">
                <div class="nav-container">
                    <a href="../" class="nav-3" style="border: none;">
                        <i class="fa fa-home fa-2x"></i>
                        <div class="tooltip">Home</div>
                    </a>
                    <a href="../html/About.html" class="nav-6">
                        <i class="fa fa-info-circle fa-2x"></i>
                        <div class="tooltip">About Us</div>
                    </a>
                    <a href="javascript:void(0);" class="icon" onclick="addHamburgerMenu()">
                        <i class="fa fa-bars"></i>
                    </a>
                </div>
                </nav>
            </div>
        <img id="backPhoto" src="../images/backgroundmap.jpg">
        
        <section class = "content_container" id = "small_container">

            <header class = "content_title_bar" id="login_header"> 
                <div class = "title" >
                    Please enter your email address and click submit to request a new password
                </div> 
            </header>
                
            <form id="forgot_password_form" method="POST" action="../ForgotPasswordServlet">  
                <div id="forgetMessage">${message}</div>
                <div id = "forgot_message_text_container">
                
                    <span style="font-size: 18px; font-weight: bold;">Email Address:</span> <input type="email" name="emailAddress" style="font-size: 18px; padding: 5px;"> 
                    <br><br>
                <br>
                <button type="button" id="resetGoBack" class ="btn-4 btn-4d icon-arrow-left" name="goback" 
                        onclick="location.href='loginScreen.jsp';">Cancel</button>
                <button type="submit" id="resetSubmit" value="Submit" class ="btn btn-4 btn-4d icon-submit" 
                        name="submit">Submit</button>
                </div>
            </form>
        </section>
    </div>
    </body>
</html>
