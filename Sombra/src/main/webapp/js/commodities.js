$(document).ready(validateSubCategory);
$('#category').change(validateSubCategory);
$('#search').submit(validateSearch);

function validateSubCategory() {
	$('#subcategory option').each(
			function() {
				if (($(this).data('category') == $('#category option:selected')
						.val())
						|| ($(this).data('category') == undefined)) {
					$(this).removeAttr('style');
				} else {
					$(this).attr('style', 'display:none');
					$(this).prop("selected", false);
				}
			});
}

function validateSearch() {
	$('#search input').each(function() {
		if ($(this).val() == '') {
			$(this).attr('disabled', true);
		}
	});
	$('#search select').each(function() {
		if ($(this).val() == '') {
			$(this).attr('disabled', true);
		}
	});
}