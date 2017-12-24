package eu.galjente.zooplus.user;

import eu.galjente.zooplus.system.Converter;
import eu.galjente.zooplus.user.domain.entity.User;

public class RegistrationFormUserConverter implements Converter<User, RegistrationForm> {

	@Override
	public User convert(RegistrationForm value) {
		User user = new User();

		user.setEmail(value.getEmail());
		user.setBirthday(value.getBirthday());
		user.setCountry(value.getCountry());
		user.setCity(value.getCity());
		user.setAddress(value.getAddress());
		user.setZip(value.getZip());

		return user;
	}

}
