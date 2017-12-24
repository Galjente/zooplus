package eu.galjente.zooplus.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConvertDto implements Serializable {

	private String disclaimer;			// Disclaimer link
	private String license;				// License agreement link
	private ConvertRequestDto request;
	private ConvertMetaDto meta;
	private BigDecimal response;		// converted value

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

	public ConvertRequestDto getRequest() {
		return request;
	}

	public void setRequest(ConvertRequestDto request) {
		this.request = request;
	}

	public ConvertMetaDto getMeta() {
		return meta;
	}

	public void setMeta(ConvertMetaDto meta) {
		this.meta = meta;
	}

	public BigDecimal getResponse() {
		return response;
	}

	public void setResponse(BigDecimal response) {
		this.response = response;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ConvertDto that = (ConvertDto) o;

		return new EqualsBuilder()
				.append(disclaimer, that.disclaimer)
				.append(license, that.license)
				.append(request, that.request)
				.append(meta, that.meta)
				.append(response, that.response)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(disclaimer)
				.append(license)
				.append(request)
				.append(meta)
				.append(response)
				.toHashCode();
	}
}
