function setDone(checkbox,user_id) {
    let id = checkbox.id;
    $.ajax({
        type: 'GET',
        data: {'id': id},
        url: 'http://localhost:8080/job4j_todo/done',
    }).done(function () {
        getItems(user_id);
    }).fail(function (err) {
        console.log(err);
    });
}