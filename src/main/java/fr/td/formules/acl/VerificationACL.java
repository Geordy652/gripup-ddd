package fr.td.formules.acl;

import fr.td.formules.Grimpeur;
import fr.td.formules.GrimpeurId;
import fr.td.formules.MotifEchec;
import fr.td.formules.PaiementAccepte;
import fr.td.formules.PrelevementEchoue;
import fr.td.formules.ResultatPaiement;
import java.math.BigDecimal;

public class VerificationACL {
    public static void main(String[] args) {
        PaySurTraducteurACL acl = new PaySurTraducteurACL();

        // 1. PAID -> PaiementAccepte en euros
        ResultatPaiement r1 = acl.traduire(new ReponseBrutePaySur("TX-1", "PAID", 5900));
        assert r1 instanceof PaiementAccepte p && p.montant().valeur().equals(new BigDecimal("59.00"));

        // 2. FAILED_NSF -> PrelevementEchoue + suspension G17
        ResultatPaiement r2 = acl.traduire(new ReponseBrutePaySur("TX-2", "FAILED_NSF", 5900));
        assert r2 instanceof PrelevementEchoue e && e.motif() == MotifEchec.PROVISION_INSUFFISANTE;

        Grimpeur hugo = new Grimpeur(new GrimpeurId("G-HUGO"), "MENSUEL", 0);
        hugo.appliquerResultatPaiement(r2);
        assert !hugo.peutReserver();

        // 3. Code inconnu -> rejet explicite
        boolean inconnuRejete = false;
        try {
            acl.traduire(new ReponseBrutePaySur("TX-3", "UNKNOWN_ERR", 5900));
        } catch (UnsupportedOperationException ex) {
            inconnuRejete = true;
        }
        assert inconnuRejete;
        System.out.println("OK : Modèle formules + ACL PaySûr vérifiés.");
    }
}
