package fr.td.reservation;

import java.time.LocalDateTime;

public class VerificationReservation {
    public static void main(String[] args) {
        LocalDateTime maintenant = LocalDateTime.of(2026, 11, 23, 10, 0);
        LocalDateTime creneauDemain = LocalDateTime.of(2026, 11, 24, 14, 0);

        // Vérification 1 : CreneauReserve
        Grimpeur g = new Grimpeur(new GrimpeurId("G-1"), false);
        CreneauReserve evt1 = g.reserverCreneau(new ReservationId("R-1"), new CreneauId("C-1"), maintenant, creneauDemain);
        assert evt1.grimpeurId().valeur().equals("G-1");

        // Vérification 2 : GrimpeurNoteAbsent (G7)
        ReservationCreneau res = new ReservationCreneau(new ReservationId("R-1"), new GrimpeurId("G-1"), new CreneauId("C-1"), creneauDemain, true);
        GrimpeurNoteAbsent evt2 = res.constaterAbsence(creneauDemain.plusMinutes(31));
        assert evt2.penaliteCarteApplicable();

        // Vérification 3 : CreneauAnnule tardif (< 24h -> G8) puis refus si déjà annulé
        ReservationCreneau res3 = new ReservationCreneau(new ReservationId("R-3"), new GrimpeurId("G-3"), new CreneauId("C-1"), creneauDemain, true);
        CreneauAnnule evt3 = res3.annuler(creneauDemain.minusHours(10));
        assert evt3.annulationTardiveG8();
        assert evt3.penaliteCarteApplicable();

        boolean doubleAnnulationRefusee = false;
        try {
            res3.annuler(creneauDemain.minusHours(9));
        } catch (IllegalStateException e) {
            doubleAnnulationRefusee = true;
        }
        assert doubleAnnulationRefusee;

        System.out.println("OK : 3 événements du Core (CreneauReserve, GrimpeurNoteAbsent, CreneauAnnule) vérifiés.");
    }
}
