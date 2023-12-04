function check_checkbox(checkbox) {
    $(checkbox).parent().parent().find('div input[type=checkbox]').prop('checked', false);
    $(checkbox).prop('checked', true);
}