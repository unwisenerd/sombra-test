$(document).ready(
		function() {
			$('#add-popup-show').click(addShow);
			$('#add-popup-hide').click(addHide);
			$('#add-submit').click(
					function() {
						var data = {
							name : $('#add-name').val()
						};
						$.ajax({
							url : '/admin/categories/add' + '?_csrf='
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
							name : $('#edit-name').val()
						};
						$.ajax({
							url : '/admin/categories/edit/'
									+ $('#edit-id').val() + '?_csrf='
									+ $('input[name=_csrf]').val(),
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

function deleteCategory(id) {
	$.ajax({
		url : '/admin/categories/delete/' + $(this).data('id') + '?_csrf='
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
}

function editShow() {
	$('#edit-id').val($(this).data('id'));
	$('#edit-name').val($($(this).parent().siblings('td')[1]).html());
	$('#edit-popup').modal('show');
}

function editHide() {
	$('#edit-popup').modal('hide');
	$('#edit-id').removeAttr('value');
	$('#edit-name').val('');
}

function addButtonListeners() {
	$('button[data-type=edit]').click(editShow);
	$('button[data-type=delete]').click(deleteCategory);
}
