$(document).ready(
		function() {
			$('#add-to-cart').click(
					function() {
						$('#add-to-cart').button('loading');
						$.ajax({
							url : window.location.pathname + "/addToCart"
									+ '?_csrf=' + $('input[name=_csrf]').val(),
							data : {
								quantity : $('#quantity').val()
							},
							method : 'POST',
							success : function(result) {
								$('#add-to-cart').button('reset')
								$('#result-text').html(result);
								$('#result-popup').modal('show');
							}
						});
					})
			$('#result-popup-hide').click(resultHide);
			$('#result-confirm').click(resultHide);
		});

function resultHide() {
	$('#result-text').html('');
	$('#result-popup').modal('hide');
}