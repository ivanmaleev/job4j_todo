function getItems(user_id) {
    var viewdone = document.getElementById('viewdone').checked;
    $.ajax({
        type: 'GET',
        data: {
            'viewdone': viewdone,
            'userid': user_id
        },
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
            th.setAttribute('scope', 'row');
            let text = document.createTextNode(item.id);
            th.append(text);
            tr.append(th);

            var td = document.createElement('td');
            text = document.createTextNode(item.description);
            td.append(text);
            tr.appendChild(td);

            td = document.createElement('td');
            var cattext = '';
            item.categories.forEach(function (cat) {
                cattext = (cattext == '' ? cat.name : cattext + ',' + cat.name);
            })
            text = document.createTextNode(cattext);
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
            checkbox.setAttribute('onchange', 'setDone(this,' + user_id +')');
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