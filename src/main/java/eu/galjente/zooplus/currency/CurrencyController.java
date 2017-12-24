package eu.galjente.zooplus.currency;

import eu.galjente.zooplus.currency.dto.ConvertDto;
import eu.galjente.zooplus.currency.dto.CurrencyDto;
import eu.galjente.zooplus.currency.exception.OpenExchangeRatesAccessForbiddenException;
import eu.galjente.zooplus.currency.exception.OpenExchangeRatesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CurrencyController {

	private final OpenExchangeRatesRestConsumer ratesConsumer;
	private final ConverterHistoryService converterHistoryService;

	@Autowired
	public CurrencyController(OpenExchangeRatesRestConsumer ratesConsumer,
	                          ConverterHistoryService converterHistoryService) {
		this.ratesConsumer = ratesConsumer;
		this.converterHistoryService = converterHistoryService;
	}

	@RequestMapping(value = {"/", "/currency/convert"}, method = RequestMethod.GET)
	public String exchangeRatePage(ExchangeForm exchangeForm, ModelMap model) {
		String errorMessage = null;
		try {
			List<CurrencyDto> currencies = ratesConsumer.getCurrencies();
			model.put("currencies", currencies);
		} catch (OpenExchangeRatesAccessForbiddenException e) {
			errorMessage = "Sorry external api don't support this function, if you want using this function, please provide API key with this functionality support.";
		} catch (Exception e) {
			errorMessage = "Something went wrong, please try again later";
		}

		if (model.get("error") != null && errorMessage != null) {
			errorMessage = model.get("error") + "<br/>" + errorMessage;
		}

		List<ConverterHistory> historyList = converterHistoryService.getAllHistory();
		model.put("exchangeForm", exchangeForm);
		model.put("historyList", historyList);
		model.put("error", errorMessage);
		return "currency/converter";
	}

	@RequestMapping(value = "/currency/convert", method = RequestMethod.POST)
	public String convertCurrency(@Valid ExchangeForm exchangeForm, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return exchangeRatePage(exchangeForm, model);
		}

		ConvertDto convertedValue = null;
		String errorMessage = null;
		try {
			convertedValue = ratesConsumer.convertValue(exchangeForm.getCurrencyFrom(),
					exchangeForm.getCurrencyTo(),
					exchangeForm.getAmount());
		} catch (OpenExchangeRatesAccessForbiddenException e) {
			errorMessage = "Sorry external api don't support this function, if you want using this function, please provide API key with this functionality support.";
		} catch (OpenExchangeRatesException e) {
			errorMessage = "Cough error while convert value";
		}

		model.put("error", errorMessage);
		model.put("convertedValue", convertedValue);

		converterHistoryService.saveHistory(exchangeForm, convertedValue, errorMessage);

		return exchangeRatePage(exchangeForm, model);
	}
}
