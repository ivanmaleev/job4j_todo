<%@ page contentType="text/html; charset=UTF-8" %>
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
    <script>
        function addItem() {
            if (validate()) {
                var description = document.getElementById('description');
                let descriptiontext = description.value;
                description.value = '';
                $.ajax({
                    type: 'POST',
                    data: {'description': descriptiontext},
                    url: 'http://localhost:8080/job4j_todo/todo',
                }).done(function () {
                    getItems();
                }).fail(function (err) {
                    console.log(err);
                });
            }
        }
    </script>
    <script>
        function setDone(checkbox) {
            let id = checkbox.id;
            $.ajax({
                type: 'POST',
                data: {'id': id},
                url: 'http://localhost:8080/job4j_todo/done',
            }).done(function () {
                getItems();
            }).fail(function (err) {
                console.log(err);
            });
        }
    </script>
    <script>
        $(document).ready(function () {
            getItems();
        });
    </script>
    <script>
        function getItems() {
            var viewdone = document.getElementById('viewdone').checked;
            $.ajax({
                type: 'GET',
                data: {'viewdone': viewdone},
                url: 'http://localhost:8080/job4j_todo/todo',
                dataType: 'json'
            }).done(function (items) {
                let itemsb = document.getElementById('itemsb');
                var newtbody = document.createElement('tbody');
                newtbody.setAttribute('id', 'itemsb');
                itemsb.parentNode.replaceChild(newtbody, itemsb);
                for (var item of items) {
                    var tr = document.createElement('tr');
                    var th = document.createElement('th');
                    var td = document.createElement('td');
                    th.setAttribute('scope', 'row');
                    let text = document.createTextNode(item.id);
                    th.append(text);
                    tr.append(th);
                    text = document.createTextNode(item.description);
                    td.append(text);
                    tr.appendChild(td);
                    td = document.createElement('td');
                    text = document.createTextNode(item.created);
                    td.append(text);
                    tr.appendChild(td);

                    td = document.createElement('td');
                    var checkbox = document.createElement('input');
                    checkbox.setAttribute('type', 'checkbox');
                    checkbox.setAttribute('id', item.id);
                    checkbox.setAttribute('name', item.id);
                    checkbox.setAttribute('onchange', 'setDone(this)');
                    if (item.done) {
                        checkbox.setAttribute('disabled', true);
                    }
                    td.append(checkbox);
                    tr.appendChild(td);
                    $('#itemsb').append($(tr));
                }
            }).fail(function (err) {
                console.log(err);
            });
        }
    </script>
</head>
<body>
<div class="container">
    <div class="form-group">
        <label for="viewdone">Показывать все</label>
        <p><input type="checkbox" name="viewdone" id="viewdone" onchange="getItems()"></p>
        <label for="description">Описание</label>
        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
    </div>
    <button type="button" class="btn btn-default" onclick="addItem()">Добавить задачу</button>
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