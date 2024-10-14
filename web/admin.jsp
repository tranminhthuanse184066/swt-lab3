<%-- 
    Document   : admin
    Created on : May 28, 2024, 9:01:01 AM
    Author     : Hieu Phi Trinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sample.clothing.ClothesDTO"%>
<%@page import="java.util.List"%>
<%@page import="sample.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>

        <c:if test ="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'AD'}">
            <c:redirect url = "login.jsp"></c:redirect>
        </c:if>
        <h1>Welcome: ${sessionScope.LOGIN_USER.fullName}</h1>
        <!--Log out-->
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Logout">
        </form>

        <!--Create User-->
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Create_Page"/>
        </form>

        <!--Search User-->
        <form action="MainController" method="GET">
            Search User:<input type="text"  name="search" value="${param.search}"/>
            <input type="submit" name="action" value="Search"/>
        </form>

        <!--Search Product-->
        <form action="MainController">
            Search Product : <input type="text" name="searchProduct" value="${param.searchProduct}"/>
            <input type="submit" name="action" value="SearchProduct"/>
            <input type="hidden" name="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
        </form>

        <!--Add product-->
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="AddProduct_Page"/>
        </form>

        <!--Get all Order List-->
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="OrderList"/>
        </form>

        <!--Export Orders to Excel-->
        <a href="MainController?action=Excel">Export Orders to Excel</a> <br/>

        ${requestScope.MESSAGE}

        ${requestScope.ERROR}

        <!--1-->
        <c:if test="${requestScope.LIST_USER != null}">
            <c:if test="${not empty requestScope.LIST_USER}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>User ID</th>
                            <th>Full Name</th>
                            <th>Role ID</th>
                            <th>Password</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" varStatus="counter" items="${requestScope.LIST_USER}">
                        <form action="MainController">
                            <tr>
                                <td>${counter.count}</td>

                                <td>
                                    <input type="text" name="userID" value="${user.userID}" readonly="" required=""/>
                                </td>
                                <td>
                                    <input type="text" name="fullName" value="${user.fullName}" required=""/>
                                </td>
                                <td>
                                    <input tytype="text" name="roleID" value="${user.roleID}" required=""/>
                                </td>
                                <td>${user.password}</td>

                                <!--Update-->
                                <td>
                                    <input type="submit" name="action" value="Update"/>
                                    <input type="hidden" name="userID" value="${user.userID}"/>
                                    <input type="hidden" name="search" value="${param.search}"/>
                                </td>

                                <!--Delete-->
                                <td>
                                    <c:url var="deleteLink" value="MainController">
                                        <c:param name="action" value="Delete"></c:param>
                                        <c:param name="userID" value="${user.userID}"></c:param>
                                        <c:param name="search" value="${param.search}"></c:param>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </c:if>
    <!--end 1-->

    <!--2-->
    <c:if test="${requestScope.LIST_PRODUCT != null}">
        <c:if test="${not empty requestScope.LIST_PRODUCT}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Link image</th>
                        <th>status</th>
                        <th>Delete</th>
                        <th>Update</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" varStatus="counter" items="${requestScope.LIST_PRODUCT}">
                    <form action="MainController">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <input type="text" name="productID" value="${product.productID}" readonly=""/>
                            </td>

                            <td>
                                <input type="text" name="productName" required="" value="${product.productName}"/>
                            </td>

                            <td>
                                <input type="number" step=0.01 min="0" required="" name="price" value="${product.price}" $/> 
                            </td>

                            <td>
                                <input type="number" min="0" required="" name="quantity" value="${product.quantity}"/> 
                            </td>

                            <td>
                                <input type="text" name="image" required="" value="${product.image}"/> 
                            </td>
                            
                            <td>
                                <input type="text" name="status" value="${product.status}"/> 
                            </td>
                            <!--Delete-->
                            <td>
                                <c:url var="deleteProductLink" value="MainController">
                                    <c:param name="action" value="DeleteProduct"></c:param>
                                    <c:param name="productID" value="${product.productID}"></c:param>
                                    <c:param name="searchProduct" value="${param.searchProduct}"></c:param>
                                    <c:param name="roleID" value="${sessionScope.LOGIN_USER.roleID}"></c:param>
                                </c:url>
                                <a href="${deleteProductLink}">Delete Product</a>
                            </td>

                            <!--Update-->
                            <td>
                                <input type="submit" name="action" value="UpdateProduct"/>
                                <input type="hidden" name="searchProduct" value="${param.searchProduct}"/>
                                <input type="hidden" name="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</c:if>
<!--end 2-->

<c:if test="${requestScope.LIST_ORDER != null}">
    <c:if test="${not empty requestScope.LIST_ORDER}">
        <table border="1">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>User ID</th>
                    <th>Order Date</th>
                    <th>Total ($)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${requestScope.LIST_ORDER}">
                    <tr>
                        <td>${order.orderID}</td>
                        <td>${order.userID}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.total}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</c:if>
</body>
</html>
