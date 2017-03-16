$(document).ready(
		function() {
			$('#add-popup-show').click(addShow);
			$('#add-popup-hide').click(addHide);
			$('#add-submit').click(
					function() {
						var data = {
							name : $('#add-name').val(),
							category : {
								id : $('#add-category').val()
							}
						};
						$.ajax({
							url : '/admin/subcategories/add' + '?_csrf='
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
							category : {
								id : $('#edit-category').val()
							}
						};
						$.ajax({
							url : '/admin/subcategories/edit/'
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

function deleteSubCategory() {
	$.ajax({
		url : '/admin/subcategories/delete/' + $(this).data('id') + '?_csrf='
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
		html += '<td>';
		if (list[k].category !== null) {
			html += list[k].category.name;
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
	$('#add-name').val('');
}

function editShow() {
	var button = this;
	$('#edit-id').val($(button).data('id'));
	$('#edit-name').val($($(button).parent().siblings('td')[1]).html());
	$('#edit-category').children().each(function() {
		if ($(this).html() == $($(button).parent().siblings('td')[2]).html()) {
			$(this).attr('selected', true);
		}
	});
	$('#edit-popup').modal('show');
}

function editHide() {
	$('#edit-popup').modal('hide');
	$('#edit-id').removeAttr('value');
	$('#edit-name').val('');
	$('#edit-category').children().each(function() {
		$(this).removeAttr('selected');
	});
}

function addButtonListeners() {
	$('button[data-type=edit]').click(editShow);
	$('button[data-type=delete]').click(deleteSubCategory);
}
