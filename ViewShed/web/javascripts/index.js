/**
 * Miscellaneous dynamic events for the main page.
 *
 * @author Mason Wu
 */

/**
 * Ensures that the splash screen is loaded only once per session
  */
(function loadSplashScreen() {
    const splashScreen = document.querySelector(".splashscreen");
    const body = document.querySelector("body");
    
    // if the application has not been visited, toggle the flag, and load the splashscreen
    if (!sessionStorage.isVisited) {
        sessionStorage.isVisited = "true";
        
        // adds "loaded" animation to splashscreen after 2 seconds
        setTimeout(function() {
            body.classList.add("loaded");
        }, 2000);

        // removes the splashscreen after 2 seconds
        setTimeout(function() {
            splashScreen.style.visibility = "hidden";
        }, 2000);
        
    // otherwise, the application has already been visited so hide the splashscreen
    } else {
        splashScreen.style.visibility = "hidden";
    }
    
    // if the refresh button is clicked, reset the splashscreen
    if (window.performance.navigation.type === 1) {
        document.querySelector(".splashscreen").style.visibility = "visible";
        
        // adds "loaded" animation to splashscreen after 2 seconds
        setTimeout(function() {
            body.classList.add("loaded");
        }, 2000);

        // removes the splashscreen after 2 seconds
        setTimeout(function() {
            splashScreen.style.visibility = "hidden";
        }, 2000);
    }
})();

/**
 * Makes widgets draggable
 */
function dragElement(elmnt) {
    var PADDING = 8;
    var rect;
    var viewport = {
      bottom: 0,
      left: 0,
      right: 0,
      top: 0
    }
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    if (document.getElementById(elmnt.id + "-header")) {
        /* if present, the header is where you move the DIV from:*/
        document.getElementById(elmnt.id + "-header").onmousedown = dragMouseDown;
    } else {
        /* otherwise, move the DIV from anywhere inside the DIV:*/
        elmnt.onmousedown = dragMouseDown;
    }

    function dragMouseDown(e) {
        e = e || window.event;
        // get the mouse cursor position at startup:
        pos3 = e.clientX;
        pos4 = e.clientY;

        // store the current viewport and element dimensions when a drag starts
        rect = elmnt.getBoundingClientRect();
        const viewDiv_rect = document.querySelector("#viewDiv").getBoundingClientRect();
        viewport.bottom = window.innerHeight - PADDING;
        viewport.left = PADDING;
        viewport.right = window.innerWidth - PADDING;
        viewport.top = viewDiv_rect.top + PADDING;

        document.onmouseup = closeDragElement;
        // call a function whenever the cursor moves:
        document.onmousemove = elementDrag;
    }

    function elementDrag(e) {
        e = e || window.event;
        // calculate the new cursor position:
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;

        // check to make sure the element will be within our viewport boundary
        var newLeft = elmnt.offsetLeft - pos1;
        var newTop = elmnt.offsetTop - pos2;

        if (newLeft < viewport.left
            || newTop < viewport.top
            || newLeft + rect.width > viewport.right
            || newTop + rect.height > viewport.bottom
        ) {
            // the element will hit the boundary, do nothing...
        } else {
          // set the element's new position:
          elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
          elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
        }
    }

    function closeDragElement() {
        /* stop moving when mouse button is released:*/
        document.onmouseup = null;
        document.onmousemove = null;
    }
}

dragElement(document.getElementById("calculator"));
dragElement(document.getElementById("streetview-container"));

/**
 * Toggles widgets
 */
(function toggleWidgets() {
    let calculator = document.querySelector("#calculator");
    let streetview = document.querySelector("#streetview-container");

    // Close calculator on x
    document.getElementsByClassName("close2")[0].onclick = function() {
        calculator.style.display = "none";
        document.querySelector(".input-lat").value = "";
        document.querySelector(".input-long").value = "";
        document.querySelector(".input-distance").value = "";
        document.querySelector(".input-elevation").value = "";
        document.querySelector(".input-azimuth").value = "";
        document.querySelector(".input-fov").value = "";
    };

    // Close streetview on x
    document.getElementsByClassName("close1")[0].onclick = function() {
        streetview.style.display = "none";
    };

    // Toggle calculator
    document.getElementsByClassName("nav-2")[0].onclick = function() {
        if (calculator.style.display === "block") {
            calculator.style.display = "none";
        } else {
            calculator.style.display = "block";
        }
    };

    // Toggle streetview
    document.getElementsByClassName("nav-3")[0].onclick = function() {
        if (streetview.style.display === "block") {
            streetview.style.display = "none";
        } else {
            streetview.style.display = "block";
        }
    };
})();

// Alerts IE users of no support
(function IE_not_supported() {
    if (navigator.appName === 'Microsoft Internet Explorer' 
        ||  !!(navigator.userAgent.match(/Trident/) 
        || navigator.userAgent.match(/rv:11/)) 
        || (typeof navigator.userAgent !== "undefined" 
        && navigator.userAgent.indexOf("MSIE") === 1)) {
        alert("IE does not support advanced web graphics.\nPlease use Chrome, Firefox, or Safari.");
    }
})();