package eu.galjente.zooplus;

import eu.galjente.zooplus.user.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private final static String[] RESOURCE_URIS = {"/favicon.ico", "/css/**", "/js/**", "/webjars/**"};

	private final String[] publicPagesUri;
	private final CustomUserDetailService customUserDetailService;

	@Autowired
	public ApplicationSecurity(@Value("${security.public.pages}") String[] publicPagesUri,  CustomUserDetailService customUserDetailService) {
		this.publicPagesUri = publicPagesUri;
		this.customUserDetailService = customUserDetailService;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(RESOURCE_URIS);
	}

	@Bean
	@Primary
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.headers().frameOptions().sameOrigin();
		http.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers(publicPagesUri).permitAll()
				.anyRequest().authenticated()
				.and().formLogin()
					.usernameParameter("email")
					.passwordParameter("password")
					.loginPage("/login")
					.failureUrl("/login?error")
					.permitAll()
				.and().logout()
					.logoutUrl("/logout")
					.permitAll();
		// @formatter:on
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return customUserDetailService;
	}
}
