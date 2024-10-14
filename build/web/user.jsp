<%-- 
    Document   : user
    Created on : May 28, 2024, 9:00:01 AM
    Author     : Hieu Phi Trinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sample.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/styleUser.css">
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url="login.jsp"></c:redirect>
        </c:if>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card text-center">
                        <div class="card-header">
                            <h1>Welcome, ${sessionScope.LOGIN_USER.fullName}!</h1>
                        </div>
                        <div class="card-body">
                            <p><strong>User ID:</strong> ${sessionScope.LOGIN_USER.userID}</p>
                            <p><strong>Full Name:</strong> ${sessionScope.LOGIN_USER.fullName}</p>
                            <p><strong>Role:</strong> ${sessionScope.LOGIN_USER.roleID}</p>

                            <form action="MainController" method="POST" class="d-inline">
                                <input type="hidden" name="action" value="Shopping_Page"/>
                                <input type="hidden" name="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
                                <button type="submit" class="btn btn-success">
                                    <i class="fa fa-shopping-cart"></i> Shopping
                                </button>
                            </form>
                            <form action="MainController" method="POST" class="d-inline">
                                <input type="hidden" name="action" value="Logout"/>
                                <button type="submit" class="btn btn-danger">
                                    Logout
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
