<%-- 
    Document   : addWebcam
    Created on : Mar 6, 2021, 7:15:24 PM
    Author     : Karissa Jelonek (2021)
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles/navigation.css" type="text/css">
        <link rel="stylesheet" href="../styles/generalStyles.css" type="text/css">
        <script src="../javascripts/navigation.js"></script>
        <script src="../javascripts/addWebcam.js"></script>
        <script src="../javascripts/countries.js" type="text/javascript"></script>
        <title>Add Your Webcam</title>
        <!--Icon Library-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Fonts-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    </head>
    <body>
        <div id="container"> 
            <div class="top-container">
                <h1 class="header">Add Your Webcam</h1>

                <nav class="nav">
                    <div class="nav-container">
                        <a href="../" class="nav-4">
                            <i class="fa fa-home fa-2x"></i>
                            <div class="tooltip">Home</div>
                        </a>
                        <form action="../LogoutServlet" method="POST" class="logout-button" onclick="logout()">
                            <i class="fa fa-sign-out fa-2x"></i>
                            <div class="tooltip">Logout</div>
                        </form>
                        <a href="../DisplayWebcams" class="nav-5">
                            <i class="fa fa-camera fa-2x"></i>
                            <div class="tooltip">My Webcams</div>
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

            <%--Container for the add a webcam menu--%>
            <section class = "content_container" id = "add_webcam_container">

                <header class = "content_title_bar" id="add_webcam_header"> 
                    <div class = "title" >
                        Add Your Webcam
                    </div> 
                </header>
                
                <form id="add_webcam_form" name="add_webcam_form" action="../AddWebcam" method = "POST" onSubmit="return checkForm(add_webcam_form)" >                               
                    <div id="image_message_container">
                        ${errorMessage}
                    </div>
                    <div id="add-webcam-container-top" style="padding: 0px 30px 0px 30px">
                        <input type="hidden" name="control" value="addWebcam"> <br />
                        <div>Camera Name:</div> <input  required type="text"  name="cameraName" size="50"  maxlength="50" placeholder="Bloomu Quad Cam" /><br><br>
                        <div>Camera Description:</div> <input required type="text" size="50" maxlength="150" name="cameraDescription"  
                                                               placeholder="Located at Bloomsburg University Quad"/><br><br>
                        <div>Camera URL: <span  id="testURL" onclick="returnURL()">Test URL</span></div> <input  required pattern="https?:\/\/.*" type="text" id="url" name="url" size="50" maxlength="100" placeholder="https://www.bloomu.edu"/><br><br>
                        <script type= "text/javascript" src = "../javascripts/countries.js"></script>

                        <div>Select Country:</div>   <select required id="country" name ="country"></select> </br></br>
                        <div>Select State:</div> <select required name ="state" id ="state"></select> <br /><br>
                        <div>City:</div> <input required type="text" id="city" name="city" size="50" maxlength="50" /><br><br>
                        <script language="javascript">
                            populateCountries("country", "state"); 
                        </script>
                    </div>
                    <div id = "add_webcam_questions_container">

                        <div id="add_webcam_container_left">

                            <label for="cameraPurpose">Webcam Purpose:</label> 
                            <select required id="cameraPurpose" name="cameraPurpose">
                                <%
                                    database.PurposeManager pm = database.Database.getDatabaseManagement().getPurposeManager();
                                    Collection<String> pc = pm.getAllPurposes();
                                    Iterator it = pc.iterator();
                                    String s;
                                    while(it.hasNext())
                                    {
                                        s = it.next().toString();
                                        %>
                                        <option value="<%=s%>"><%=s%></option>
                                        
                                    <% }
                                    
                                %>
                            </select> <br /><br />
                            Camera Latitude: 
                            <div class="tooltip"><img src="../images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">Five decimal precision <br /> + for East <br /> - for West</SPAN> 
                            </div>
                            <br />
                            <select name="latFormat" id="latFormat">
                                <option value="dmsFormat">Degrees Minutes Seconds</option>
                                <option value="ddFormat">Decimal Degrees</option>
                            </select>

                            <div id="latdd"><input onblur="resetCustomValidity('cameraLatitude')" type="text" pattern="-?[0-9]{1,2}\.[0-9]{5,10}" id="cameraLatitude" name="cameraLatitude"  maxlength="13" size="13" placeholder="DD.ddddd"/><br></div>
                            <div name="latdms" id="latdms">
                                <input onblur="resetCustomValidity('cameraLatitudeDegrees')" type="text" pattern="[0-9]{1,2}" id="cameraLatitudeDegrees" name="cameraLatitudeDegrees" size="2" placeholder="DD" maxlength="2" />
                                <input onblur="resetCustomValidity('cameraLatitudeMinutes')" type="text" pattern="[0-9]{1,2}" id="cameraLatitudeMinutes" name="cameraLatitudeMinutes"  size="2" placeholder="MM" maxlength="2" />
                                <input onblur="resetCustomValidity('cameraLatitudeSeconds')" type="textnchange" pattern="^\d{1,2}(\.\d{1,2})?$"  id="cameraLatitudeSeconds" name="cameraLatitudeSeconds" size="5" placeholder="SS" maxlength="5" />
                                <select onchange="resetCustomValidity('cameraLatitudeDirection')" id="cameraLatitudeDirection" name="cameraLatitudeDirection">
                                    <option value=""></option>
                                    <option value="N">N</option>
                                    <option value="S">S</option>
                                </select>
                            </div><br>
                            


                            Camera Longitude: 
                            <div class="tooltip"><img src="../images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">Five decimal precision <br /> + for East <br /> - for West</SPAN> 
                            </div>
                            <br />
                            <select name="longFormat" id="longFormat">
                                <option value="dmsFormat">Degrees Minutes Seconds</option>
                                <option value="ddFormat">Decimal Degrees</option>
                            </select>

                            <div id="longdd"><input onblur="resetCustomValidity('cameraLongitude')"  pattern="-?[0-9]{1,3}\.[0-9]{5,10}" type="text" id="cameraLongitude" name="cameraLongitude"  maxlength="14" size="14" placeholder="DDD.ddddd"/><br></div>
                            <div name="longdms" id="longdms">
                                <input pattern="[0-9]{1,3}" onblur="resetCustomValidity('cameraLongitudeDegrees')" type="text" id="cameraLongitudeDegrees" name="cameraLongitudeDegrees" size="3" placeholder="DDD" maxlength="3" />
                                <input onblur="resetCustomValidity('cameraLongitudeMinutes')" pattern="[0-9]{1,2}" type="text" id="cameraLongitudeMinutes" name="cameraLongitudeMinutes"  size="2" placeholder="MM" maxlength="2" />
                                <input onblur="resetCustomValidity('cameraLongitudeSeconds')" pattern="^\d{1,2}(\.\d{1,2})?$" type="text" id="cameraLongitudeSeconds"  name="cameraLongitudeSeconds" size="5" placeholder="SS" maxlength="5" />
                                <select onchange="resetCustomValidity('cameraLongitudeDirection')" id="cameraLongitudeDirection" name="cameraLongitudeDirection">
                                    <option value=""></option>
                                    <option value="E">E</option>
                                    <option value="W">W</option>
                                </select>
                            </div><br>
                            
                            Height Above Ground: <br />
                            <input id="HAG" required pattern="[0-9]{1,}" type="text" name="heightAboveGround"  value="1" maxlength="7" />   
                            <select onchange="resetCustomValidity('HAGUnits')" name="HAGUnits"  id="HAGUnits">
                                <option value="meters">m</option>
                                <option value="kilometers">km</option>
                                <option value="feet">ft</option>
                                <option value="miles">mi</option>
                            </select>
                            <br><br>
                        </div>

                        <div id="add_webcam_container_right">
                            
                            
                            <input onclick="changeDisabledStatus('inputMinViewRadius')" type="checkbox" id="inputMinViewRadius" name="inputMinViewRadius" value="yes" />
                            Minimum View Radius: <br />
                            <input disabled pattern="[0-9]{0,}" type="text" id="minViewRadius" name="minViewRadius" size="7" value="0" placeholder="0" />
                            <select  disabled onchange="resetCustomValidity('minVRUnits')" name="minVRUnits" id="minVRUnits">
                                <option value="miles">mi</option>
                                <option value="feet">ft</option>
                                <option value="meters">m</option>
                                <option value="kilometers">km</option>
                            </select>
                            <br>
                            <br>
                            <input onclick="changeDisabledStatus('inputMaxViewRadius')" type="checkbox" id="inputMaxViewRadius" name="inputMaxViewRadius" value="yes" />
                            Maximum View Radius:  <br />
                            <input  disabled onblur="resetCustomValidity('maxViewRadius')" pattern="[0-9]{0,}" type="text" id="maxViewRadius" name="maxViewRadius" size="6" value="10" placeholder="10"/>
                            <select disabled onchange="resetCustomValidity('maxVRUnits'); resetCustomValidity('maxViewRadius');" name="maxVRUnits" id="maxVRUnits">
                                <option value="miles">mi</option>
                                <option value="feet">ft</option>
                                <option value="meters">m</option>
                                <option value="kilometers">km</option>
                            </select><br><br>
                            
                            <input onclick="changeDisabledStatus('inputVerticalViewAngle')" type="checkbox" id="inputVerticalViewAngle" name="inputVerticalViewAngle" value="yes" />
                            Vertical View Angle: 
                            <div class="tooltip"><img src="../images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">0 Degrees is Level <br /> Positive Values are Above Level <br /> Negative Values are Below Level</SPAN> 
                            </div><br />
                            <input  disabled onblur="resetCustomValidity('verticalViewAngle')" id="verticalViewAngle" pattern="-?[0-9]{1,2}" type="text" name="verticalViewAngle" value="0" size="3" maxlength="3"/><br><br>
                            
                            <label for="cameraMotion">Camera Orientation:</label> 
                            <select onchange="disableAzimuth()" name="cameraMotion" id="cameraMotion">
                                <option value="static">Static</option>
                                <option value="360DegreeRotation">360 Degree Rotation</option>
                                <option value="manualControl">Variable (Manual Control)</option>
                            </select> <br /><br />
                            
                            Camera Azimuth:
                            <div class="tooltip"><img src="../images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">Direction the center of the camera is facing</SPAN> 
                            </div><br />
                            <input type="radio" name="azimuthFormat" id="selectFormat" value="selectFormat" checked  /> Select A Direction<br>
                            <input type="radio" name="azimuthFormat" id="textFormat" value="textFormat"  /> Enter Azimuth

                            <div id="azimuthTextFormat"> <br />
                                <input onblur="resetCustomValidity('azimuth')" type="text" id="azimuth" name="azimuth" size="3" maxlength="3" placeholder="90"/><br>
                            </div>
                            <div id="azimuthSelectFormat"> 
                                <br />
                                <select onchange="resetCustomValidity('azimuthCheckbox')" id="azimuthCheckbox" name="azimuthCheckbox" >
                                    <option value="north">North</option>
                                    <option value="northeast">Northeast</option>
                                    <option value="east">East</option>
                                    <option value="southeast">Southeast</option>
                                    <option value="south">South</option>
                                    <option value="southwest">Southwest</option>
                                    <option value="west">West</option>
                                    <option value="northwest">Northwest</option>
                                </select>
                            </div><br>   
                        </div>

                    </div>
                    <div style="flex-wrap: wrap; display: flex;">
                        <input onclick="goBack()" type ="button" id="cancel_button" value="Cancel" class="cancel_button"/>
                        <input id="add_webcam_btn" type ="submit" name ="addWebcam" value="Add Webcam" class="submit_button"/>
                    </div>
                    <br /> <br />
                </form>

            </section>   
        </div>
    </body>
    </html>
