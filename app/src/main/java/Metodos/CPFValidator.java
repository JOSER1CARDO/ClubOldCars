package Metodos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPFValidator {

    private static final String CPF_PATTERN = "^\\d{11}$";

    private static final Pattern pattern = Pattern.compile(CPF_PATTERN);

    public static boolean isValidCPF(String cpf) {
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches() && isCPFValido(cpf) && cpfIgual(cpf);
    }

    private static boolean cpfIgual(String cpf){
        return true;
    }

    private static boolean isCPFValido(String cpf) {
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }

        // Verifica o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int remainder = 11 - (sum % 11);
        int digit1 = (remainder == 10 || remainder == 11) ? 0 : remainder;
        if (digit1 != digits[9]) {
            return false;
        }

        // Verifica o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        remainder = 11 - (sum % 11);
        int digit2 = (remainder == 10 || remainder == 11) ? 0 : remainder;
        return digit2 == digits[10];
    }
}
