package eu.galjente.zooplus.currency.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRatesDto {

	private String disclaimer;	// Disclaimer link
	private String license;		// License agreement link
	private Long timestamp;		// Timestamp collected in seconds
	private String base;		// Base currency
	private Map<String, BigDecimal> rates;

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}
}
