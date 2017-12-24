$(function () {
	var jCurrencyFrom = $('#currencyFrom');
	var jCurrencyTo = $('#currencyTo');


	jCurrencyFrom.change(function() {
		var currency = jCurrencyFrom.val();
		jCurrencyTo.find(':disabled').attr("disabled", false);
		jCurrencyTo.find('option[value="' + currency + '"]').attr("disabled", true);
	});

	jCurrencyTo.change(function() {
		var currency = jCurrencyTo.val();
		jCurrencyFrom.find(':disabled').attr("disabled", false);
		jCurrencyFrom.find('option[value="' + currency + '"]').attr("disabled", true);
	});
});