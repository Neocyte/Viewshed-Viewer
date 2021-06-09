/*
 * Event handling and input validation for the add webcam, edit webcam, 
 * and approve webcam forms
 * 
 * @author Karissa Jelonek (2021)
 */

var submittingForm = false;
var revokingApproval = false;

/*
 * Returns the user to the previous page
 */
function goBack() {
        window.history.back();
}

/*
 * Sets revokingApproval variable to true when the "Revoke Approval" button is
 * pressed to trigger confirmation prompt in the onsubmit function
 */
function confirmRevoke() {
    revokingApproval = true;
}
    
/*
 * Resets the custom error message when input is changed to
 * allow form to be resubmitted
 */
function resetCustomValidity(element)
{
    document.getElementById(element).setCustomValidity('');
}


/*
 * Changes the latitude input format from dms to dd or dd to 
 * dms when the latitude format checkbox is changed and converts any
 * current latitude input to the correct format
 */
document.addEventListener('DOMContentLoaded', function () {
  var checkbox = document.getElementById('latFormat');
  var ld, lm, ls, ldir, ldd;
  checkbox.addEventListener('change', function () {
    if (checkbox.selectedIndex === 1) {
      resetCustomValidity("cameraLatitudeDegrees");
      resetCustomValidity("cameraLatitudeMinutes");
      resetCustomValidity("cameraLatitudeSeconds");
      resetCustomValidity("cameraLatitudeDirection");
      if(document.getElementById("cameraLatitudeDegrees").value !== "" &&
              document.getElementById("cameraLatitudeMinutes").value !== "" &&
              document.getElementById("cameraLatitudeSeconds").value !== "" &&
              document.getElementById("cameraLatitudeDirection").value !== "")
        {
            ld = parseInt(document.getElementById("cameraLatitudeDegrees").value, 10);
            lm = parseInt(document.getElementById("cameraLatitudeMinutes").value, 10);
            ls = parseFloat(document.getElementById("cameraLatitudeSeconds").value, 10);
            ldir = document.getElementById("cameraLatitudeDirection").value;
            ldd = ld + lm/60 + ls/3600;
            if(ldir === "S") {
                ldd = ldd * -1;
            }
            document.getElementById("latdms").style.display = "none";
            document.getElementById("latdd").style.display = "block";
            document.getElementById("cameraLatitude").value = ldd.toFixed(6);
        }
        else {
            document.getElementById("latdms").style.display = "none";
            document.getElementById("latdd").style.display = "block";
        }
    } else {
       resetCustomValidity("cameraLatitude");
       if(document.getElementById("cameraLatitude").value !== "")
       {
            ldd = parseInt(document.getElementById("cameraLatitude").value, 10);
            if(ldd < 0)
            {
                document.getElementById("cameraLatitudeDirection").value = "S"; 
            }
            else 
            {
                document.getElementById("cameraLatitudeDirection").value = "N"; 
            }
            ldd = Math.abs(parseFloat(document.getElementById("cameraLatitude").value, 10));
            ld = Math.trunc(ldd);
            lm = Math.trunc((ldd - ld) * 60);
            ls = (ldd - ld - lm/60) * 3600;
            ls = ls.toFixed(0);

            document.getElementById("latdd").style.display = "none";
            document.getElementById("latdms").style.display = "block";
            document.getElementById("cameraLatitudeDegrees").value = ld; 
            document.getElementById("cameraLatitudeMinutes").value = lm;
            document.getElementById("cameraLatitudeSeconds").value = ls;
       }
       else {
            document.getElementById("latdd").style.display = "none";
            document.getElementById("latdms").style.display = "block";
       }
           
    }
  });
});



/*
 * Changes the longitude input format from dms to dd or dd to 
 * dms when the longitude format checkbox is changed and converts any
 * current longitude input to the correct format
 */
