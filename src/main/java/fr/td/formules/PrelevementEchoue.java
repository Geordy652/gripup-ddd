package fr.td.formules;

public record PrelevementEchoue(String reference, MotifEchec motif) implements ResultatPaiement {}
