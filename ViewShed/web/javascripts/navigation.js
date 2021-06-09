/**
 * Dynamic events for the navigation bar.
 * 
 * @author Mason Wu 
 */

/* Toggle the "responsive" class to nav container when the user clicks on the hamburger icon*/
function addHamburgerMenu() {
    const nav = document.querySelector(".nav-container");
    if (nav.className === "nav-container") {
        nav.className += " responsive";
    } else {
        nav.className = "nav-container";
    }
}



// Grays out the mywebcams button when logged in and replaces the login button with the logout button
// Used only in index.jsp
(function handleButtons() {
    if (document.querySelector("#viewDiv") !== null) {
        const myWebcamsButton = document.querySelector(".nav-6");
        const loginButton = document.querySelector(".nav-5");

        // If you user isn't logged in, disable the mywebcams button
        if (sessionStorage.getItem('logged_in_status') === null) {
            myWebcamsButton.classList.add("grayedOut");
            myWebcamsButton.style.pointerEvents = "none";

        // Otherwise, the user is logged in so replace the login button with the logout button
        } else {
            myWebcamsButton.classList.remove("grayedOut");
            myWebcamsButton.style.pointerEvents = "auto";

            // Dynamically create the logout button
            const logoutButton = document.createElement("form");
            logoutButton.action = "LogoutServlet";
            logoutButton.method = "POST";
            logoutButton.classList.add("logout-button");
            logoutButton.innerHTML = `<i class="fa fa-sign-out fa-2x"></i>
                                                    <div class="tooltip">Logout</div>`;
            loginButton.parentNode.replaceChild(logoutButton, loginButton);

            // Remove the logged-in status and redirect to the logout servlet
            document.querySelector(".logout-button").onclick = function() {
                sessionStorage.removeItem("logged_in_status");
                logoutButton.submit();
            };
        }
    }
}) ();