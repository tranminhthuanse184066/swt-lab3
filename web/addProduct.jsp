<%-- 
    Document   : addProduct
    Created on : Jun 20, 2024, 2:03:24 PM
    Author     : Hieu Phi Trinh
--%>

<%@page import="sample.clothing.ClothesError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add new Product Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Add new Product:</h1>
        <form action="MainController" method="POST">
            Product ID: <input type="text" name="productID" required=""/> ${requestScope.CLOTHES_ERROR.productID}
            <br/>Product Name: <input type="text" name="productName" required=""/> ${requestScope.CLOTHES_ERROR.productName}
            <br/>Price: <input type="number" min="0" name="price" required=""/> ${requestScope.CLOTHES_ERROR.price}
            <br/>Quantity: <input type="number" min="0" name="quantity" required=""/> ${requestScope.CLOTHES_ERROR.quantity}
            <br/>Image: <input type="text" name="image"/> ${requestScope.CLOTHES_ERROR.image}
            <br/><input type="submit" name="action" value="AddProduct"/>
            <input type="reset" value="Reset"/> ${requestScope.CLOTHES_ERROR.errorMessage}
        </form>
    </body>
</html>