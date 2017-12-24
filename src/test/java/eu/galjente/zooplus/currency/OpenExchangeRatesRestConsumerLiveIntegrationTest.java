package eu.galjente.zooplus.currency;

import eu.galjente.zooplus.ZooPlusApplication;
import eu.galjente.zooplus.currency.dto.ConvertDto;
import eu.galjente.zooplus.currency.dto.ConvertRequestDto;
import eu.galjente.zooplus.currency.dto.CurrencyDto;
import eu.galjente.zooplus.currency.dto.ExchangeRatesDto;
import eu.galjente.zooplus.currency.exception.OpenExchangeRatesException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ZooPlusApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-exrate.properties")
public class OpenExchangeRatesRestConsumerLiveIntegrationTest {

	private final static String BASE_CURRENCY = "USD";
	private final static String CONVERT_TO_CURRENCY = "EUR";
	private final static BigDecimal CONVERT_AMOUNT = new BigDecimal("10.2");

	@Autowired
	private OpenExchangeRatesRestConsumer consumer;

	@Test
	public void testValidCurrencyList() throws IOException, OpenExchangeRatesException {
		// when
		List<CurrencyDto> currencies = consumer.getCurrencies();

		// then
		assertThat(currencies)
				.contains(
						new CurrencyDto("EUR", "Euro"),
						new CurrencyDto("USD", "United States Dollar"),
						new CurrencyDto("NZD", "New Zealand Dollar"),
						new CurrencyDto("GBP", "British Pound Sterling"),
						new CurrencyDto("AUD", "Australian Dollar"),
						new CurrencyDto("JPY", "Japanese Yen"),
						new CurrencyDto("HUF", "Hungarian Forint")
				);
	}

	@Test
	public void testValidExchangeList() throws IOException, OpenExchangeRatesException {
		// when
		ExchangeRatesDto exchangeRate = consumer.getExchangeRate(BASE_CURRENCY);

		// then
		assertThat(exchangeRate.getBase()).isEqualTo(BASE_CURRENCY);
		assertThat(exchangeRate.getRates())
				.containsKeys("EUR", "NZD", "GBP", "AUD", "JPY", "HUF");
	}

	@Test
	public void testValidConvertValue() throws IOException, OpenExchangeRatesException {
		// when
		ConvertDto convertedValue = consumer.convertValue(BASE_CURRENCY, CONVERT_TO_CURRENCY, CONVERT_AMOUNT);

		// then
		ConvertRequestDto convertRequest = convertedValue.getRequest();

//		assertThat(convertRequest).isNotNull();
		assertThat(convertRequest.getFrom()).isEqualTo(BASE_CURRENCY);
		assertThat(convertRequest.getTo()).isEqualTo(CONVERT_TO_CURRENCY);
		assertThat(convertRequest.getAmount()).isEqualTo(CONVERT_AMOUNT);
	}
}
