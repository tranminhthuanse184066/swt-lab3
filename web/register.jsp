<%-- 
    Document   : create
    Created on : Jun 11, 2024, 7:42:54 AM
    Author     : Hieu Phi Trinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sample.users.UserError"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store - Register</title>
        <link rel="stylesheet" href="css/register.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container-fluid" style="margin: 50px;">
            <section>
                <form action="MainController" method="POST">
                    <h1>Register</h1>
                    <div class="inputbox">
                        <ion-icon name="person-outline"></ion-icon>
                            ${requestScope.USER_ERROR.userIDError}
                        <input type="text" id="username" name="userID"/>
                        <label for="username">User ID</label> 
                    </div>

                    <div class="inputbox">
                        <ion-icon name="person-outline"></ion-icon>
                            ${requestScope.USER_ERROR.fullNameError}
                        <input type="text" name="fullName" required/>
                        <label for="fullName">Full Name</label>
                    </div>

                    <div class="inputbox">
                        <ion-icon name="lock-closed-outline"></ion-icon>
                        <input type="password" id="password" name="password"/>
                        <label for="password">Password</label>
                    </div>

                    <div class="inputbox">
                        <ion-icon name="lock-closed-outline"></ion-icon>
                            ${requestScope.USER_ERROR.confirmError}
                        <input type="password" id="confirmPassword" name="confirm"/>
                        <label for="confirmPassword">Confirm Password</label>
                    </div>

                    <div id="message" style="font-size: 20px;"></div>
                    <button type="submit" name="action" value="Create">Register</button>
                    <input type="hidden" name="roleID" value="US"/>
                    <input type="hidden" name="newAccountType" value="CreateByUS"/>
                    ${requestScope.USER_ERROR.error}
                    <div class="register">
                        <p>You already have an account? <a href="MainController?action=Login">Login here!</a></p>
                    </div>
                </form>
            </section>
        </div>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>
</html>
