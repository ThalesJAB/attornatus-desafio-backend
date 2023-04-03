package br.com.desafioattornatus.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.desafioattornatus.entities.interfaces.Cep;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CepValidator implements ConstraintValidator<Cep, String> {

    private Pattern pattern1;
    private Pattern pattern2;

    @Override
    public void initialize(Cep constraintAnnotation) {
        pattern1 = Pattern.compile("\\d{8}");
        pattern2 = Pattern.compile("\\d{5}-?\\d{3}?");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        Matcher matcher1 = pattern1.matcher(value);
        Matcher matcher2 = pattern2.matcher(value);

        return matcher1.matches() || matcher2.matches();
    }
}