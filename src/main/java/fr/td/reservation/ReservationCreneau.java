package fr.td.reservation;

import java.time.Duration;
import java.time.LocalDateTime;

public class ReservationCreneau {
    private final ReservationId id;
    private final GrimpeurId grimpeurId;
    private final CreneauId creneauId;
    private final LocalDateTime debutCreneau;
    private final boolean estPorteurCarte;
    private boolean badgeScanne = false;
    private boolean absent = false;
    private boolean annulee = false;

    public ReservationCreneau(ReservationId id, GrimpeurId grimpeurId, CreneauId creneauId, LocalDateTime debutCreneau, boolean estPorteurCarte) {
        this.id = id;
        this.grimpeurId = grimpeurId;
        this.creneauId = creneauId;
        this.debutCreneau = debutCreneau;
        this.estPorteurCarte = estPorteurCarte;
    }

    public GrimpeurNoteAbsent constaterAbsence(LocalDateTime heureActuelle) {
        if (badgeScanne) {
            throw new IllegalStateException("G7 : Passage déjà validé.");
        }
        if (Duration.between(debutCreneau, heureActuelle).toMinutes() <= 30) {
            throw new IllegalStateException("G7 : Délai de 30 minutes non écoulé.");
        }
        this.absent = true;
        return new GrimpeurNoteAbsent(id, grimpeurId, creneauId, estPorteurCarte);
    }

    // Commande 3 : Annuler une réservation de créneau (Règle G8 : < 24h = tardive)
    public CreneauAnnule annuler(LocalDateTime dateAnnulation) {
        if (annulee || absent || badgeScanne) {
            throw new IllegalStateException("Annulation impossible : réservation déjà annulée, absente ou consommée.");
        }
        long heuresAvantDebut = Duration.between(dateAnnulation, debutCreneau).toHours();
        boolean tardive = heuresAvantDebut < 24;
        boolean penaliteCarte = tardive && estPorteurCarte;
        this.annulee = true;
        return new CreneauAnnule(id, grimpeurId, creneauId, tardive, penaliteCarte, dateAnnulation);
    }
}
