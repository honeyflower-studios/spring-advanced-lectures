package net.honeyflower.lecturing.rest.exceptionhandling.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import net.honeyflower.lecturing.rest.exceptionhandling.student.Student;

public class StudentValidator implements ConstraintValidator<StudentValid, Student>  {

	@Override
	public boolean isValid(Student value, ConstraintValidatorContext context) {
		return !StringUtils.isEmpty(value.getName()) ||
				!StringUtils.isEmpty(value.getMiddleName()) ||
				!StringUtils.isEmpty(value.getFamilyName());
		
	}

}
