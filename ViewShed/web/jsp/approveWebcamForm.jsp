<%-- 
    Document   : approveWebcamForm
    Created on : Mar 22, 2021, 1:15:53 AM
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
            <title>Approve Webcam</title>
            <!--Icon Library-->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <!--Fonts-->
            <link rel="preconnect" href="https://fonts.gstatic.com">
            <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet">
    </head>
    <body onload="setPurpose(); populateCountries('country', 'state', '${data.country}', '${data.stateProvinceRegion}')"  style='background-color: #909090; width: 100%'>
        <div id="container"> 
            <div class="top-container">
                <h1 class="header">Approve Webcam</h1>

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
 

                
            <br /><div style="text-align: center;"></div>
            <form id="approve_webcam_form" name=approve_webcam_form" action="ApproveWebcam" method = "POST" onSubmit="return checkForm(approve_webcam_form)" >              
                <input type="hidden" value="${data.webcamID}" name="hiddenIDValue" />
                <input type="hidden" value="${cpn}" name="currentPageNum" />
                <input type="hidden" value="${display}" name="display" />
                <input type="hidden" value="${searchCriteria}" name="DisplayWebcamsSearchCriteria" />
                <input type="hidden" value="${adminApproved}" name="adminApproved" />
                <input type="hidden" value="${userNumber}" name="userNumber" />
                <div id="add-webcam-container-top" style="padding: 30px">
                    <div id="approve-tables-container" style="display:flex; ">
                        <div id="approve-table-right-container">
                        <table style=" padding-right: 20px">
                            <tr>
                            <th>Webcam ID</th>
                            <th class="th1">
                                <div class="adminDisplay">${data.webcamID}  </div>
                            </th>
                            </tr>
                            <tr>
                            <th>User ID</th>
                            <th class="th1">
                                <div class="adminDisplay">${data.userNumber}</div>
                            </th>
                            </tr>
                            <tr>
                            <th>Camera Name</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.webcamName}</div>
                                <div id="adminEdit" class="adminEdit"><input value="${data.webcamName}" required type="text"  name="cameraName" size="50"  maxlength="50" placeholder="Bloomu Quad Cam" /></div>
                            </th>
                            </tr>
                            <tr>
                            <th>Camera Description</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.description}</div>
                                <div class="adminEdit"><input required value="${data.description}" type="text" size="50" maxlength="50" name="cameraDescription"  
                                                    placeholder="Located at Bloomsburg University Quad"/></div>
                            </th>
                            </tr>
                            <tr>
                            <th>Camera URL</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.url}</div>
                                <div  class="adminEdit"><input value="${data.url}" required pattern="https?:\/\/.*" type="text" id="url" name="url" size="50" maxlength="100" placeholder="https://www.bloomu.edu"/> </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Country</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.country}</div>
                                <div class="adminEdit">
                                    <select required id="country" name ="country"></select>
                                </div> 
                            </th>
                            </tr>
                            <tr>
                            <th>State</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.stateProvinceRegion}</div>
                                <div class="adminEdit">
                                    <select required name ="state" id ="state"></select>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>City</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.city}</div>
                                <div class="adminEdit">
                                    <input value="${data.city}" required type="text" id="city" name="city" size="50" maxlength="50" />
                                </div>
                            </th>
                            </tr>
                        
                            <tr>
                            <th>Purpose</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: <SPAN id="purposeValue">${data.purpose}</SPAN></div>
                                <div  class="adminEdit">
                            <select required name="cameraPurpose" id="cameraPurpose">
                                <!--The following code gets the webcam purpose options from
                                the purposes database table-->
                                <%
                                    mysql.PurposeManager pm = new mysql.PurposeManager();
                                    Collection<String> pc = pm.getAllPurposes();
                                    Iterator it = pc.iterator();
                                    String s;
                                    while(it.hasNext())
                                    {
                                        s = it.next().toString();
                                        %> 
                                        <option value="<%=s%>"><%=s%></option>
                                        
                                    <% } %>

                            </select>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Latitude</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.latitude}</div>
                                <div class="adminEdit">
                                    <select name="latFormat" id="latFormat">
                                        <option id="latdmsFormat" value="dmsFormat">Degrees Minutes Seconds</option>
                                        <option id="latddFormat" selected value="ddFormat">Decimal Degrees</option>
                                    </select>

                                    <div id="latdd" style="display: block;">
                                        <input onblur="resetCustomValidity('cameraLatitude')" value="<fmt:formatNumber type='number' minFractionDigits='5' value='${data.latitude}' />" type="text" pattern="-?[0-9]{1,2}\.[0-9]{5,10}" id="cameraLatitude" name="cameraLatitude"  maxlength="13" size="13" placeholder="DD.ddddd"/><br>
                                    </div>
                                    <div id="latdms" style="display: none;">
                                        <input onblur="resetCustomValidity('cameraLatitudeDegrees')" type="text" pattern="[0-9]{1,2}" id="cameraLatitudeDegrees" name="cameraLatitudeDegrees" size="3" placeholder="DD" maxlength="3" />&deg;
                                        <input onblur="resetCustomValidity('cameraLatitudeMinutes')" type="text" pattern="[0-9]{1,2}" id="cameraLatitudeMinutes" name="cameraLatitudeMinutes"  size="2" placeholder="MM" maxlength="2" />'
                                        <input onblur="resetCustomValidity('cameraLatitudeSeconds')" type="text" pattern="^\d{1,2}(\.\d{1,2})?$" id="cameraLatitudeSeconds" name="cameraLatitudeSeconds" size="5" placeholder="SS" maxlength="5" />"
                                        <select onchange="resetCustomValidity('cameraLatitudeDirection')"  id="cameraLatitudeDirection" name="cameraLatitudeDirection">
                                            <option value=""></option>
                                            <option value="N">N</option>
                                            <option value="S">S</option>
                                        </select>
                                    </div>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Longitude</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.longitude}</div>
                                <div  class="adminEdit">
                                    <select name="longFormat" id="longFormat">
                                        <option id="longdmsFormat" value="dmsFormat">Degrees Minutes Seconds</option>
                                        <option selected id="longddFormat" value="ddFormat">Decimal Degrees</option>
                                    </select>

                                <div id="longdd" style="display: block;">
                                    <input onblur="resetCustomValidity('cameraLongitude')" value="<fmt:formatNumber type='number' minFractionDigits='5' value='${data.longitude}' />" pattern="-?[0-9]{1,3}\.[0-9]{5,10}" type="text" id="cameraLongitude" name="cameraLongitude"  maxlength="14" size="14" placeholder="DDD.ddddd"/><br>
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
                                </div>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Height Above Ground</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.height} ${data.heightUnits}</div>
                                <div class="adminEdit">
                                    <input id="HAG" required value="${data.height}" pattern="[0-9]{1,}" type="text" name="heightAboveGround" maxlength="7" placeholder="10"/>   
                                    <select onchange="resetCustomValidity('HAGUnits')" name="HAGUnits"  id="HAGUnits">
                                        <option ${data.heightUnits.equals("ft")? "selected": ""} value="feet">ft</option>
                                        <option ${data.heightUnits.equals("mi")? "selected": ""} value="miles">mi</option>
                                        <option ${data.heightUnits.equals("m")? "selected": ""} value="meters">m</option>
                                        <option ${data.heightUnits.equals("km")? "selected": ""} value="kilometers">km</option>
                                    </select>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Azimuth</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.azimuth}</div>
                                <div class="adminEdit">
                                    <input type="radio" name="azimuthFormat" id="selectFormat" value="selectFormat" ${data.rotating.equals("static")? "": "disabled"} /> Select A Direction
                                    <input type="radio" name="azimuthFormat" id="textFormat" value="textFormat"  ${data.rotating.equals("static")? "": "disabled"} checked  /> Enter Azimuth


                                    <div id="azimuthTextFormat" style="display: block;"> <br />
                                        <input ${data.rotating.equals("static")? "": "disabled"} onblur="resetCustomValidity('azimuth')" type="text" value="${data.azimuth}" id="azimuth" name="azimuth" size="3" maxlength="3" placeholder="90"/><br>
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
                                    </div>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Field of View</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.fieldOfView}</div>
                                <div class="adminEdit">
                                    <input value="${data.fieldOfView}" pattern="[0-9]{1,3}" type="text" name="fieldOfView" size="3" maxlength="3"/>
                                </div>
                            </th>
                            </tr>
                        </table>
                        </div>
                        <div id="flexBreak"></div> 
                        <div id="approve-table-right-container">
                        <table>
                            <tr>
                            <th>Camera Orientation</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.rotating}</div>
                                <div class="adminEdit">
                                    <select onchange="disableAzimuth()" name="cameraMotion" id="cameraMotion">
                                        <option ${data.rotating.equals("static")? "selected": ""} value="static">Static</option>
                                        <option ${data.rotating.equals("360DegreeRotation")? "selected": ""} value="360DegreeRotation">360 Degree Rotation</option>
                                        <option ${data.rotating.equals("manualControl")? "selected": ""} value="manualControl">Variable (Manual Control)</option>
                                    </select> <br /><br />
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Rotating Field of View</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.rotatingFieldOfView}</div>
                                <div class="adminEdit">
                                    <input value="${data.rotatingFieldOfView}" pattern="[0-9]{1,3}" type="text" name="rotatingFieldOfView" size="3" maxlength="3"/>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Vertical View Angle</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.verticalViewAngle}</div>
                                <div class="adminEdit">
                                    <input onblur="resetCustomValidity('verticalViewAngle')" id="verticalViewAngle" value="${data.verticalViewAngle}" pattern="-?[0-9]{1,2}" type="text" name="verticalViewAngle" size="2" maxlength="2"/>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Vertical Field of View</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.verticalFieldOfView}</div>
                                <div class="adminEdit">
                                    <input value="${data.verticalFieldOfView}" pattern="[0-9]{1,3}" type="text" name="verticalFieldOfView" size="3" maxlength="3"/>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Minimum View Radius</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: <fmt:formatNumber type='number' maxFractionDigits='0' value='${data.minViewRadius}' /> ${data.minViewRadiusUnits}</div>
                                <div class="adminEdit">
                                    <input id="minViewRadius2" value="<fmt:formatNumber type='number' maxFractionDigits='0' value='${data.minViewRadius}' />" pattern="[0-9]{0,}" type="text" name="minViewRadius" size="6"  placeholder="0"/>
                                    <select onchange="resetCustomValidity('minVRUnits')" name="minVRUnits" id="minVRUnits2">
                                        <option value=""></option>
                                        <option ${data.minViewRadiusUnits.equals("ft")? "selected": ""} value="feet">ft</option>
                                        <option ${data.minViewRadiusUnits.equals("mi")? "selected": ""} value="miles">mi</option>
                                        <option ${data.minViewRadiusUnits.equals("m")? "selected": ""} value="meters">m</option>
                                        <option ${data.minViewRadiusUnits.equals("km")? "selected": ""} value="kilometers">km</option>
                                    </select>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Maximum View Radius</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: <fmt:formatNumber type='number' maxFractionDigits='0' value='${data.maxViewRadius}' /> ${data.maxViewRadiusUnits}</div>
                                <div class="adminEdit">
                                    <input onblur="resetCustomValidity('maxViewRadius')" value="<fmt:formatNumber type='number' maxFractionDigits='0' value='${data.maxViewRadius}' />" pattern="[0-9]{0,}" id="maxViewRadius2" type="text" name="maxViewRadius" size="6" placeholder="35"/>
                                        <select onchange="resetCustomValidity('maxVRUnits'); resetCustomValidity('maxViewRadius');"  name="maxVRUnits" id="maxVRUnits2">
                                            <option value=""></option>
                                            <option ${data.maxViewRadiusUnits.equals("ft")? "selected": ""} value="feet">ft</option>
                                            <option ${data.maxViewRadiusUnits.equals("mi")? "selected": ""} value="miles">mi</option>
                                            <option ${data.maxViewRadiusUnits.equals("m")? "selected": ""}  value="meters">m</option>
                                            <option ${data.maxViewRadiusUnits.equals("km")? "selected": ""} value="kilometers">km</option>
                                        </select>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Camera Color</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.webcamColor}</div>
                                <div class="adminEdit">
                                    <input value="${data.webcamColor}" type="text" name="webcamColor" size="6" placeholder="FFFFFF"/>    
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Viewshed Color</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.viewshedColor}</div>
                                <div class="adminEdit">
                                    <input value="${data.viewshedColor}" type="text" name="viewshedColor" size="6" placeholder="FFFFFF"/> 
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Currently Active?</th>
                            <th class="th1">
                                <div class="adminDisplay">Current Value: ${data.active}</div>
                                <div class="adminEdit">
                                     <select name="isActive">
                                        <option ${data.active == "true"? "selected": ""} value="yes">Yes</option>
                                        <option ${data.active == "false"? "selected": ""} value="no">No</option>
                                    </select>
                                </div>
                            </th>
                            </tr>
                            <tr>
                            <th>Date Submitted</th>
                            <th class="th1">
                                <div class="adminDisplay">${data.dateSubmitted}</div>
                            </th>
                            </tr>
                            <tr>
                            <th>Date Approved</th>
                            <th class="th1">
                                <div class="adminDisplay">${data.dateApproved}</div>                      
                            </th>
                            </tr>
                            <tr>
                            <th>Admin Approved?</th>
                            <th class="th1">
                                <div class="adminDisplay">${data.approvalStatus}</div>
                            </th>
                            </tr>
                        </table>
                        </div>
                    </div>
                        

                 
                            
                            <br /><br />
                            
                        
                           
                                <input onclick="goBack()" type="button" id="cancel_button" name="cancelButton" value="Cancel" class="submit_button"/>                               
                                <input type ="submit" id="approveWebcam" name ="submitWebcam" value="Approve" class="submit_button"/>
                                <input onclick="confirmRevoke()" type ="submit" id="denyWebcam" name ="submitWebcam" value="Revoke" class="submit_button"/> <!--Send email to user why not approved?-->
           
                                <br />
                                

                                
                            
          
                    <br><br>
                </form>

 
        </div>
    </body>
    </html>
