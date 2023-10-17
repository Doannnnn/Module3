<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/18/2023
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">

        <c:if test="${product.id == 0}">
        <h3 class="text-center">CREATE PRODUCT</h3>
        <form action="/product?action=create" method="post">
            </c:if>
            <c:if test="${product.id != 0}">
            <h3 class="text-center">EDIT PRODUCT</h3>
            <form action="/product?action=edit&id=${product.id}" method="post">
                </c:if>
                <div class="mb-3">
                    <label for="name" class="form-label">NAME</label>
                    <input type="text" class="form-control" id="name" name="name" value="${product.name}">
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">PRICE</label>
                    <input type="number" class="form-control" name="price" id="price" value="${product.price}">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">DESCRIPTION</label>
                    <input type="text" class="form-control" name="description" id="description"
                           value="${product.description}">
                </div>
                <div class="mb-3">
                    <label for="category" class="form-label">CATEGORY</label>
                    <select class="form-control" name="category" id="category">
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}"
                                    <c:if test="${category.id == product.category.id}">
                                        selected
                                    </c:if>
                            >${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
