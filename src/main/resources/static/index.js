function createTableRow(text){
    return '<tr>' + text + '</tr>';
}

function createTableCell(text) {
    return '<td>' + text + '</td>';
}

function updateButton(key){
    var update = '<button '
        + 'class="btn btn-default" '
        + 'onclick="editKey(\'{key}\')" '
        + '>Edit</button>';

    return update.replace(/{key}/g, key);

}

function deleteButton(key){
    var deleteB = '<button '
        + 'class="btn btn-default" '
        + 'data-key="{key}" '
        + 'onclick="deleteKey(\'{key}\')" '
        + '>Delete</button>';
    return deleteB.replace(/{key}/g, key);
}

function singleRow(key,value){
    return $(
        createTableRow(
            createTableCell(key) +
            createTableCell(value) +
            createTableCell(updateButton(key)) +
            createTableCell(deleteButton(key))
        )
    );
}

function reloadTable(){
    $.get('/values',function (data) {
        var attr,
            mainTable = $('#mainTable tbody');
        mainTable.empty();
        for (attr in data){
            if (data.hasOwnProperty(attr)){
                mainTable.append(singleRow(attr,data[attr]));
            }
        }
    });
}

function editKey(key){
    var edit = '#mainTable tbody td:first-child:contains("{key}")',
        selector = edit.replace(/{key}/, key),
        cells = $(selector).parent().children(),
        key = cells[0].textContent,
        value = cells[1].textContent,
        keyInput = $('#keyInput'),
        valueInput = $('#valueInput');

    keyInput.val(key);
    valueInput.val(value);
    valueInput.select();
}


function deleteKey(key) {
    $.post('/delete', {key: key}, function() {
        reloadTable();
        $('#keyInput').focus();
    });
}

$(document).ready(function() {
    var keyInput = $('#keyInput'),
        valueInput = $('#valueInput');

    reloadTable();
    $('#addForm').on('submit', function(event) {
        var data = {
            key: keyInput.val(),
            value: valueInput.val()
        };

        $.post('/add', data, function() {
            reloadTable();
            keyInput.val('');
            valueInput.val('');
            keyInput.focus();
        });
        event.preventDefault();
    });

    keyInput.focus();
});