document.addEventListener('DOMContentLoaded', function () {
  var checkbox = document.getElementById('longFormat');
  var ld, lm, ls, ldir, ldd;
  checkbox.addEventListener('change', function () {
    if (checkbox.selectedIndex === 1) {
      resetCustomValidity("cameraLongitudeDegrees");
      resetCustomValidity("cameraLongitudeMinutes");
      resetCustomValidity("cameraLongitudeSeconds");
      resetCustomValidity("cameraLongitudeDirection");
      if(document.getElementById("cameraLongitudeDegrees").value !== "" &&
              document.getElementById("cameraLongitudeMinutes").value !== "" &&
              document.getElementById("cameraLongitudeSeconds").value !== "" &&
              document.getElementById("cameraLongitudeDirection").value !== "")
        {
            ld = parseInt(document.getElementById("cameraLongitudeDegrees").value, 10);
            lm = parseInt(document.getElementById("cameraLongitudeMinutes").value, 10);
            ls = parseFloat(document.getElementById("cameraLongitudeSeconds").value, 10);
            ldir = document.getElementById("cameraLongitudeDirection").value;
            ldd = ld + lm/60 + ls/3600;
            if(ldir === "W") {
                ldd = ldd * -1;
            }
            document.getElementById("longdms").style.display = "none";
            document.getElementById("longdd").style.display = "block";
            document.getElementById("cameraLongitude").value = ldd.toFixed(6);
        }
        else {
            document.getElementById("longdms").style.display = "none";
            document.getElementById("longdd").style.display = "block";
        }
    } else {
       resetCustomValidity("cameraLongitude");
       if(document.getElementById("cameraLongitude").value !== "")
       {
            ldd = parseInt(document.getElementById("cameraLongitude").value, 10);
            if(ldd < 0)
            {
                document.getElementById("cameraLongitudeDirection").value = "W"; 
            }
            else 
            {
                document.getElementById("cameraLongitudeDirection").value = "E"; 
            }
            ldd = Math.abs(parseFloat(document.getElementById("cameraLongitude").value, 10));
            ld = Math.trunc(ldd);
            lm = Math.trunc((ldd - ld) * 60);
            ls = (ldd - ld - lm/60) * 3600;
            ls = ls.toFixed(0);

            document.getElementById("longdd").style.display = "none";
            document.getElementById("longdms").style.display = "block";
            document.getElementById("cameraLongitudeDegrees").value = ld; 
            document.getElementById("cameraLongitudeMinutes").value = lm;
            document.getElementById("cameraLongitudeSeconds").value = ls;
       }
       else {
            document.getElementById("longdd").style.display = "none";
            document.getElementById("longdms").style.display = "block";
       }
           
    }
  });
});


/*
 * Allows user to test url by opening in a new page
 */
function returnURL() {
    window.open(document.getElementById("url").value);
}


/*
 * Displays alert when user leaves page to ensure they want to leave
 */
window.onbeforeunload = function() {
    if(!submittingForm) {
        return 'Are you sure you want to leave this page?';
    }
};

// Remove the logged-in status and redirect to the logout servlet
// Used in the onclick attribute of all other pages
function logout() {
    submittingForm = true; //prevents window.onbeforeunload from doing another confirmation
    if(confirm('Are you sure you want to leave this page?')) {
        const logoutButton = document.querySelector(".logout-button");
        sessionStorage.removeItem("logged_in_status");
        logoutButton.submit();
    }
    else {
        return false; //stay logged in if user hits cancel
    }
}

function disableAzimuth() {
    if(document.getElementById("cameraMotion").value === "360DegreeRotation" ||
        document.getElementById("cameraMotion").value === "manualControl") {
        if(document.getElementById("textFormat").checked) {
            document.getElementById("selectFormat").disabled = true;
            document.getElementById("textFormat").disabled = true;
            document.getElementById("azimuth").disabled = true; 
        }
        else {
            document.getElementById("selectFormat").disabled = true;
            document.getElementById("textFormat").disabled = true;
            document.getElementById("azimuthCheckbox").disabled = true;
        }   
    }
    else if(document.getElementById("cameraMotion").value === "static") {
        document.getElementById("selectFormat").removeAttribute("disabled");
        document.getElementById("textFormat").removeAttribute("disabled");
        document.getElementById("azimuthCheckbox").removeAttribute("disabled");
        document.getElementById("azimuth").removeAttribute("disabled");
    }
}


/*
 * Changes azimuth input format from checkbox to textfield and
 * populates the textfield with the current user input if applicable
 */
