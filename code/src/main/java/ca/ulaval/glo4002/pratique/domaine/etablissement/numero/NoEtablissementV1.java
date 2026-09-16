package ca.ulaval.glo4002.pratique.domaine.etablissement.numero;

import java.util.Objects;

public class NoEtablissementV1 implements NoEtablissement {
    private final long numero;

    public NoEtablissementV1(long numero) {
        this.numero = numero;
    }

    @Override
    public String asString() {
        return Long.toString(this.numero);
    }

    @Override
    public boolean equals(Object autre) {
        if (this == autre) {
            return true;
        }
        if (autre == null || getClass() != autre.getClass()) {
            return false;
        }

        NoEtablissementV1 that = (NoEtablissementV1) autre;
        return this.numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero);
    }
}
