<!DOCTYPE html>
<%-- 
    Document   : editWebcamPurposes
    Created on : Apr 6, 2021, 6:57:01 PM
    Author     : Karissa Jelonek (2021)
--%>

<html>
    <head>
        <title>Edit Webcam Purposes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" href="styles/generalStyles.css" type="text/css">
        <link rel="stylesheet" href="styles/navigation.css" type="text/css">
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
        <script src="javascripts/navigation.js"></script>
        <script src="javascripts/logout.js"></script>
        <script type="text/javascript" src="../javascripts/passwordValidate.js"></script>           
    </head>
   <body>
       <div id="container"> 
            <div class="top-container">
                <h1 class="header">Edit Webcam Purposes</h1>

                <nav class="nav">
                <div class="nav-container">
                    <a href="" class="nav-3" style="border: none;">
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
        <img id="backPhoto" src="images/backgroundmap.jpg">
        
        <section class = "content_container" id = "small_container">

            <header class = "content_title_bar" id="login_header"> 
                <div class = "title" >
                    Edit Webcam Purposes
                </div> 
            </header>

            <form id="edit_webcam_purposes_form" style="width: 100%; padding: 50px 20px;" action="EditWebcamPurpose" method = "POST">              
                <div id = "edit_webcam_purposes_text_container">
                    
                    Add a New Purpose:
                    <input value="" type="text" id="newPurpose" name="newPurpose" required />
                    <br /><br />
                </div>
                <input type="button" style="font-size: 18px;"  id="cancel_button" value="Cancel" onclick="location.href='DisplayWebcams'"/>
                <input type="submit" name="editWebcamPurposesBtn" value="Save" class="submit_button"/>
            </form>
                    
        </section>
       </div>
    </body>
</html>