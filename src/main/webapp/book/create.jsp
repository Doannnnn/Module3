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

        <c:if test="${book.id == 0}">
        <h3 class="text-center">CREATE BOOK</h3>
        <form action="/book?action=create" method="post">
            </c:if>
            <c:if test="${book.id != 0}">
            <h3 class="text-center">EDIT BOOK</h3>
            <form action="/book?action=edit&id=${book.id}" method="post">
                </c:if>
                <div class="mb-3">
                    <label for="title" class="form-label">TITLE</label>
                    <input type="text" class="form-control" id="title" name="title" value="${book.title}">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">DESCRIPTION</label>
                    <input type="text" class="form-control" name="description" id="description" value="${book.description}">
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">PRICE</label>
                    <input type="number" class="form-control" name="price" id="price" value="${book.price}">
                </div>
                <div class="mb-3">
                    <label for="publishDate" class="form-label">PUBLISHDATE</label>
                    <input type="date" class="form-control" name="publishDate" id="publishDate" value="${book.publishDate}">
                </div>
                <div class="mb-3">
                    <label for="author" class="form-label">AUTHOR</label>
                    <select class="form-control" name="author" id="author">
                        <c:forEach var="author" items="${authors}">
                            <option value="${author.id}"
                                    <c:if test="${author.id == book.author.id}">
                                        selected
                                    </c:if>
                            >${author.name}</option>
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