document.addEventListener('DOMContentLoaded', function () {
 var textFormat = document.getElementById('textFormat');
  textFormat.addEventListener('change', function () {
    if (textFormat.checked === true) {
        resetCustomValidity("azimuthCheckbox");
        var setText = "";
        if(document.getElementById("azimuthCheckbox").value === "north")
        {
            setText = "0";
        }
        else if(document.getElementById("azimuthCheckbox").value === "northeast")
        {
            setText = "45";
        }
        else if(document.getElementById("azimuthCheckbox").value === "east")
        {
            setText = "90";
        }
        else if(document.getElementById("azimuthCheckbox").value === "southeast")
        {
            setText = "135";
        }
        else if(document.getElementById("azimuthCheckbox").value === "south")
        {
            setText = "180";
        }
        else if(document.getElementById("azimuthCheckbox").value === "southwest")
        {
            setText = "225";
        }
        else if(document.getElementById("azimuthCheckbox").value === "west")
        {
            setText = "270";
        }
        if(document.getElementById("azimuthCheckbox").value === "northwest")
        {
            setText = "315";
        }
      document.getElementById("azimuthSelectFormat").style.display = "none";
      document.getElementById("azimuthTextFormat").style.display = "block";
      document.getElementById("azimuth").value = setText;
      }
        });
});

/*
 * Changes azimuth input format from textfield to checkbox and
 * selects the checkbox option that matches the current user input if applicable
 */
document.addEventListener('DOMContentLoaded', function () {
 var selectFormat = document.getElementById('selectFormat');
  selectFormat.addEventListener('change', function () {
    resetCustomValidity("azimuth");
    var setCheckbox = "";
    if (selectFormat.checked === true) {
        if(parseInt(document.getElementById("azimuth").value) === 0)
        {
            setCheckbox = "north";
        }
        else if(parseInt(document.getElementById("azimuth").value) === 45)
        {
            setCheckbox = "northeast";
        }
        else if(parseInt(document.getElementById("azimuth").value) === 90)
        {
            setCheckbox = "east";
        }
        else if(parseInt(document.getElementById("azimuth").value) === 135)
        {
            setCheckbox = "southeast";
        }    
        else if(parseInt(document.getElementById("azimuth").value) === 180)
        {
            setCheckbox = "south";
        }
        else if(parseInt(document.getElementById("azimuth").value) === 225)
        {
            setCheckbox = "southwest";
        }
        else if(parseInt(document.getElementById("azimuth").value) === 270)
        {
            setCheckbox = "west";
        }
        else if(parseInt(document.getElementById("azimuth").value) === 315)
        {
            setCheckbox = "northwest";
        }
        
        
       document.getElementById("azimuthTextFormat").style.display = "none";
       document.getElementById("azimuthSelectFormat").style.display = "block";
       document.getElementById("azimuthCheckbox").value = setCheckbox;
    }
  });
});

function confirmDelete() {
    if(!confirm("Are you sure you want to delete this webcam")) {
        return false;
    }
}
/*
 * Validates that user input is in the correct format 
 */
