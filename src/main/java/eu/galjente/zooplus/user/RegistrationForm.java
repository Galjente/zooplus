package eu.galjente.zooplus.user;

import eu.galjente.zooplus.system.Convertible;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@PasswordMatches
public class RegistrationForm implements Convertible<RegistrationForm> {

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

	@NotEmpty
	private String repeatPassword;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate birthday;

	@NotNull
	private String country;

	@NotNull
	private String city;

	@NotNull
	private String address;

	@NotNull
	private String zip;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		RegistrationForm that = (RegistrationForm) o;

		return new EqualsBuilder()
				.append(email, that.email)
				.append(password, that.password)
				.append(repeatPassword, that.repeatPassword)
				.append(birthday, that.birthday)
				.append(country, that.country)
				.append(city, that.city)
				.append(address, that.address)
				.append(zip, that.zip)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(email)
				.append(password)
				.append(repeatPassword)
				.append(birthday)
				.append(country)
				.append(city)
				.append(address)
				.append(zip)
				.toHashCode();
	}
}
