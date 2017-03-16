$(document).ready(
		function() {
			$('#add-popup-show').click(addShow);
			$('#add-popup-hide').click(addHide);
			$('#add-submit').click(
					function() {
						var data = {
							name : $('#add-name').val(),
							surname : $('#add-surname').val(),
							email : $('#add-email').val(),
							password : $('#add-password').val(),
							phone : $('#add-phone').val(),
							locked : $('#add-locked').val(),
							enabled : $('#add-enabled').val(),
							role : $('#add-role').val()
						};
						$.ajax({
							url : '/admin/users/add' + '?_csrf='
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
							name : $('#edit-name').val(),
							surname : $('#edit-surname').val(),
							email : $('#edit-email').val(),
							password : $('#edit-password').val(),
							phone : $('#edit-phone').val(),
							locked : $('#edit-locked').val(),
							enabled : $('#edit-enabled').val(),
							role : $('#edit-role').val()
						};
						$.ajax({
							url : '/admin/users/edit/' + $('#edit-id').val()
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
			$('#edit-image-submit').click(
					function() {
						if ($(":file").first().val() != '') {
							var formData = new FormData();
							formData.append('image', $(":file")[0].files[0]);
							formData.append('string', "123");
							$.ajax({
								url : '/admin/users/editImage/'
										+ $('#edit-id').val() + '?_csrf='
										+ $('input[name=_csrf]').val(),
								type : 'POST',
								data : formData,
								processData : false,
								contentType : false,
								success : function(result) {
									var html = renderResultList(result);
									$('#content-table tbody').html(html);
									addButtonListeners();
								}
							});
							editHide();
						}
					});
			$('#delete-image-submit').click(
					function() {
						$.ajax({
							url : '/admin/users/deleteImage/'
									+ $('#edit-id').val() + '?_csrf='
									+ $('input[name=_csrf]').val(),
							type : 'POST',
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

function deleteUser() {
	$.ajax({
		url : '/admin/users/delete/' + $(this).data('id') + '?_csrf='
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
		html += '<td>' + list[k].name + '</td>';
		html += '<td>' + list[k].surname + '</td>';
		html += '<td>' + list[k].email + '</td>';
		html += '<td>' + list[k].phone + '</td>';
		html += '<td>' + list[k].locked + '</td>';
		html += '<td>' + list[k].enabled + '</td>';
		html += '<td>' + list[k].role + '</td>';
		if (list[k].image !== null) {
			html += '<td><img src="' + list[k].image + '"></td>';
		} else {
			html += '<td></td>';
		}
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
	$('#add-name').val('');
	$('#add-surname').val('');
	$('#add-email').val('');
	$('#add-password').val('');
	$('#add-phone').val('');
}

function editShow() {
	var button = this;
	$('#edit-id').val($(button).data('id'));
	$('#edit-name').val($($(button).parent().siblings('td')[1]).html());
	$('#edit-surname').val($($(button).parent().siblings('td')[2]).html());
	$('#edit-email').val($($(button).parent().siblings('td')[3]).html());
	$('#edit-phone').val($($(button).parent().siblings('td')[4]).html());
	$('#edit-locked').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[5]).html()) {
			$(this).attr('selected', true);
		}
	});
	$('#edit-enabled').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[6]).html()) {
			$(this).attr('selected', true);
		}
	});
	$('#edit-role').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[7]).html()) {
			$(this).attr('selected', true);
		}
	});
	$('#edit-popup').modal('show');
}

function editHide() {
	$('#edit-popup').modal('hide');
	$('#edit-id').removeAttr('value');
	$('#edit-name').val('');
	$('#edit-surname').val('');
	$('#edit-email').val('');
	$('#edit-password').val('');
	$('#edit-phone').val('');
	$('#edit-locked').children().each(function() {
		$(this).removeAttr('selected');
	});
	$('#edit-enabled').children().each(function() {
		$(this).removeAttr('selected');
	});
	$('#edit-role').children().each(function() {
		$(this).removeAttr('selected');
	});
}

function addButtonListeners() {
	$('button[data-type=edit]').click(editShow);
	$('button[data-type=delete]').click(deleteUser);
}