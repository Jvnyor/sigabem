package br.com.javatest.sigabem.utils;

import java.util.Objects;

import br.com.javatest.sigabem.exception.ViaCepException;

public class CEPUtils {

	public static void validaCep(String cep) {
		if (Objects.isNull(cep) || cep.isEmpty() || cep.isBlank())
			throw new ViaCepException("O CEP informado não pode ser nulo ou vazio");
		if (removeMascaraCep(cep).length() < 8 || removeMascaraCep(cep).length() > 8)
			throw new ViaCepException("CEP faltando ou com mais numeros");
		if (!CepIsNumeric(removeMascaraCep(cep)))
			throw new ViaCepException("CEP sem números");
	}

	public static boolean CepIsNumeric(String s) {
		boolean isNumeric = true;
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				isNumeric = false;
			}
		}
		return isNumeric;
	}

	public static String removeMascaraCep(String cep) {
		if (cep.contains("-")) {
			cep = cep.replace("-", "");
			return cep;
		} else {
			return cep;
		}
	}

	public static String mascararCep(String cep) {
		if (!cep.contains("-")) {
			cep = cep.substring(0, 5) + "-" + cep.substring(5);
			return cep;
		} else {
			return cep;
		}
	}

}