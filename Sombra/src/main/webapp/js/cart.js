$(document).ready(function() {
	addButtonListeners();
});

function resultHide() {
	$('#result-text').html('');
	$('#result-popup').modal('hide');
}

function makeOrder() {
	$.ajax({
		url : '/cart/order/' + $(this).data('id') + '?_csrf='
				+ $('input[name=_csrf]').val(),
		method : 'POST',
		dataType : 'json',
		success : function(result) {
			var html = renderResultList(result);
			$('#content-table tbody').html(html);
			addButtonListeners();
		}
	});
}

function deleteFromCart() {
	$.ajax({
		url : '/cart/delete/' + $(this).data('id') + '?_csrf='
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

function renderResultList(map) {
	var html = '';
	for ( var k in map) {
		var key = JSON.parse(k);
		html += '<tr>';
		html += '<td>' + key.id + '</td>';
		html += '<td>' + key.name + '</td>';
		html += '<td>' + key.brand + '</td>';
		html += '<td>' + map[k] + '</td>';
		html += '<td>' + key.price * map[k] + '</td>';
		html += '</td>';
		html += '<td><a href="/commodities/view' + key.id
				+ '">View</button></td>';
		html += '<td><button class="btn btn-default" data-id="' + key.id
				+ '" data-type="order">Order</button></td>';
		html += '<td><button class="btn btn-default" data-id="' + key.id
				+ '" data-type="delete">Delete</button></td>';
		html += '</tr>';
	}
	return html;
}

function addButtonListeners() {
	$('button[data-type=order]').click(makeOrder);
	$('button[data-type=delete]').click(deleteFromCart);
}