package eu.galjente.zooplus.currency;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExchangeForm {

	@NotEmpty
	private String currencyFrom;

	@NotEmpty
	private String currencyTo;

	@Min(0)
	@NotNull
	private BigDecimal amount;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ExchangeForm that = (ExchangeForm) o;

		return new EqualsBuilder()
				.append(currencyFrom, that.currencyFrom)
				.append(currencyTo, that.currencyTo)
				.append(amount, that.amount)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(currencyFrom)
				.append(currencyTo)
				.append(amount)
				.toHashCode();
	}
}
