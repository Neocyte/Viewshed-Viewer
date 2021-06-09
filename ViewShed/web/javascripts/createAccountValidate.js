/* 
 * Validate the create account form input on submission
 * 
 * @author Karissa Jelonek (2021)
 */

function validateForm(create_account_form) {
    if(create_account_form.password.value !== create_account_form.confirmPassword.value)
    {
        alert("Passwords do not match.");
        return false;
    }
    if(create_account_form.password.value.length < 8)
    {
        alert("Password must be at least 8 characters");
        return false;
    }
    
    
}
