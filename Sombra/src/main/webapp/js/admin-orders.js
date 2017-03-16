$(document).ready(
		function() {
			$('#add-popup-show').click(addShow);
			$('#add-popup-hide').click(addHide);
			$('#add-submit').click(
					function() {
						var data = {
							quantity : $('#add-quantity').val(),
							paid : $('#add-paid').val(),
							commodity : {
								id : $('#add-commodity').val()
							},
							user : {
								id : $('#add-user').val()
							}
						};
						$.ajax({
							url : '/admin/orders/add' + '?_csrf='
									+ $('input[name=_csrf]').val(),
							method : 'POST',
							contentType : 'application/json',
							dataType : 'json',
							data : JSON.stringify(data),
							success : function(result) {
								var html = renderResultList(result);
								$('#content-table tbody').html(html);
								addButtonListeners();
							}
						});
						addHide();
					});
			$('#edit-popup-hide').click(editHide);
			$('#edit-submit').click(
					function() {
						var data = {
							quantity : $('#edit-quantity').val(),
							paid : $('#edit-paid').val(),
							commodity : {
								id : $('#edit-commodity').val()
							},
							user : {
								id : $('#edit-user').val()
							}
						};
						$.ajax({
							url : '/admin/orders/edit/' + $('#edit-id').val()
									+ '?_csrf=' + $('input[name=_csrf]').val(),
							method : 'PUT',
							contentType : 'application/json',
							dataType : 'json',
							data : JSON.stringify(data),
							success : function(result) {
								var html = renderResultList(result);
								$('#content-table tbody').html(html);
								addButtonListeners();
							}
						});
						editHide();
					});
			addButtonListeners();
		});

function deleteOrder() {
	$.ajax({
		url : '/admin/orders/delete/' + $(this).data('id') + '?_csrf='
				+ $('input[name=_csrf]').val(),
		method : 'DELETE',
		dataType : 'json',
		success : function(result) {
			var html = renderResultList(result);
			$('#content-table tbody').html(html);
			addButtonListeners();
		}
	});
}

function renderResultList(list) {
	var html = '';
	for ( var k in list) {
		html += '<tr>';
		html += '<td>' + list[k].id + '</td>';
		html += '<td>' + list[k].quantity + '</td>';
		html += '<td>' + list[k].paid + '</td>';
		html += '<td>';
		if (list[k].commodity !== null) {
			html += list[k].commodity.name;
		}
		html += '</td>';
		html += '<td>';
		if (list[k].user !== null) {
			html += list[k].user.email;
		}
		html += '</td>';
		html += '<td><button class="btn btn-default" data-id="' + list[k].id
				+ '" data-type="edit">Edit</button></td>';
		html += '<td><button class="btn btn-default" data-id="' + list[k].id
				+ '" data-type="delete">Delete</button></td>';
		html += '</tr>';
	}
	return html;
}

function addShow() {
	$('#add-popup').modal('show');
}

function addHide() {
	$('#add-popup').modal('hide');
	$('#add-quantity').val('');
}

function editShow() {
	var button = this;
	$('#edit-id').val($(button).data('id'));
	$('#edit-quantity').val($($(button).parent().siblings('td')[1]).html());
	$('#edit-paid').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[2]).html()) {
			$(this).attr('selected', true);
		}

	});
	$('#edit-commodity').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[3]).html()) {
			$(this).attr('selected', true);
		}

	});
	$('#edit-user').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[4]).html()) {
			$(this).attr('selected', true);
		}
	});
	$('#edit-popup').modal('show');
}

function editHide() {
	$('#edit-popup').modal('hide');
	$('#edit-id').removeAttr('value');
	$('#edit-quantity').val('');
	$('#edit-paid').children().each(function() {
		$(this).removeAttr('selected');
	});
	$('#edit-commodity').children().each(function() {
		$(this).removeAttr('selected');
	});
	$('#edit-user').children().each(function() {
		$(this).removeAttr('selected');
	});
}

function addButtonListeners() {
	$('button[data-type=edit]').click(editShow);
	$('button[data-type=delete]').click(deleteOrder);
}
