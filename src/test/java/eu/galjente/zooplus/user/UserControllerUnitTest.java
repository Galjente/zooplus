package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	public void loginPageShouldReturnLoginFtl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/login")).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		ModelAndView modelAndView = mvcResult.getModelAndView();

		assertThat(modelAndView.getViewName()).isEqualTo("user/login");
		assertThat(response.getCharacterEncoding()).isEqualTo("UTF-8");
		assertThat(response.getContentType()).isEqualTo("text/html;charset=UTF-8");
	}

	@Test
	public void registrationPageShouldReturnRegistrationFtl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/registration")).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		Set<String> countryList = (Set<String>)modelAndView.getModel().get("countyList");
		RegistrationForm registrationForm = (RegistrationForm)modelAndView.getModel().get("registrationForm");

		assertThat(modelAndView.getViewName()).isEqualTo("user/registration");
		assertThat(response.getCharacterEncoding()).isEqualTo("UTF-8");
		assertThat(response.getContentType()).isEqualTo("text/html;charset=UTF-8");
		assertThat(registrationForm).isEqualTo(new RegistrationForm());
		assertThat(countryList)
				.contains("Latvia", "Poland", "Germany")
				.doesNotContain("", " ");
	}

	@Test
	public void registrationPageShouldReturnRedirectToHomepage() throws Exception {
		when(userService.registerUser(any())).thenReturn(new User());

		MvcResult mvcResult = mockMvc.perform(
					post("/registration")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.params(createDefaultParameters())
				).andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();

		assertThat(modelAndView.getViewName()).isEqualTo("redirect:/");
		assertThat(modelAndView.getModel()).isEmpty();
	}

	@Test
	public void registrationPageWithoutEmailShouldReturnError() throws Exception {
		pageTestWithoutField("email");
	}

	@Test
	public void registrationPageWithoutPasswordShouldReturnError() throws Exception {
		pageTestWithoutField("password");
	}

	@Test
	public void registrationPageWithoutRepeatPasswordShouldReturnError() throws Exception {
		pageTestWithoutField("repeatPassword");
	}

	@Test
	public void registrationPageWithoutCountryShouldReturnError() throws Exception {
		pageTestWithoutField("country");
	}

	@Test
	public void registrationPageWithoutCityShouldReturnError() throws Exception {
		pageTestWithoutField("city");
	}

	@Test
	public void registrationPageWithoutAddressShouldReturnError() throws Exception {
		pageTestWithoutField("address");
	}

	@Test
	public void registrationPageWithoutZipShouldReturnError() throws Exception {
		pageTestWithoutField("zip");
	}

	private void pageTestWithoutField(String fieldName) throws Exception {
		when(userService.registerUser(any())).thenReturn(new User());
		MultiValueMap<String, String> parameters = createDefaultParameters();
		parameters.remove(fieldName);

		MvcResult mvcResult = mockMvc.perform(
				post("/registration")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.params(parameters)
		).andReturn();

		ModelAndView modelAndView = mvcResult.getModelAndView();
		BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult)modelAndView.getModel().get("org.springframework.validation.BindingResult.registrationForm");
		List<FieldError> errors = bindingResult.getFieldErrors();

		assertThat(modelAndView.getViewName()).isEqualTo("user/registration");
		assertThat(errors.get(0).getField()).isEqualTo(fieldName);
	}

	private MultiValueMap createDefaultParameters() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		parameters.set("email", "a@a.aaa");
		parameters.set("password", "1234");
		parameters.set("repeatPassword", "1234");
		parameters.set("birthday", "2017-08-08");
		parameters.set("country", "Latvia");
		parameters.set("city", "Riga");
		parameters.set("address", "Test");
		parameters.set("zip", "123456");

		return parameters;
	}
}
