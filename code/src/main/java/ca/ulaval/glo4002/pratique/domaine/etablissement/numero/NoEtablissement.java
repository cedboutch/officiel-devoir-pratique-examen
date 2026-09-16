package ca.ulaval.glo4002.pratique.domaine.etablissement.numero;

public interface NoEtablissement {

    String asString();

    @Override
    boolean equals(Object autre);

    @Override
    int hashCode();
}
