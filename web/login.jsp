<%-- 
    Document   : login
    Created on : May 28, 2024, 8:57:30 AM
    Author     : Hieu Phi Trinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="css/styleLogin.css">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Input your information!</h1>
            <form action="MainController" method="POST">
                <input type="text" name="userID" required="" placeholder="Input your ID"/>
                <input type="password" name="password" required="" placeholder="Input your password"/>
                <div class="g-recaptcha" data-sitekey="6Lewcf4pAAAAANiUtYuG72qqWoSpT4VZm8SPdbYp"></div>
                <input type="submit" name="action" value="Login"/>
                <input type="reset" value="Reset"/>
            </form>
            <div>${requestScope.ERROR}</div>
            <a href="MainController?action=Create_Page">Create new Account here!</a>
            <a href="https://www.facebook.com/dialog/oauth?client_id=1511614989774551&redirect_uri=http://localhost:8084/Clothes_Store_JSTL/FacebookController" class="fb-login-btn">
                <i class="fa fa-facebook"></i> Login with Facebook
            </a>
        </div>
    </body>
</html>

