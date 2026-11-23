package fr.td.reservation;

public record GrimpeurNoteAbsent(ReservationId reservationId, GrimpeurId grimpeurId, CreneauId creneauId, boolean penaliteCarteApplicable) {}
