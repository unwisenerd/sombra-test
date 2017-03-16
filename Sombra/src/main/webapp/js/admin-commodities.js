$(document).ready(
		function() {
			$('#add-popup-show').click(addShow);
			$('#add-popup-hide').click(addHide);
			$('#add-submit').click(
					function() {
						var data = {
							name : $('#add-name').val(),
							brand : $('#add-brand').val(),
							country : $('#add-country').val(),
							consist : $('#add-consist').val(),
							price : $('#add-price').val(),
							available : $('#add-available').val(),
							subCategory : {
								id : $('#add-subcategory').val(),
							}
						};
						$.ajax({
							url : '/admin/commodities/add' + '?_csrf='
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
							brand : $('#edit-brand').val(),
							country : $('#edit-country').val(),
							consist : $('#edit-consist').val(),
							price : $('#edit-price').val(),
							available : $('#edit-available').val(),
							subCategory : {
								id : $('#edit-subcategory').val()
							}
						};
						$.ajax({
							url : '/admin/commodities/edit/'
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
			$('#edit-image-submit').click(
					function() {
						if ($(':file').first().val() != '') {
							var formData = new FormData();
							formData.append('image', $(':file')[0].files[0]);
							$.ajax({
								url : '/admin/commodities/editImage/'
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
							url : '/admin/commodities/deleteImage/'
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

function deleteCommodity() {
	$.ajax({
		url : '/admin/commodities/delete/' + $(this).data('id') + '?_csrf='
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
		html += '<td>' + list[k].brand + '</td>';
		html += '<td>' + list[k].country + '</td>';
		html += '<td>' + list[k].consist + '</td>';
		html += '<td>' + list[k].price + '</td>';
		html += '<td>' + list[k].available + '</td>';
		html += '<td>';
		if (list[k].subCategory !== null) {
			html += list[k].subCategory.name;
		}
		html += '</td>';
		html += '<td>';
		if (list[k].image !== null) {
			html += '<img src="' + list[k].image + '">';
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
	$('#add-country').val('');
	$('#add-consist').val('');
	$('#add-price').val('');
	$('#add-available').val('');
}

function editShow() {
	var button = this;
	$('#edit-id').val($(button).data('id'));
	$('#edit-name').val($($(button).parent().siblings('td')[1]).html());
	$('#edit-brand').val($($(button).parent().siblings('td')[2]).html());
	$('#edit-country').val($($(button).parent().siblings('td')[3]).html());
	$('#edit-consist').val($($(button).parent().siblings('td')[4]).html());
	$('#edit-price').val($($(button).parent().siblings('td')[5]).html());
	$('#edit-available').val($($(button).parent().siblings('td')[6]).html());
	$('#edit-subcategory').children().each(function() {
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
	$('#edit-country').val('');
	$('#edit-consist').val('');
	$('#edit-price').val('');
	$('#edit-available').val('');
	$('#edit-subcategory').children().each(function() {
		$(this).removeAttr('selected');
	});
}

function addButtonListeners() {
	$('button[data-type=edit]').click(editShow);
	$('button[data-type=delete]').click(deleteCommodity);
}