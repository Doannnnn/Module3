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
        <h3 class="text-center">CREATE USER</h3>
        <form action="/user?action=create" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">NAME</label>
                <input type="text" class="form-control" id="name" name="name">
            </div>
            <div class="mb-3">
                <label for="username" class="form-label">USERNAME</label>
                <input type="text" class="form-control" id="username" name="username">
            </div>
            <div class="mb-3">
                <label for="password">PASSWORD</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">EMAIL</label>
                <input type="email" class="form-control" name="email" id="email">
            </div>
            <div class="mb-3">
                <label for="dob" class="form-label">DOB</label>
                <input type="date" class="form-control" name="dob" id="dob">
            </div>
            <div class="mb-3">
                <label for="gender" class="form-label">GENDER</label>
                <select class="form-control" name="gender" id="gender">
                    <c:forEach var="gender" items="${genders}">
                        <option value="${gender}">${gender}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="role" class="form-label">ROLE</label>
                <select class="form-control" name="role" id="role">
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.id}">${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <a href="/user" class="btn btn-danger">Cancel</a>
        </form>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
