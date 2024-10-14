<%-- 
    Document   : viewCart
    Created on : Jun 14, 2024, 8:33:56 AM
    Author     : Hieu Phi Trinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sample.users.UserDTO"%>
<%@page import="sample.clothing.ClothesDTO"%>
<%@page import="sample.shopping.Clothes"%>
<%@page import="sample.shopping.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/styleViewCart.css">
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12 text-center">
                    <h1 class="my-4">Your Cart</h1>
                </div>
            </div>

            <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
                <c:redirect url="login.jsp"></c:redirect>
            </c:if>

            <!--Define total var-->
            <c:set var="total" value="0" scope="page"/>

            <div class="row justify-content-center">
                <div class="col-12 text-center">
                    ${requestScope.CART_MESSAGE}
                </div>
            </div>

            <c:if test="${sessionScope.CART != null}">
                <!--Table of customer's orders-->
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>No</th>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Remove</th>
                                <th>Edit</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${sessionScope.CART.cart.values()}" varStatus="status">
                            <form action="MainController">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>
                                        ${product.clothesID}
                                        <input type="hidden" name="productID" value="${product.clothesID}"/>
                                    </td>
                                    <td>${product.clothesName}</td>
                                    <td>${product.price}$</td>
                                    <td>
                                        <input type="number" name="quantity" value="${product.quantity}" min="1" required class="form-control"/>
                                    </td>
                                    <td>${product.price * product.quantity}$</td>
                                    <td>
                                        <button type="submit" name="action" value="Remove" class="btn btn-danger">Remove</button>
                                    </td>
                                    <td>
                                        <button type="submit" name="action" value="Edit" class="btn btn-warning">Edit</button>
                                    </td>
                                </tr>
                                <c:set var="total" value="${total + (product.price * product.quantity)}" scope="page"/>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!--Total-->
                <div class="row justify-content-center">
                    <div class="col-12 text-center">
                        <h2>Total: ${total} $</h2>
                    </div>
                </div>
            </c:if>

            <!--Check Out buttons-->
            <div class="row">
                <div class="col-lg-6 col-md-6 text-left">
                    <form action="MainController">
                        <button type="submit" class="btn btn-success">Add More</button>
                        <input type="hidden" name="action" value="Shopping_Page"/>
                        <input type="hidden" name="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
                    </form>
                </div>
                <div class="col-lg-6 col-md-6 text-right">
                    <form action="MainController">
                        <label for="userEmail">Please enter your email to check out:</label>
                        <input type="email" name="userEmail" id="userEmail" required class="form-control d-inline-block w-auto"/>
                        <button type="submit" name="action" value="CheckOut" class="btn btn-primary">Check Out</button>
                        <input type="hidden" name="total" value="${total}"/>
                        <input type="hidden" name="userID" value="${sessionScope.LOGIN_USER.userID}"/>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>    
    </body>
</html>
