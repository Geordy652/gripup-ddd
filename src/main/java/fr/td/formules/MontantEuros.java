package fr.td.formules;

import java.math.BigDecimal;

public record MontantEuros(BigDecimal valeur) {
    public static MontantEuros depuisCentimes(long centimes) {
        return new MontantEuros(BigDecimal.valueOf(centimes, 2));
    }
}
