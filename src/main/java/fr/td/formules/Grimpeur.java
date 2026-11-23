package fr.td.formules;

public class Grimpeur {
    private final GrimpeurId id;
    private final String typeFormule; // CARTE_10, MENSUEL, ANNUEL
    private boolean suspenduG17 = false;
    private int entreesRestantes;

    public Grimpeur(GrimpeurId id, String typeFormule, int entreesInitiales) {
        this.id = id;
        this.typeFormule = typeFormule;
        this.entreesRestantes = entreesInitiales;
    }

    public void appliquerResultatPaiement(ResultatPaiement resultat) {
        if (resultat instanceof PrelevementEchoue) {
            this.suspenduG17 = true;
        } else if (resultat instanceof PaiementAccepte) {
            this.suspenduG17 = false;
        }
    }

    public boolean peutReserver() {
        if (suspenduG17) return false;
        if ("CARTE_10".equals(typeFormule)) return entreesRestantes > 0;
        return true;
    }
}
