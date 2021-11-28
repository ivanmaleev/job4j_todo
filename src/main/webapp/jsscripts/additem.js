function addItem(user_id) {
    if (validate()) {
        var description = document.getElementById('description');
        let descriptiontext = description.value;
        description.value = '';
        $.ajax({
            type: 'POST',
            data: {
                'description': descriptiontext,
                'userid': user_id
            },
            url: 'http://localhost:8080/job4j_todo/todo',
        }).done(function () {
            getItems(user_id);
        }).fail(function (err) {
            console.log(err);
        });
    }
}