$(document).ready(function() {
	$('#register-form').submit(validateRegister);
	$('#register-form input').focus(clear);
});

function validateRegister() {
	var result = true;
	if (($('#register-form-name input').val() == '')
			|| (($('#register-form-name input').val()
					.match(/[^А-Яа-яA-Za-zІіЇїЄєЁё]+/)))) {
		error($('#register-form-name'));
		result = false;
	} else {
		success($('#register-form-name'));
	}
	if (($('#register-form-surname input').val() == '')
			|| (($('#register-form-surname input').val()
					.match(/[^А-Яа-яA-Za-zІіЇїЄєЁё]+/)))) {
		error($('#register-form-surname'));
		result = false;
	} else {
		success($('#register-form-surname'));
	}
	if (($('#register-form-email input').val() == '')
			|| (!($('#register-form-email input').val().match(/\w+@\w+\.\w+/)))) {
		error($('#register-form-email'));
		result = false;
	} else {
		success($('#register-form-email'));
	}
	if ($('#register-form-password input').val() == '') {
		error($('#register-form-password'));
		result = false;
	} else {
		success($('#register-form-password'));
	}
	if (($('#register-form-phone input').val() == '')
			|| (!($('#register-form-phone input').val().match(/\+?\d+/)))) {
		error($('#register-form-phone'));
		result = false;
	} else {
		success($('#register-form-phone'));
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