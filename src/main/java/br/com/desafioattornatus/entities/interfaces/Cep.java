package br.com.desafioattornatus.entities.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.desafioattornatus.services.CepValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CepValidator.class)

public @interface Cep {
	
	String message() default "CEP inválido";
	
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
