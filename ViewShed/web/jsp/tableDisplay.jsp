<%-- 
    Document   : properties
    Created on : Feb 12, 2018, 7:38:23 PM
    Author     : Curt Jones
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
        <title>${title}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css">
        <link rel="stylesheet" href="styles/generalStyles.css" type="text/css">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <script>
            function goBack() {
                window.history.back();
            }
        </script>
        <noscript>
            <meta http-equiv="refresh" content="0; URL=html/javascriptDisabled.html">
        </noscript>
    </head>
    <body class="table_body" style='background-color: #909090'>
        <input onclick="goBack()" type ="button" id="cancel_button" value="Back" class="cancel_button"/>
        <br /><br />
        ${table}
    </body>
</html>
