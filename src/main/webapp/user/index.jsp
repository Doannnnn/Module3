<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 18/09/2023
  Time: 10:13 SA
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
    <div class="card container px-6" style="height: 100vh">
        <h3 class="text-center">MANAGEMENT USER</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>
        <div style="display: flex">
            <a href="/user?action=create" class="btn btn-success mb-2 me-1">Create</a>
            <a href="/user?action=showRestore" class="btn btn-warning mb-2" style="margin-right: 155px">Restore</a>
            <div class="d-flex">
                <input type="text" id="searchInput" class="form-control me-2 mb-2" style="width: 600px;" placeholder="Search">
                <button id="searchButton" class="btn btn-info mb-2">Search</button>
            </div>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th style="background: pink">ID</th>
                <th style="background: pink">NAME</th>
                <th style="background: pink">USERNAME</th>
                <th style="background: pink">EMAIL</th>
                <th style="background: pink">DOB</th>
                <th style="background: pink">GENDER</th>
                <th style="background: pink">ROLE</th>
                <th style="background: pink; padding-left: 36px;">ACTION</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.userName}</td>
                    <td>${user.email}</td>
                    <td>${user.dob}</td>
                    <td>${user.gender}</td>
                    <td>${user.role.name}</td>
                    <td>
                        <div class="text-right">
                            <a href="/user?action=edit&id=${user.id}" class="btn btn-primary">Edit</a>
                            <a href="/user?action=delete&id=${user.id}" class="btn btn-danger" onclick="return confirmDelete()">Delete</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

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
<script>
    function confirmDelete() {
        return confirm("Bạn có chắc chắn muốn xóa?");
    }
</script>
<script>
    const searchButton = document.getElementById('searchButton');
    searchButton.addEventListener('click', () => {
        const searchInput = document.getElementById('searchInput');
        const searchText = searchInput.value.trim();

        // Thực hiện tìm kiếm dựa trên searchText
        performSearch(searchText);
    });

    function performSearch(searchText) {
        // Logic tìm kiếm dữ liệu
        // Ví dụ: Lặp qua dữ liệu bảng và ẩn các hàng không khớp với searchText
        const rows = document.querySelectorAll('table tr');
        rows.forEach(row => {
            const tdText = row.textContent.toLowerCase();
            if (tdText.includes(searchText.toLowerCase())) {
                row.style.display = 'table-row';
            } else {
                row.style.display = 'none';
            }
        });
    }
</script>
</body>
</html>
