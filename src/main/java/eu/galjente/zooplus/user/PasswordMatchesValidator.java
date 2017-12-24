package eu.galjente.zooplus.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context){
		RegistrationForm form = (RegistrationForm) obj;
		return form.getPassword() != null && form.getPassword().equals(form.getRepeatPassword());
	}
}
