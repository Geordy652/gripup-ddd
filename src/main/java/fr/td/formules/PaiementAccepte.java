package fr.td.formules;

public record PaiementAccepte(String reference, MontantEuros montant) implements ResultatPaiement {}
