function addItem(user_id) {
    if (validate()) {
        var description = document.getElementById('description');
        let descriptiontext = description.value;
        var cidsel = document.getElementById('cIds');
        let cids = new Array();
        var count = 0;
        for (i = 0; i < cidsel.options.length; i++) {
            if (cidsel.options[i].selected) {
                cids[count] = cidsel.options[i].value;
                count++;
            }
        }
        description.value = '';
        $.ajax({
            type: 'POST',
            data: {
                'description': descriptiontext,
                'userid': user_id,
                'cIds': cids
            },
            url: 'http://localhost:8080/job4j_todo/todo',
        }).done(function () {
            getItems(user_id);
        }).fail(function (err) {
            console.log(err);
        });
    }
}