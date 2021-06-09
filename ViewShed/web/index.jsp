<!--
The main page that contains the viewshed viewer.
Users can click on the icons in the navigation to access various widgets that manipulate the viewshed.
Other icons provide account functionalities for users to log in and store webcams between sessions.
The top-left contains a search bar that can be toggled to search between general locations and the webcam database.

@author Mason Wu
-->

<%@page import="database.DatabasePropertyManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Viewshed Viewer</title>
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/index.css">

    <!--Fonts-->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">

    <!--Icon Library-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <!--API Key Script-->
    <script>
        <% DatabasePropertyManager pm = database.Database.getDatabaseManagement().getDatabasePropertyManager(); %>
        var esriAPIKey = "<%=pm.getPropertyValue("ESRI API Key")%>";
    </script>

    <!--ArcGIS-->
    <link rel="stylesheet" href="https://js.arcgis.com/4.19/esri/themes/light/main.css" />
    <script src="https://js.arcgis.com/4.19/"></script>

</head>

<body>
    <div id="container">

        <!--Splash screen-->
        <div class="splashscreen">
            <div class="animation-container">
                <div class="cube">
                    <div class="sides">
                        <div class="top"></div>
                        <div class="right"></div>
                        <div class="bottom"></div>
                        <div class="left"></div>
                        <div class="front"></div>
                        <div class="back"></div>
                    </div>
                </div>
            </div>
            <div class="loader-section section-right"><span class="splash-text">Viewshed Viewer</span></div>
        </div>

        <div class="top-container">

            <!--Search Bar-->
            <div class="search-bar"></div>

            <!--Header-->
            <h1 class="header">Viewshed Viewer</h1>

            <!--Navigation-->
            <nav class="nav">
                <div class="nav-container">
                    <a class="nav-1">
                        <i class="fa fa-window-close fa-2x"></i>
                        <div class="tooltip">Clear Viewsheds</div>
                    </a>
                    <a class="nav-2">
                        <i class="fa fa-calculator fa-2x"></i>
                        <div class="tooltip">Viewshed Calculator</div>
                    </a>
                    <a class="nav-3">
                        <i class="fa fa-tv fa-2x"></i>
                        <div class="tooltip">Street View</div>
                    </a>
                    <a href="" class="nav-4">
                        <i class="fa fa-home fa-2x"></i>
                        <div class="tooltip">Home</div>
                    </a>
                    <a href="jsp/loginScreen.jsp" class="nav-5">
                        <i class="fa fa-sign-in fa-2x"></i>
                        <div class="tooltip">Login</div>
                    </a>
                    <a href="DisplayWebcams" class="nav-6">
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

        <!--Progress Bar-->
        <div class="progress-bar">
            <div class="progress">
                <span class="progress-num">0%</span>
            </div>
        </div>

        <!--Viewshed Tool-->
        <div id="viewDiv"></div>

        <!--Mini Street View-->
        <div id="streetview-container">
            <div id="streetview-container-header" class="draggable">
                <span class="streetview-warning">Black = no street view for this location.</span>
                <span class="close1">&times;</span>
            </div>
            <iframe id="streetview" style="width:100%;" src="https://www.google.com/maps/embed/v1/streetview?key=AIzaSyDZKXc347ZwGPp0beJDLVH9ZZcId91Sw9o&location=0,0&heading=0&pitch=0&fov=90"></iframe>
        </div>

        <!--Viewshed Calculator-->
        <form id="calculator" onsubmit="return false;">
            <div id="calculator-header" class="draggable">
                <span class="close2">&times;</span>
            </div>
            <h3>Viewshed Calculator</h3>
            <h4>Enter your viewshed parameters</h4>
            <fieldset>
                <input class="input-lat" name="lat" placeholder="Latitude" type="number" tabindex="1" min="-90" max="90" required step="any" autofocus>
            </fieldset>
            <fieldset>
                <input class="input-long" name="long" placeholder="Longitude" type="number" tabindex="2" min="-180" max="180" step="any" required>
            </fieldset>
            <fieldset>
                <input class="input-distance" name="maxDistance" placeholder="Max View Radius" type="number" tabindex="3" min="1" max="100000" step="any" required>
                <select name="distanceUnits" id="distanceUnits">
                    <option value="Miles">mi</option>
                    <option value="Feet">ft</option>
                    <option value="Meters">m</option>
                    <option value="Kilometers">km</option>
                </select>
            </fieldset>
            <fieldset>
                <input class="input-elevation" name="elevation" placeholder="Observer Height" type="number" tabindex="4" min="0" max="1000" step="any" required>
                <select name="elevationUnits" id="elevationUnits">
                    <option value="Feet">ft</option>
                    <option value="Miles">mi</option>
                    <option value="Meters">m</option>
                    <option value="Kilometers">km</option>
                </select>
            </fieldset>
            <fieldset>
                <input class="input-azimuth" name="azimuth" placeholder="Azimuth" type="number" tabindex="5" min="0" max="360" step="1" required>
            </fieldset>
            <fieldset>
                <input class="input-fov" name="fov" placeholder="Field of View" type="number" tabindex="6" min="0" max="360" step="1" required>
            </fieldset>
            <fieldset>
                <button class="input-submit" name="submit" type="submit" id="contact-submit" data-submit="...Sending">Calculate</button>
            </fieldset>
        </form>
    </div>

    <!--Miscellaneous dynamic events-->
    <script src="javascripts/index.js"></script>
    <script src="javascripts/navigation.js"></script>
    <script src="javascripts/logout.js"></script>
    <script src="javascripts/viewshed.js"></script>
</body>
</html>