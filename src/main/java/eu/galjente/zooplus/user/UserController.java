package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "user/login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationPage(ModelMap model, RegistrationForm registrationForm) {
		Set<String> countyList = Arrays.stream(Locale.getAvailableLocales())
									.map(Locale::getDisplayCountry)
									.filter(country -> !country.isEmpty())
									.sorted(String::compareTo)
									.collect(Collectors.toCollection(TreeSet::new));
		model.put("countyList", countyList);
		model.put("registrationForm", registrationForm);
		return "user/registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@Valid RegistrationForm registrationForm, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return registrationPage(model, registrationForm);
		}

		User user = userService.registerUser(registrationForm);
		userService.signInRegisteredUser(user, registrationForm);

		return "redirect:/";
	}
}
