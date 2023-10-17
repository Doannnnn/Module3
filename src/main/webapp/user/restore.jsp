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
    <form action="/user?action=restoreAll" method="post">
        <div class="card container px-6" style="height: 100vh">
            <h3 class="text-center">RESTORE USER</h3>
            <c:if test="${message != null}">
                <h6 class="d-none" id="message">${message}</h6>
            </c:if>
            <div>
                <a href="/user" class="btn btn-primary mb-2">Back</a>
                <button type="submit" class="btn btn-success mb-2" id="restoreAll">Restore ALL</button>
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
                    <th style="background: pink; padding-left: 15px;">ACTION</th>
                    <th style="background: pink" id="selectAllCheckbox">SELECT</th>
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
                                <a href="/user?action=restore&id=${user.id}" class="btn btn-danger" onclick="return confirmRestore()">Restore</a>
                            </div>
                        </td>
                        <td>
                            <input type="checkbox" class="checkbox" name="selectedUsers" value="${user.id}" style="transform: scale(1.4); margin-left: 21px;">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
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
<script>
    function confirmRestore() {
        return confirm("Bạn có chắc chắn muốn Restore?");
    }

    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.getElementsByClassName('checkbox');
    let checked = true;
    selectAllCheckbox.addEventListener('click', function () {
        Array.from(checkboxes).forEach(function (checkbox) {
            checkbox.checked = checked;

        });
        checked = !checked;
    });


        const restoreAll = document.getElementById('restoreAll');
            restoreAll.addEventListener('click', () => {
            var selectedUserIds = [];
                const userCheckboxes = document.querySelectorAll('.checkbox');
                userCheckboxes.forEach(checkbox => {
                if (checkbox.checked) {
                    selectedUserIds.push(checkbox.value);
                }
            });
            if (selectedUserIds.length === 0) {
                alert('Vui lòng chọn ít nhất một cuốn sách để khôi phục.');
            } else {
                if (confirm('Bạn có chắc chắn muốn khôi phục sách đã chọn không?')) {
                    $.ajax({
                        url: '/user?action=restoreAll&id=' + selectedUserIds.join(','),
                        method: 'post'
                    });
                }
            }
        });
</script>
</body>
</html>
