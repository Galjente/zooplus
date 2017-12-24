package eu.galjente.zooplus.currency;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import eu.galjente.zooplus.currency.dto.*;
import eu.galjente.zooplus.currency.exception.*;
import eu.galjente.zooplus.utils.ResourceUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class OpenExchangeRatesRestConsumerUnitTest {

	private final static Integer SERVER_PORT = 8080;
	private final static String API_TOKEN = "token";
	private final static String API_URL = "http://localhost:" + SERVER_PORT + "/api";

	private final static String BASE_CURRENCY = "USD";
	private final static String CONVERT_FROM_CURRENCY = "GBP";
	private final static String CONVERT_TO_CURRENCY = "EUR";
	private final static BigDecimal CONVERT_AMOUNT = new BigDecimal("19999.95");
	private final static BigDecimal CONVERT_RATE = new BigDecimal("1.383702");
	private final static BigDecimal CONVERT_RESULT = new BigDecimal("27673.975864");

	private OpenExchangeRatesRestConsumer consumer;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(SERVER_PORT).jettyStopTimeout(5000L));

	@Before
	public void setUp() {
		consumer = new OpenExchangeRatesRestConsumer(API_URL, API_TOKEN, new RestTemplate());
	}

	@Test
	public void testValidCurrencyList() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
			get(urlPathEqualTo("/api/currencies.json"))
					.withQueryParam("app_id", new EqualToPattern(API_TOKEN))
					.willReturn(
					aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", APPLICATION_JSON_VALUE)
						.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/currency.json"))
			)
		);

		// when
		List<CurrencyDto> currencies = consumer.getCurrencies();

		// then
		assertThat(currencies)
				.hasSize(168)
				.contains(new CurrencyDto("EUR", "Euro"))
				.contains(new CurrencyDto("USD", "United States Dollar"));
	}

	@Test
	public void testValidExchangeList() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/latest.json"))
						.withQueryParam("base", new EqualToPattern(BASE_CURRENCY))
						.withQueryParam("app_id", new EqualToPattern(API_TOKEN))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.OK.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/exchangeRate.json"))
						)
		);

		// when
		ExchangeRatesDto exchangeRate = consumer.getExchangeRate(BASE_CURRENCY);

		// then
		assertThat(exchangeRate.getBase()).isEqualTo(BASE_CURRENCY);
		assertThat(exchangeRate.getRates())
				.hasSize(8)
				.containsEntry("AED", new BigDecimal("3.672538"))
				.containsEntry("AUD", new BigDecimal("1.390866"));
	}

	@Test
	public void testValidConvertValue() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/convert/" + CONVERT_AMOUNT + "/" + CONVERT_FROM_CURRENCY + "/" + CONVERT_TO_CURRENCY))
						.withQueryParam("app_id", new EqualToPattern(API_TOKEN))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.OK.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/convert.json"))
						)
		);

		// when
		ConvertDto convertedValue = consumer.convertValue(CONVERT_FROM_CURRENCY, CONVERT_TO_CURRENCY, CONVERT_AMOUNT);

		// then

		ConvertRequestDto convertRequest = convertedValue.getRequest();
		ConvertMetaDto convertMeta = convertedValue.getMeta();

		assertThat(convertRequest.getFrom()).isEqualTo(CONVERT_FROM_CURRENCY);
		assertThat(convertRequest.getTo()).isEqualTo(CONVERT_TO_CURRENCY);
		assertThat(convertRequest.getAmount()).isEqualTo(CONVERT_AMOUNT);
		assertThat(convertMeta.getRate()).isEqualTo(CONVERT_RATE);
		assertThat(convertedValue.getResponse()).isEqualTo(CONVERT_RESULT);
	}

	@Test(expected = OpenExchangeRatesAccessForbiddenException.class)
	public void testError403ConvertValue() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/convert/" + CONVERT_AMOUNT + "/" + CONVERT_FROM_CURRENCY + "/" + CONVERT_TO_CURRENCY))
						.withQueryParam("app_id", new EqualToPattern(API_TOKEN))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.FORBIDDEN.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/convert403.json"))
						)
		);

		// when
		consumer.convertValue(CONVERT_FROM_CURRENCY, CONVERT_TO_CURRENCY, CONVERT_AMOUNT);
	}

	@Test(expected = OpenExchangeRatesUnauthorizedException.class)
	public void testError401ExchangeList() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/latest.json"))
						.withQueryParam("base", new EqualToPattern(BASE_CURRENCY))
						.withQueryParam("app_id", new EqualToPattern(API_TOKEN))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.UNAUTHORIZED.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/exchangeRate401.json"))
						)
		);

		// when
		consumer.getExchangeRate(BASE_CURRENCY);
	}

	@Test(expected = OpenExchangeRatesInvalidCurrencyException.class)
	public void testError400ExchangeList() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/latest.json"))
						.withQueryParam("base", new EqualToPattern(BASE_CURRENCY))
						.withQueryParam("app_id", new EqualToPattern(API_TOKEN))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.BAD_REQUEST.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/exchangeRate400.json"))
						)
		);

		// when
		consumer.getExchangeRate(BASE_CURRENCY);
	}

	@Test(expected = OpenExchangeRatesNotAllowedException.class)
	public void testError429CurrencyList() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/currencies.json"))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.TOO_MANY_REQUESTS.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/currency429.json"))
						)
		);

		// when
		consumer.getCurrencies();
	}

	@Test(expected = OpenExchangeRatesResourceNotFoundException.class)
	public void testError404CurrencyList() throws IOException, OpenExchangeRatesException {
		// given
		stubFor(
				get(urlPathEqualTo("/api/currencies.json"))
						.willReturn(
								aResponse()
										.withStatus(HttpStatus.NOT_FOUND.value())
										.withHeader("Content-Type", APPLICATION_JSON_VALUE)
										.withBody(ResourceUtils.readResourceFromClassPathAsString("/json/currency404.json"))
						)
		);

		// when
		consumer.getCurrencies();
	}
}
