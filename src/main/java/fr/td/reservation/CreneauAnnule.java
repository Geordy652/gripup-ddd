package fr.td.reservation;

import java.time.LocalDateTime;

public record CreneauAnnule(
    ReservationId reservationId,
    GrimpeurId grimpeurId,
    CreneauId creneauId,
    boolean annulationTardiveG8,
    boolean penaliteCarteApplicable,
    LocalDateTime dateAnnulation
) {}
