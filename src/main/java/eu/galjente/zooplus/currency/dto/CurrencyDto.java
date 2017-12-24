package eu.galjente.zooplus.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDto implements Serializable {

	private String currency;
	private String description;

	public CurrencyDto() {}

	public CurrencyDto(String currency, String description) {
		this.currency = currency;
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CurrencyDto that = (CurrencyDto) o;

		return new EqualsBuilder()
				.append(currency, that.currency)
				.append(description, that.description)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(currency)
				.append(description)
				.toHashCode();
	}
}
