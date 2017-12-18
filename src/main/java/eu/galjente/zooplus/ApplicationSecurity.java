package eu.galjente.zooplus;

import eu.galjente.zooplus.user.CustomUserDetailService;
import eu.galjente.zooplus.user.domain.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private final static String[] PUBLIC_PAGE_URIS = {"/h2-console/**", "/registration"};
	private final static String[] RESOURCE_URIS = {"/favicon.ico", "/css/**", "/webjars/**"};

	private final CustomUserDetailService customUserDetailService;

	@Autowired
	public ApplicationSecurity(CustomUserDetailService customUserDetailService) {
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
				.antMatchers(PUBLIC_PAGE_URIS).permitAll()
				.anyRequest().hasAnyRole(Authority.ROLE_USER)
				.and().formLogin()
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
