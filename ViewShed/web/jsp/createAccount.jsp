<%-- 
    Document   : createAccount
    Created on : Mar 6, 2021, 9:26:45 PM
    Author     : Karissa Jelonek (2021)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles/navigation.css" type="text/css">
        <link rel="stylesheet" href="../styles/generalStyles.css" type="text/css">
        <script src="../javascripts/navigation.js"></script>
        <script src="../javascripts/logout.js"></script>
        <script src="../javascripts/createAccountValidate.js"></script>
        <title>Create Account</title>
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    </head>
    <body>
        <div id="container"> 
            <div class="top-container">
                <h1 class="header">Create Account</h1>

                <nav class="nav">
                    <div class="nav-container">
                        <a href="../" class="nav-4">
                            <i class="fa fa-home fa-2x"></i>
                            <div class="tooltip">Home</div>
                        </a>
                        <a href="../html/About.html" class="nav-7">
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

        <%--Container for the create account menu--%>
        <section class = "content_container" id = "small_container" style="width:400px;">

            <header class = "content_title_bar" id="login_header"> 
                <div class = "title" >
                    Create Account
                </div> 
            </header>
            <br />
            <div style="text-align: center;"></div>
            <form id="add_account_form" action="../CreateAccount" method = "POST" onSubmit="return validateForm(add_account_form)" >              
                <div id="image_message_container" style="height: 0;">
                        ${errorMessage}
                </div>
                <div id = "create_account_text_container">      
                    <input type="hidden" name="control" value="createAccount">
                    First Name: <br /> <input type = "text" name="firstName" id="firstName" maxlength="25" placeholder="John" /> <br><br>
                    Last Name:  <br /> <input type = "text" name="lastName" id="lastName" maxlength="35" placeholder="Smith" /> <br><br>
                    Email:  <br /> <input type = "email" name="email" maxlength="50" id="email" placeholder="johnsmith@aol.com" /> <br><br>
                    Password: <br /> <input id="Password" type="password" name="password"  maxlength="64" /><br><br>
                    Confirm Password: <br /> <input id="confirmPassword" type="password" maxlength="64" name="confirmPassword" /><br><br>
                </div>
                <input id="create_account_btn" type ="submit" name = "createAccount" value="Create Account" class="submit_button"/>
                <br>
                <br>
            </form>
                    
        </section>   
        </div>
    </body>
</html>
