$(document).ready(function() {
	$('#name-form').submit(validateName);
	$('#surname-form').submit(validateSurname);
	$('#email-form').submit(validateEmail);
	$('#phone-form').submit(validatePhone);
	$('#password-form').submit(validatePassword);
	$('#image-form').submit(validateImage);
	$('form input').focus(clear);
});

function validateName() {
	var result = true;
	if (($('#name-form input').val() == '')
			|| (($('#name-form input').val().match(/[^А-Яа-яA-Za-z]+/)))) {
		error($('#name-form'));
		result = false;
	} else {
		success($('#name-form'));
	}
	return result;
}

function validateSurname() {
	var result = true;
	if (($('#surname-form input').val() == '')
			|| (($('#surname-form input').val().match(/[^А-Яа-яA-Za-z]+/)))) {
		error($('#surname-form'));
		result = false;
	} else {
		success($('#surname-form'));
	}
	return result;
}

function validateEmail() {
	var result = true;
	if (($('#email-form input').val() == '')
			|| (!($('#email-form input').val().match(/\w+@\w+\.\w+/)))) {
		error($('#email-form'));
		result = false;
	} else {
		success($('#email-form'));
	}
	return result;
}

function validatePhone() {
	var result = true;
	if (($('#phone-form input').val() == '')
			|| (!($('#phone-form input').val().match(/\+?\d+/)))) {
		error($('#phone-form'));
		result = false;
	} else {
		success($('#phone-form'));
	}
	return result;
}

function validatePassword() {
	var result = true;
	if ($('#password-form input').val() == '') {
		error($('#password-form'));
		result = false;
	} else {
		success($('#password-form'));
	}
	return result;
}

function validateImage() {
	var result = true;
	if ($('#image-form input').first().val() == '') {
		result = false;
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