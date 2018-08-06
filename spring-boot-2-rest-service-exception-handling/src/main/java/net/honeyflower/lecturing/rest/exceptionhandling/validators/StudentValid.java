package net.honeyflower.lecturing.rest.exceptionhandling.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StudentValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentValid {
    String message() default "{student.entity.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
