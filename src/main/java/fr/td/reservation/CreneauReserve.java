package fr.td.reservation;

import java.time.LocalDateTime;

public record CreneauReserve(ReservationId reservationId, GrimpeurId grimpeurId, CreneauId creneauId, LocalDateTime dateReservation) {}
