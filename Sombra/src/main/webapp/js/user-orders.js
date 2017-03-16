$(document).ready(function() {
	$('button[data-type=pay]').click(cardShow);
	$('#card-popup-hide').click(cardHide);
	$('#card-form').submit(validateCard);
	$('#card-form-number input').focus(clear);
});

function cardShow() {
	$('#id').val($(this).data('id'));
	$('#card-popup').modal('show');
}

function cardHide() {
	$('#id').val('');
	$('#card-popup').modal('hide');
}

function validateCard() {
	var result = true;
	if (($('#card-form-number input').val() == '')
			|| (!$('#card-form-number input').val().match(/[0-9]{16}/))) {
		error($('#card-form-number'));
		result = false;
	} else {
		success($('#card-form-number'));
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