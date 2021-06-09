/* 
 * Function for the ApproveWebcam page
 * 
 * @author Karissa Jelonek (2021)
 * 
 */

/*
 * Change visibility of searches when search options are selected
 */
function displaySearchCriteriaValues() {
    if(document.getElementById("searchCriteriaAdminApproved") !== null) {
        document.getElementById("searchCriteriaAdminApproved").style.display = "none";
    }
    if(document.getElementById("searchCriteriaActive") !== null) {
        document.getElementById("searchCriteriaActive").style.display = "none";
    }
    if(document.getElementById("searchCriteriaUserNumber") !== null) {
        document.getElementById("searchCriteriaUserNumber").style.display = "none";
    }
    if(document.getElementById("searchCriteriaOptions").value === "userId")
    {
        document.getElementById("searchCriteriaUserNumber").style.display = "block";
    }
    if(document.getElementById("searchCriteriaOptions").value === "adminApproved")
    {
       document.getElementById("searchCriteriaAdminApproved").style.display = "block";
    }
    if(document.getElementById("searchCriteriaOptions").value === "active")
    {
        document.getElementById("searchCriteriaActive").style.display = "block";
    }
}

/*
 *  Opens popup form to search webcams
 */
function openForm() {
    document.getElementById("myForm").style.display = "block";
}

/*
 *  Closes popup form 
 */
function closeForm() {
    document.getElementById("myForm").style.display = "none";
}

/*
 * Prompts admin to confirm that they want to delete the user
 */
function confirmDelete() {
    if(confirm("Are you sure you want to delete this user?") === false) {
        return false;
    }
}

/*
 * Prompts admin to confirm that they want to lock user's account
 */
function confirmLock(lockStatus) {
    if(lockStatus.value === "Lock") {
        if(confirm("Are you sure you want to lock this account?") === false) {
            return false;
        }
    }
    else {
        if(confirm("Are you sure you want to unlock this account?") === false) {
            return false;
        }
    }
 
}

/*
 * Returns to previous page when back button is clicked
 */
function goBack() {
    window.history.back();
}