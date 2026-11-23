package fr.td.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Grimpeur {
    private final GrimpeurId id;
    private final boolean estAbonne;
    private int reservationsActives = 0;
    private LocalDate finSanctionG9;

    public Grimpeur(GrimpeurId id, boolean estAbonne) {
        this.id = id;
        this.estAbonne = estAbonne;
    }

    public void appliquerSanctionG9(LocalDate dateDebut) {
        this.finSanctionG9 = dateDebut.plusDays(7);
    }

    // Commande 1 : Réserver un créneau (Règles G3, G4, G9)
    public CreneauReserve reserverCreneau(ReservationId resId, CreneauId creneauId, LocalDateTime maintenant, LocalDateTime debutCreneau) {
        if (finSanctionG9 != null && !maintenant.toLocalDate().isAfter(finSanctionG9)) {
            throw new IllegalStateException("G9 : Abonné bloqué pendant 7 jours.");
        }
        if (reservationsActives >= 2) {
            throw new IllegalStateException("G4 : Maximum 2 réservations à venir.");
        }
        long heuresAvant = java.time.Duration.between(maintenant, debutCreneau).toHours();
        long maxHeures = estAbonne ? 7 * 24 : 48;
        if (heuresAvant > maxHeures) {
            throw new IllegalStateException("G3 : Réservation trop précoce pour cette formule.");
        }
        this.reservationsActives++;
        return new CreneauReserve(resId, id, creneauId, maintenant);
    }
}
