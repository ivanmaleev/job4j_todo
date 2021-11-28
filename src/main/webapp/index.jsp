<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Список задач</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
        function validate() {
            if ($('#description').val() == '') {
                alert("Заполните описание");
                return false;
            }
            return true;
        }
    </script>
    <script src="jsscripts/additem.js"></script>
    <script src="jsscripts/setdone.js"></script>
    <script>
        $(document).ready(function () {
            let user_id = <c:out value="${user.id}"/>;
            getItems(user_id);
        });
    </script>
    <script src="jsscripts/getitems.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"><c:out value="${user.name}"/> |
                        Выйти</a>
                </li>
            </c:if>
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="form-group">
        <label for="viewdone">Показывать все</label>
        <p><input type="checkbox" name="viewdone" id="viewdone" onchange="getItems(<c:out value="${user.id}"/>)"></p>
        <label for="description">Описание</label>
        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
    </div>
    <button type="button" class="btn btn-default" onclick="addItem(<c:out value="${user.id}"/>)">Добавить задачу
    </button>
    <div class="row pt-3">
        <table class="table" id="itemstable">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Описание</th>
                <th scope="col">Создано</th>
                <th scope="col">Выполнено</th>
            </tr>
            </thead>
            <tbody id="itemsb">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>