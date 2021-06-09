<%-- 
    Document   : editWebcam
    Created on : Mar 6, 2021, 7:15:24 PM
    Author     : Karissa Jelonek (2021)
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="req" value="${HttpServletRequest.req}" />
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url">${req.requestURL}</c:set>
    <!DOCTYPE html>
    <html>
        <head>
            <script>
                function setPurpose() {
                    for(var i = 0; i < document.getElementById("cameraPurpose").options.length; i++)
                    {    
                        if(document.getElementById("cameraPurpose").options[i].value === document.getElementById("purposeValue").textContent )
                        {
                            document.getElementById("cameraPurpose").options[i].selected = true;
                        }
                    } 
                }
            </script>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/ViewShed/" />
            <link rel="stylesheet" href="styles/navigation.css" type="text/css">
            <link rel="stylesheet" href="styles/generalStyles.css" type="text/css">
            <script src="javascripts/navigation.js"></script>
            <script src="javascripts/addWebcam.js"></script>
            <script src="javascripts/countries.js"></script>
            <title>Edit Your Webcam</title>
            <!--Icon Library-->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <!--Fonts-->
            <link rel="preconnect" href="https://fonts.gstatic.com">
            <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    </head>
    <body onload="setPurpose()">
        <div id="container"> 
            <div class="top-container">
                <h1 id="testing" class="header">Edit Your Webcam</h1>

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
            <img id="backPhoto" src="images/backgroundmap.jpg">

            <%--Container for the add a webcam menu--%>
            <section class = "content_container" id = "add_webcam_container">

                <header class = "content_title_bar" id="add_webcam_header"> 
                    <div class = "title" >
                        Edit Your Webcam
                    </div> 
                </header>
                <form id="edit_webcam_form" name="edit_webcam_form" action="EditWebcam" method = "POST" onSubmit="return checkForm(edit_webcam_form)" >
                <input type="hidden" value="${cpn}" name="currentPageNum" />

                    <div id="add-webcam-container-top" style="padding: 0px 30px 0px 30px">
                        <input type="hidden" name="control" value="editWebcam">
			<input type="hidden" name="id" value="${data.webcamID}"><br />
                        Camera Name: <input value="${data.webcamName}" required type="text"  name="cameraName" size="50"  maxlength="50" placeholder="Bloomu Quad Cam" /><br><br>
                        Camera Description: <input required value="${data.description}" type="text" size="50" maxlength="150" name="cameraDescription"  
                                                    placeholder="Located at Bloomsburg University Quad"/><br><br>
                        Camera URL: <span  id="testURL" onclick="returnURL()">Test URL</span><input value="${data.url}" required pattern="https?:\/\/.*" type="text" id="url" name="url" size="50" maxlength="100" placeholder="https://www.bloomu.edu"/><br><br>
                        <script type= "text/javascript" src = "javascripts/countries.js"></script>
                        Select Country:   <select required id="country" name ="country"></select> </br></br>
                        Select State: <select required name ="state" id ="state"></select> <br /><br>
                        City: <br><input  value="${data.city}" required type="text" id="city" name="city" size="50" maxlength="50" /><br><br>

                        <script language="javascript">
                            populateCountries("country", "state", "${data.country}", "${data.stateProvinceRegion}"); 
                        </script>
                    </div>
                    <div id = "add_webcam_questions_container">

                        <div id="add_webcam_container_left">
                            
                            <label for="cameraPurpose">Webcam Purpose*:</label> 
                            <SPAN style="display: none" id="purposeValue">${data.purpose}</SPAN> <!--used in setPurpose function-->
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
                            <div class="tooltip"><img src="images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">Five decimal precision <br /> + for East <br /> - for West</SPAN> 
                            </div>
                            <br />
                            <select name="latFormat" id="latFormat">
                                <option value="dmsFormat">Degrees Minutes Seconds</option>
                                <option selected value="ddFormat">Decimal Degrees</option>
                            </select>

                            <div id="latdd" style="display: block;">
                                <input onblur="resetCustomValidity('cameraLatitude')" value="<fmt:formatNumber type='number' minFractionDigits='5' value='${data.latitude}' />" type="text" pattern="-?[0-9]{1,2}\.[0-9]{5,10}" id="cameraLatitude" name="cameraLatitude"  maxlength="12" size="12" placeholder="DD.ddddd"/><br>
                            </div>
                            <div id="latdms" style="display: none;">
                                <input onblur="resetCustomValidity('cameraLatitudeDegrees')" type="text" pattern="[0-9]{1,2}" id="cameraLatitudeDegrees" name="cameraLatitudeDegrees" size="3" placeholder="DD" maxlength="3" />&deg;
                                <input onblur="resetCustomValidity('cameraLatitudeMinutes')" type="text" pattern="[0-9]{1,2}" id="cameraLatitudeMinutes" name="cameraLatitudeMinutes"  size="2" placeholder="MM" maxlength="2" />'
                                <input onblur="resetCustomValidity('cameraLatitudeSeconds')" type="text" pattern="^\d{1,2}(\.\d{1,2})?$"  id="cameraLatitudeSeconds" name="cameraLatitudeSeconds" size="5" placeholder="SS" maxlength="5" />"
                               <select onchange="resetCustomValidity('cameraLatitudeDirection')" id="cameraLatitudeDirection" name="cameraLatitudeDirection">
                                    <option value=""></option>
                                    <option value="N">N</option>
                                    <option value="S">S</option>
                                </select>
                            </div><br>

                            Camera Longitude: 
                            <div class="tooltip"><img src="images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">Five decimal precision <br /> + for East <br /> - for West</SPAN> 
                            </div>                           
                            <br />
                            <select name="longFormat" id="longFormat">
                                <option value="dmsFormat">Degrees Minutes Seconds</option>
                                <option selected value="ddFormat">Decimal Degrees</option>
                            </select>

                            <div id="longdd" style="display: block;">
                                 <input onblur="resetCustomValidity('cameraLongitude')" value="<fmt:formatNumber type='number' minFractionDigits='5' value='${data.longitude}' />" pattern="-?[0-9]{1,3}\.[0-9]{5,10}" type="text" id="cameraLongitude" name="cameraLongitude"  maxlength="12" size="12" placeholder="DDD.ddddd"/><br>
                            </div>
                            <div id="longdms" style="display: none;">
                                <input onblur="resetCustomValidity('cameraLongitudeDegrees')" pattern="[0-9]{1,3}" type="text" id="cameraLongitudeDegrees" name="cameraLongitudeDegrees" size="3" placeholder="DDD" maxlength="3" />&deg;
                                <input onblur="resetCustomValidity('cameraLongitudeMinutes')" pattern="[0-9]{1,2}" type="text" id="cameraLongitudeMinutes" name="cameraLongitudeMinutes"  size="2" placeholder="MM" maxlength="2" />'
                                <input onblur="resetCustomValidity('cameraLongitudeSeconds')" pattern="^\d{1,2}(\.\d{1,2})?$" type="text" id="cameraLongitudeSeconds" name="cameraLongitudeSeconds" size="5" placeholder="SS" maxlength="5" />"
                                <select onchange="resetCustomValidity('cameraLongitudeDirection')" id="cameraLongitudeDirection" name="cameraLongitudeDirection">
                                    <option value=""></option>
                                    <option value="E">E</option>
                                    <option value="W">W</option>
                                </select>
                            </div><br>

                            Height Above Ground: <br />
                            <input id="HAG" value="${data.height}" required pattern="[0-9]{1,}" type="text" name="heightAboveGround" maxlength="7" placeholder="10"/>   
                            <select onchange="resetCustomValidity('HAGUnits')" name="HAGUnits"  id="HAGUnits">
                                <option ${data.heightUnits.equals("ft")? "selected": ""} value="feet">ft</option>
                                <option ${data.heightUnits.equals("mi")? "selected": ""} value="miles">mi</option>
                                <option ${data.heightUnits.equals("m")? "selected": ""} value="meters">m</option>
                                <option ${data.heightUnits.equals("km")? "selected": ""} value="kilometers">km</option>
                            </select> <br><br>
                            
                            Camera Currently Active? 
                            <select name="isActive">
                                <option ${data.active == "true"? "selected": ""} value="yes">Yes</option>
                                <option ${data.active == "false"? "selected": ""} value="no">No</option>
                            </select>
                        </div>
                        
                        <div id="add_webcam_container_right">  
                            <input onclick="changeDisabledStatus('inputVerticalViewAngle')" type="checkbox" id="inputVerticalViewAngle" name="inputVerticalViewAngle" value="yes" />
                            Vertical View Angle: 
                            <div class="tooltip"><img src="images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">0 Degrees is Level <br /> Positive Values are Above Level <br /> Negative Values are Below Level</SPAN> 
                            </div>
                            <br />
                            <input disabled onblur="resetCustomValidity('verticalViewAngle')" onblur="resetCustomValidity('verticalViewAngle')" id="verticalViewAngle" value="${data.verticalViewAngle}" pattern="-?[0-9]{1,2}" type="text" name="verticalViewAngle" size="2" maxlength="2"/><br><br>

                            <input onclick="changeDisabledStatus('inputMinViewRadius')" type="checkbox" id="inputMinViewRadius" name="inputMinViewRadius" value="yes" />
                            Minimum View Radius: <br />                        
                            <input disabled id="minViewRadius" value="<fmt:formatNumber type='number' maxFractionDigits='0' value='${data.minViewRadius}' />" pattern="[0-9]{0,}" type="text" name="minViewRadius" size="6"  placeholder="0"/>
                            <select disabled onchange="resetCustomValidity('minVRUnits')"  name="minVRUnits" id="minVRUnits">
                                <option value=""></option>
                                <option ${data.minViewRadiusUnits.equals("ft")? "selected": ""} value="feet">ft</option>
                                <option ${data.minViewRadiusUnits.equals("mi")? "selected": ""} value="miles">mi</option>
                                <option ${data.minViewRadiusUnits.equals("m")? "selected": ""} value="meters">m</option>
                                <option ${data.minViewRadiusUnits.equals("km")? "selected": ""} value="kilometers">km</option>
                            </select><br><br>
                            
                            <input onclick="changeDisabledStatus('inputMaxViewRadius')" type="checkbox" id="inputMaxViewRadius" name="inputMaxViewRadius" value="yes" />
                            Maximum View Radius:  <br />
                            <input  onblur="resetCustomValidity('maxViewRadius')" disabled id="maxViewRadius"  value="<fmt:formatNumber type='number' maxFractionDigits='0' value='${data.maxViewRadius}' />" pattern="[0-9]{0,}" type="text" name="maxViewRadius" size="6" placeholder="35"/>
                            <select disabled onchange="resetCustomValidity('maxVRUnits'); resetCustomValidity('maxViewRadius'); " name="maxVRUnits" id="maxVRUnits">
                                <option value=""></option>
                                <option ${data.maxViewRadiusUnits.equals("ft")? "selected": ""} value="feet">ft</option>
                                <option ${data.maxViewRadiusUnits.equals("mi")? "selected": ""} value="miles">mi</option>
                                <option ${data.maxViewRadiusUnits.equals("m")? "selected": ""}  value="meters">m</option>
                                <option ${data.maxViewRadiusUnits.equals("km")? "selected": ""} value="kilometers">km</option>
                            </select> <br /><br>
                            
                            <label for="cameraMotion">Camera Orientation:</label> 
                            <select onchange="disableAzimuth()" name="cameraMotion" id="cameraMotion">
                                <option ${data.rotating.equals("static")? "selected": ""} value="static">Static</option>
                                <option ${data.rotating.equals("360DegreeRotation")? "selected": ""} value="360DegreeRotation">360 Degree Rotation</option>
                                <option ${data.rotating.equals("manualControl")? "selected": ""} value="manualControl">Variable (Manual Control)</option>
                            </select> <br /><br />
                          
                            Camera Azimuth (Direction): 
                            <div class="tooltip"><img src="images/question-mark.png" height="15px" width="15px" /> 
                                <SPAN class="tooltiptext">Direction the center of the camera is facing</SPAN> 
                            </div><br />
                            <input type="radio" name="azimuthFormat" id="selectFormat" value="selectFormat" ${data.rotating.equals("static")? "": "disabled"} /> Select A Direction<br>
                            <input type="radio" name="azimuthFormat" id="textFormat" value="textFormat"  checked ${data.rotating.equals("static")? "": "disabled"} /> Enter Azimuth


                            <div id="azimuthTextFormat" style="display: block;"> <br />
                                <input onblur="resetCustomValidity('azimuth')" ${data.rotating.equals("static")? "": "disabled"} type="text" value="${data.azimuth}" id="azimuth" name="azimuth" size="3" maxlength="3" placeholder="90"/>&deg;<br>
                            </div>
                            <div style="display: none;" id="azimuthSelectFormat">  <br />
                                <select ${data.rotating.equals("static")? "": "disabled"} onchange="resetCustomValidity('azimuthCheckbox')" id="azimuthCheckbox" name="azimuthCheckbox" >
                                    <option value="north">North</option>
                                    <option value="northeast">Northeast</option>
                                    <option value="east">East</option>
                                    <option value="southeast">Southeast</option>
                                    <option value="south">South</option>
                                    <option value="southwest">Southwest</option>
                                    <option value="west">West</option>
                                    <option value="northwest">Northwest</option>
                                </select>
                            </div> <br />
                            <br />
                        </div>

                    </div>
                    <br />
                    <div id="edit_webcam_form_btns" style="display: flex; flex-wrap: wrap;">
                        <input onclick="goBack()" type ="button" id="cancel_button" value="Cancel" class="cancel_button"/>
                        <input type ="submit" name ="submitWebcam" value="Save Changes" class="submit_button"/>
                        <input type ="submit" onclick="return confirmDelete()" name ="submitWebcam" value="Delete" class="submit_button"/>                          
                        
                    </div>
                    <br /><br />
                </form>

            </section>   
        </div>
    </body>
    </html>
