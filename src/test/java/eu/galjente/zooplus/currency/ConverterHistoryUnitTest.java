package eu.galjente.zooplus.currency;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterHistoryUnitTest {

	private final static Integer HISTORY_ID = 1;
	private final static String HISTORY_FROM = "USD";
	private final static String HISTORY_TO = "EUR";
	private final static BigDecimal HISTORY_AMOUNT = new BigDecimal("33.33");
	private final static BigDecimal HISTORY_EXCHANGE_RATE = new BigDecimal("0.33");
	private final static String HISTORY_ERROR_MESSAGE = "Test 134";
	private final static Instant HISTORY_CREATION_DATE = Instant.now();
	private final static BigDecimal HISTORY_EXCHANGED_AMOUNT = new BigDecimal("10.9989");

	@Test
	public void shouldBeEquals() {
		ConverterHistory history1 = createDefaultHistory();
		ConverterHistory history2 = createDefaultHistory();

		assertThat(history1.getId()).isEqualTo(history2.getId());
		assertThat(history1.getCurrencyFrom()).isEqualTo(history2.getCurrencyFrom());
		assertThat(history1.getCurrencyTo()).isEqualTo(history2.getCurrencyTo());
		assertThat(history1.getAmount()).isEqualTo(history2.getAmount());
		assertThat(history1.getExchangeRate()).isEqualTo(history2.getExchangeRate());
		assertThat(history1.getErrorMessage()).isEqualTo(history2.getErrorMessage());
		assertThat(history1.getCreationDate()).isEqualTo(history2.getCreationDate());
		assertThat(history1.getExchangedAmount()).isEqualTo(history2.getExchangedAmount());
		assertThat(history1.hashCode()).isEqualTo(history2.hashCode());
		assertThat(history1).isEqualTo(history2);
	}

	@Test
	public void shouldNotBeEquals() {
		ConverterHistory history1 = createDefaultHistory();
		ConverterHistory history2 = new ConverterHistory();

		history2.setId(2);
		history2.setCurrencyTo("GGG");

		assertThat(history1.hashCode()).isNotEqualTo(history2.hashCode());
		assertThat(history1).isNotEqualTo(history2);
	}

	@Test
	public void checkExchangedAmount() {
		ConverterHistory history1 = createDefaultHistory();
		assertThat(history1.getExchangedAmount()).isEqualTo(HISTORY_EXCHANGED_AMOUNT);
	}

	private ConverterHistory createDefaultHistory() {
		ConverterHistory history = new ConverterHistory();

		history.setId(HISTORY_ID);
		history.setCurrencyFrom(HISTORY_FROM);
		history.setCurrencyTo(HISTORY_TO);
		history.setAmount(HISTORY_AMOUNT);
		history.setExchangeRate(HISTORY_EXCHANGE_RATE);
		history.setErrorMessage(HISTORY_ERROR_MESSAGE);
		history.setCreationDate(HISTORY_CREATION_DATE);

		return history;
	}
}
