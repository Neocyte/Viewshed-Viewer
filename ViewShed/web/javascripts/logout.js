/*
 * Remove the logged-in status and redirect to the logout servlet. Used in the 
 * onclick attribute of all pages except form pages which have additional 
 * confirmation prompts in addwebcam.js to prevent logging out with unsaved 
 * changes
 * 
 * @author Karissa Jelonek (2021)
 * @author Mason Wu (2021)
 */


// Remove the logged-in status and redirect to the logout servlet
// Used in the onclick attribute of all other pages
function logout() {
    const logoutButton = document.querySelector(".logout-button");
    sessionStorage.removeItem("logged_in_status");
    logoutButton.submit();
}