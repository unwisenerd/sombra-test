$(document).ready(function() {
	$('#login-form').submit(validateLogin);
	$('#login-form input').focus(clear);
});

function validateLogin() {
	var result = true;
	if (($('#login-form-username input').val() == '')
			|| (!($('#login-form-username input').val().match(/\w+@\w+\.\w+/)))) {
		error($('#login-form-username'));
		result = false;
	} else {
		success($('#login-form-username'));
	}
	if ($('#login-form-password input').val() == '') {
		error($('#login-form-password'));
		result = false;
	} else {
		success($('#login-form-password'));
	}
	return result;
}

function success(element) {
	element.addClass('has-success').removeClass('has-error');
	element.find('span.glyphicon').addClass('glyphicon-ok').removeClass(
			'glyphicon-remove');
}

function error(element) {
	element.addClass('has-error').removeClass('has-success');
	element.find('span.glyphicon').addClass('glyphicon-remove').removeClass(
			'glyphicon-ok');
}

function clear() {
	$(this).parent().parent().parent().removeClass('has-success').removeClass(
			'has-error');
	$(this).parent().parent().parent().find('span.glyphicon').removeClass(
			'glyphicon-ok').removeClass('glyphicon-remove');

}