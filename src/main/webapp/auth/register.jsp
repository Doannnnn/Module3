<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/25/2023
  Time: 10:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="text-center">Đăng ký</h1>
    <c:if test="${message != null}">
        <h6 class="d-none" id="message">${message}</h6>
    </c:if>
    <form action="auth?action=register" method="POST">
        <div class="mb-3">
            <label for="name">Họ và tên</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="mb-3">
            <label for="username">Tên tài khoản</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="password">Mật khẩu</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="mb-3">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="dob">Dob</label>
            <input type="date" class="form-control" id="dob" name="dob" required>
        </div>
        <div class="mb-3">
            <label for="gender">Giới tính</label>
            <select class="form-control" id="gender" name="gender" required>
                <c:forEach var="gender" items="${genders}">
                    <option value="${gender}">${gender}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Đăng ký</button>
        <a href="/auth" class="btn btn-danger">Cancel</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script>
    const message = document.getElementById('message');
    if (message !== null && message.innerHTML) {
        toastr.success(message.innerHTML);
    }
</script>
</body>
</html>