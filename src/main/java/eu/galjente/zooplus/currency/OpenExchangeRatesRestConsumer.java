package eu.galjente.zooplus.currency;

import eu.galjente.zooplus.currency.dto.ConvertDto;
import eu.galjente.zooplus.currency.dto.CurrencyDto;
import eu.galjente.zooplus.currency.dto.ExchangeRatesDto;
import eu.galjente.zooplus.currency.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class OpenExchangeRatesRestConsumer {

	private final String apiUrl;
	private final String apiToken;
	private final RestTemplate restTemplate;

	@Autowired
	public OpenExchangeRatesRestConsumer(@Value("${currency.api.rest.baseUrl}") String apiUrl,
										 @Value("${currency.api.rest.token}") String apiToken,
										 RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.apiToken = apiToken;
		this.restTemplate = restTemplate;
	}

	@Cacheable("currency")
	public List<CurrencyDto> getCurrencies() throws OpenExchangeRatesException {
		final Map<String, Object> parameters = getDefaultParameters();
		try {
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(apiUrl + "/currencies.json?app_id={API_KEY}", Map.class, parameters);
			return convertCurrencyMapToDto(responseEntity.getBody());
		} catch (HttpClientErrorException e) {
			processErrorResponse(e);
		}

		return new ArrayList<>();
	}

	@Cacheable(value = "exchangeRates", key="#baseCurrency")
	public ExchangeRatesDto getExchangeRate(String baseCurrency) throws OpenExchangeRatesException {
		final Map<String, Object> parameters = getDefaultParameters();
		parameters.put("baseCurrency", baseCurrency);

		try {
			ResponseEntity<ExchangeRatesDto> responseEntity = restTemplate.getForEntity(apiUrl + "/latest.json?app_id={API_KEY}&base={baseCurrency}", ExchangeRatesDto.class, parameters);
			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			processErrorResponse(e);
		}

		return new ExchangeRatesDto();
	}

	@Cacheable(value="convert")
	public ConvertDto convertValue(String currencyFrom, String currencyTo, BigDecimal amount) throws OpenExchangeRatesException {
		final Map<String, Object> parameters = getDefaultParameters();
		parameters.put("currencyFrom", currencyFrom);
		parameters.put("currencyTo", currencyTo);
		parameters.put("amount", amount);

		try {
			ResponseEntity<ConvertDto> responseEntity = restTemplate.getForEntity(apiUrl + "/convert/{amount}/{currencyFrom}/{currencyTo}?app_id={API_KEY}", ConvertDto.class, parameters);
			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			processErrorResponse(e);
		}

		return new ConvertDto();
	}

	private Map<String, Object> getDefaultParameters() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("API_KEY", apiToken);

		return parameters;
	}

	private List<CurrencyDto> convertCurrencyMapToDto(Map<String, String> currencies) {
		return currencies
				.entrySet()
				.stream()
				.map((entry) -> new CurrencyDto(entry.getKey(), entry.getValue()))
				.collect(toList());
	}

	private void processErrorResponse(HttpClientErrorException e) throws OpenExchangeRatesException {
		switch (e.getStatusCode()) {
			case BAD_REQUEST:
				throw new OpenExchangeRatesInvalidCurrencyException();
			case UNAUTHORIZED:
				throw new OpenExchangeRatesUnauthorizedException();
			case TOO_MANY_REQUESTS:
				throw new OpenExchangeRatesNotAllowedException();
			case NOT_FOUND:
				throw new OpenExchangeRatesResourceNotFoundException();
			case FORBIDDEN:
				throw new OpenExchangeRatesAccessForbiddenException();

			default:
				throw new OpenExchangeRatesException(e.getMessage());
		}
	}
}
