package eu.galjente.zooplus.currency;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "converter_history")
public class ConverterHistory {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "currency_from", nullable = false)
	private String currencyFrom;

	@Column(name = "currency_to", nullable = false)
	private String currencyTo;

	@Column(name = "amount", nullable = false, precision = 15, scale = 3)
	private BigDecimal amount;

	@Column(name = "exchange_rate", precision = 20, scale = 13)
	private BigDecimal exchangeRate;

	@Column(name = "error_message")
	private String errorMessage;

	@Column(name = "creation_date", nullable = false)
	private Instant creationDate = Instant.now();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getExchangedAmount() {
		return exchangeRate == null ? null : amount.multiply(exchangeRate);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ConverterHistory that = (ConverterHistory) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(currencyFrom, that.currencyFrom)
				.append(currencyTo, that.currencyTo)
				.append(amount, that.amount)
				.append(exchangeRate, that.exchangeRate)
				.append(errorMessage, that.errorMessage)
				.append(creationDate, that.creationDate)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(currencyFrom)
				.append(currencyTo)
				.append(amount)
				.append(exchangeRate)
				.append(errorMessage)
				.append(creationDate)
				.toHashCode();
	}
}
