<%-- 
    Document   : shopping
    Created on : Jun 14, 2024, 8:26:37 AM
    Author     : Hieu Phi Trinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sample.users.UserDTO"%>
<%@page import="sample.clothing.ClothesDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/styleShopping.css">
    </head>
    <body>
        <c:if test="${sessionScope.LOGIN_USER == null}">
            <c:redirect url="register.jsp"/>
        </c:if>
        
        <c:if test="${sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <header class="bg-light p-3 mb-3 shadow-sm">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 d-flex align-items-center">
                        <h1 class="mb-0">Welcome to L&P Store!</h1>
                    </div>
                    <div class="col-md-6 d-flex justify-content-end align-items-center">
                        <form action="MainController" class="me-2">
                            <input type="text" name="searchProduct" value="${param.searchProduct}" placeholder="Search product" class="form-control d-inline w-auto me-2"/>
                            <input type="submit" name="action" value="SearchProduct" class="btn btn-primary"/>
                            <input type="hidden" name="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
                        </form>
                        <form action="MainController" class="me-2">
                            <input type="submit" name="action" value="View" class="btn btn-info"/>
                        </form>
                        <form action="MainController" method="POST">
                            <input type="submit" name="action" value="Logout" class="btn btn-danger"/>
                        </form>
                    </div>
                </div>
            </div>
        </header>

        <!-- Paging -->
        <div class="container mb-3">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <!-- Previous button -->
                    <c:if test="${requestScope.CURRENT_PAGE > 1}">
                        <li class="page-item">
                            <a class="page-link" href="ShoppingController?page=${requestScope.CURRENT_PAGE - 1}">Previous</a>
                        </li>
                    </c:if>

                    <!-- Page numbers -->
                    <c:forEach var="index" begin="1" end="${requestScope.TOTAL_PAGES}">
                        <li class="page-item <c:if test='${index == requestScope.CURRENT_PAGE}'>active</c:if>'">
                            <a class="page-link" href="SearchProductController?searchProduct=${param.searchProduct}&roleID=${sessionScope.LOGIN_USER.roleID}&page=${index}">
                                ${index}
                            </a>
                        </li>
                    </c:forEach>

                    <!-- Next button -->
                    <c:if test="${requestScope.CURRENT_PAGE < requestScope.TOTAL_PAGES}">
                        <li class="page-item">
                            <a class="page-link" href="ShoppingController?page=${requestScope.CURRENT_PAGE + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>

        <!-- Message -->
        <div class="container mb-3">
            <div class="alert alert-info text-center" role="alert">
                ${requestScope.MESSAGE}
            </div>
        </div>

        <!-- Items table -->
        <div class="container">
            <div class="row">
                <c:if test="${not empty requestScope.LIST_PRODUCT}">
                    <c:forEach var="product" items="${requestScope.LIST_PRODUCT}">
                        <c:if test="${product.status && product.quantity > 0}">
                            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                                <div class="card h-100">
                                    <img src="${product.image}" alt="Product picture" class="card-img-top img-fluid">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">${product.productName}</h5>
                                        <p class="card-text">Price: ${product.price} $</p>
                                        <form action="MainController" class="d-flex justify-content-center">
                                            <select name="cmbQuantity" class="form-select me-2" style="width: auto;">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                            </select>
                                            <input type="submit" name="action" value="Add" class="btn btn-success"/>
                                            <input type="hidden" name="cmbProduct" value="${product.productID}_${product.productName}_${product.price}"/>
                                            <input type="hidden" name="searchProduct" value="${param.searchProduct}"/>
                                            <input type="hidden" name="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
                                            <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}"/>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
