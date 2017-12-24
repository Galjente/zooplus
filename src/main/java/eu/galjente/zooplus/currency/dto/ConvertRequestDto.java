package eu.galjente.zooplus.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConvertRequestDto implements Serializable {

	private String query;
	private BigDecimal amount;
	private String from;		// currency from
	private String to;			// currency to

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ConvertRequestDto that = (ConvertRequestDto) o;

		return new EqualsBuilder()
				.append(query, that.query)
				.append(amount, that.amount)
				.append(from, that.from)
				.append(to, that.to)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(query)
				.append(amount)
				.append(from)
				.append(to)
				.toHashCode();
	}
}
