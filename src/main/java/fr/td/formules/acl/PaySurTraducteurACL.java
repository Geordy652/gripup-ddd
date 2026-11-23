package fr.td.formules.acl;

import fr.td.formules.*;

record ReponseBrutePaySur(String transactionRef, String statusCode, long amountInCents) {}

public class PaySurTraducteurACL {
    public ResultatPaiement traduire(ReponseBrutePaySur brute) {
        if (brute == null || brute.statusCode() == null) {
            throw new IllegalArgumentException("Réponse PaySûr invalide.");
        }
        MontantEuros montant = MontantEuros.depuisCentimes(brute.amountInCents());

        return switch (brute.statusCode()) {
            case "PAID" -> new PaiementAccepte(brute.transactionRef(), montant);
            case "FAILED_NSF" -> new PrelevementEchoue(brute.transactionRef(), MotifEchec.PROVISION_INSUFFISANTE);
            case "FAILED_EXPIRED" -> new PrelevementEchoue(brute.transactionRef(), MotifEchec.CARTE_EXPIREE);
            case "FAILED_DECLINED" -> new PrelevementEchoue(brute.transactionRef(), MotifEchec.REFUS_BANCAIRE);
            default -> throw new UnsupportedOperationException("Code PaySûr inconnu : " + brute.statusCode());
        };
    }
}