function checkForm(add_webcam_form) {
    if(revokingApproval)
    {
        revokingApproval = false; //reset value
        if(confirm("Are you sure you want to revoke approval for this webcam?") === false) {
            return false;
        }
    }
    submittingForm = true;
    //Max View Radius cannot be greater than 50 km/31 mi/50000 m/ 164041 ft
    if(add_webcam_form.maxVRUnits !== null) {
        if(add_webcam_form.maxVRUnits.value === "miles" &&
               parseInt(add_webcam_form.maxViewRadius.value) > 31 ) {
            add_webcam_form.maxViewRadius.setCustomValidity("Max View Radius must be less than 31 mi");
            return false;
        }
        else if(add_webcam_form.maxVRUnits.value === "meters" &&
               parseInt(add_webcam_form.maxViewRadius.value) > 50000) {
            add_webcam_form.maxViewRadius.setCustomValidity("Max View Radius must be less than 50,000 m");
            return false;
        }
        else if(add_webcam_form.maxVRUnits.value === "feet" &&
               parseInt(add_webcam_form.maxViewRadius.value) > 164041) {
            add_webcam_form.maxViewRadius.setCustomValidity("Max View Radius must be less than 164,041 ft");
            return false;
        }
        else if(add_webcam_form.maxVRUnits.value === "kilometers" &&
               parseInt(add_webcam_form.maxViewRadius.value) > 50) {
            add_webcam_form.maxViewRadius.setCustomValidity("Max View Radius must be less than 50 km");
            return false;
        }
    }
        
    
    
    //azimuth less than 359 must either be entered in textbox or a 
    //direction must be chosen from the dropdown list
    if(add_webcam_form.cameraMotion.value === "static" && add_webcam_form.selectFormat.checked &&
            add_webcam_form.azimuthCheckbox.value === "" && 
            add_webcam_form.cameraMotion.value === "static") {
        add_webcam_form.azimuthCheckbox.setCustomValidity("Please select a direction");
        return false;
    }
    else if(add_webcam_form.cameraMotion.value === "static"  && add_webcam_form.textFormat.checked &&
             (add_webcam_form.azimuth.value === "" || parseInt(add_webcam_form.azimuth.value) < 0 ||
             parseInt(add_webcam_form.azimuth.value) > 359)) {
        add_webcam_form.azimuth.setCustomValidity("Invalid Azimuth Input");
        return false;
    }
    
    //A latitude must be entered in either dms or dd format in
    //the range of -90.000000 - 90.000000
    if(add_webcam_form.latFormat.value === "dmsFormat" )
    {
        if(add_webcam_form.cameraLatitudeDegrees.value === "")
        {
            add_webcam_form.cameraLatitudeDegrees.setCustomValidity("Please Enter Degrees");
            return false;
        }
                    
        if(add_webcam_form.cameraLatitudeMinutes.value === "")
        {
            add_webcam_form.cameraLatitudeMinutes.setCustomValidity("Please Enter Minutes");
            return false;
        }
  
        
        if(add_webcam_form.cameraLatitudeSeconds.value === "")
        {
            add_webcam_form.cameraLatitudeSeconds.setCustomValidity("Please Enter Seconds");
            return false;
        }
        
        if(add_webcam_form.cameraLatitudeDirection.value === "")
        {
            add_webcam_form.cameraLatitudeDirection.setCustomValidity("Please Enter Direction");
            return false;
        }
        
        if(parseInt(add_webcam_form.cameraLatitudeDegrees.value, 10) > 90) {
            
            add_webcam_form.cameraLatitudeDegrees.setCustomValidity("Latitude Degrees Cannot Be Greater Than 90");
            return false;
        }
        if(parseInt(add_webcam_form.cameraLatitudeMinutes.value, 10) > 59) {
            
            add_webcam_form.cameraLatitudeMinutes.setCustomValidity("Latitude Minutes Cannot Be Greater Than 59");
            return false;
        }
        if(parseInt(add_webcam_form.cameraLatitudeSeconds.value, 10) > 59) {
            
            add_webcam_form.cameraLatitudeSeconds.setCustomValidity("Latitude Seconds Cannot Be Greater Than 59");
            return false;
        }

        if(parseInt(add_webcam_form.cameraLatitudeDegrees.value, 10) === 90 &&
               (parseInt(add_webcam_form.cameraLatitudeMinutes.value, 10) !== 0 ||
               parseInt(add_webcam_form.cameraLatitudeSeconds.value, 10) !== 0)) {
                   add_webcam_form.cameraLatitudeDegrees.setCustomValidity("Latitude Degrees Cannot Be Greater Than 90");
            return false;
        }

    }
    if(add_webcam_form.latFormat.value === "ddFormat" )
    {
        if(add_webcam_form.cameraLatitude.value === "")
        {
            add_webcam_form.cameraLatitude.setCustomValidity("Please Enter Latitude");
            return false;
        }
        if(parseFloat(add_webcam_form.cameraLatitude.value, 10) > 90 ||
                parseFloat(add_webcam_form.cameraLatitude.value, 10) < -90) {
            add_webcam_form.cameraLatitude.setCustomValidity("Latitude Cannot Be Greater Than 90.000000");
            return false;
        }

    }
    //A longitude must be entered in either dms or dd format in
    //the range of -180.000000 - 180.000000
    if(add_webcam_form.longFormat.value === "dmsFormat" )
    {
        if(add_webcam_form.cameraLongitudeDegrees.value === "")
        {
            add_webcam_form.cameraLongitudeDegrees.setCustomValidity("Please Enter Degrees");
            return false;
        }
        if(add_webcam_form.cameraLongitudeMinutes.value === "")
        {
            add_webcam_form.cameraLongitudeMinutes.setCustomValidity("Please Enter Minutes");
            return false;
        }
        if(add_webcam_form.cameraLongitudeSeconds.value === "" )
        {
            add_webcam_form.cameraLongitudeSeconds.setCustomValidity("Please Enter Seconds");
            return false;
        }
        if(add_webcam_form.cameraLongitudeDirection.value === "")
        {
            add_webcam_form.cameraLongitudeDirection.setCustomValidity("Please Enter Direction");
            return false;
        }
        if(parseInt(add_webcam_form.cameraLongitudeDegrees.value, 10) > 180) {
            
            add_webcam_form.cameraLongitudeDegrees.setCustomValidity("Longitude Cannot Be Greater Than 180");
            return false;
        }
        if(parseInt(add_webcam_form.cameraLongitudeMinutes.value, 10) > 59) {
            
            add_webcam_form.cameraLongitudeMinutes.setCustomValidity("Longitude Minutes Cannot Be Greater Than 59");
            return false;
        }
        if(parseInt(add_webcam_form.cameraLongitudeSeconds.value, 10) > 59) {
            
            add_webcam_form.cameraLongitudeSeconds.setCustomValidity("Longitude Seconds Cannot Be Greater Than 59");
            return false;
        }
        if(parseInt(add_webcam_form.cameraLongitudeDegrees.value, 10) === 180 &&
               (parseInt(add_webcam_form.cameraLongitudeMinutes.value, 10) !== 0 ||
               parseInt(add_webcam_form.cameraLongitudeSeconds.value, 10) !== 0)) {
                   
            add_webcam_form.cameraLongitudeDegrees.setCustomValidity("Longitude Cannot Be Greater Than 180");
            return false;
        }

    }
    if(add_webcam_form.longFormat.value === "ddFormat" )
    {
        if(add_webcam_form.cameraLongitude.value === "")
        {
            add_webcam_form.cameraLongitude.setCustomValidity("Please Enter Longitude");
            return false;
        }
        if(parseFloat(add_webcam_form.cameraLongitude.value, 10) > 180 ||
                parseFloat(add_webcam_form.cameraLongitude.value, 10) < -180) {
            add_webcam_form.cameraLongitude.setCustomValidity("Longitude Cannot Be Greater Than 180");
            return false;
        }
    }
    
    if(add_webcam_form.HAGUnits.value === "")
    {
        add_webcam_form.HAGUnits.setCustomValidity("Please Enter Units");
        return false;
    }
    if(add_webcam_form.minViewRadius.value !== 0 &&
            add_webcam_form.minVRUnits.value === "")
    {
        add_webcam_form.minVRUnits.setCustomValidity("Please Enter Units");
        return false;
    }
    if(add_webcam_form.maxViewRadius.value !== "" &&
            add_webcam_form.maxVRUnits.value === "")
    {
        add_webcam_form.maxVRUnits.setCustomValidity("Please Enter Units");
        return false;
    } 
    
    //vertical view angle must be between -90(straight downwards) 
    //and 90 (straight upwards)
    if(add_webcam_form.verticalViewAngle.value < -90 || 
            add_webcam_form.verticalViewAngle.value > 90)
    {
        add_webcam_form.verticalViewAngle.setCustomValidity("Angle Must Be Between -90 and 90");
        return false;
    }
        
    return true;
}

/*
 *  Change optional input elements to be disable/not disabled based
 *  on user input
 */
function changeDisabledStatus(id) {
    if(id === "inputMinViewRadius")
    {
        if(document.getElementById(id).checked)
        {
            document.getElementById("minViewRadius").removeAttribute("disabled");
            document.getElementById("minVRUnits").removeAttribute("disabled");
        }
        else {
            document.getElementById('minViewRadius').disabled = true;
            document.getElementById("minVRUnits").disabled = true;
            
        }
        
    }
    else if(id === "inputMaxViewRadius") {
        if(document.getElementById(id).checked)
        {
            document.getElementById("maxViewRadius").removeAttribute("disabled");
            document.getElementById("maxVRUnits").removeAttribute("disabled");
        }
        else {
            document.getElementById('maxViewRadius').disabled = true;
            document.getElementById("maxVRUnits").disabled = true;
            
        }
    }
    else if(id === "inputVerticalViewAngle") {
        if(document.getElementById(id).checked)
        {
            document.getElementById("verticalViewAngle").removeAttribute("disabled");
        }
        else {
            document.getElementById('verticalViewAngle').disabled = true;
        }
    }
}
