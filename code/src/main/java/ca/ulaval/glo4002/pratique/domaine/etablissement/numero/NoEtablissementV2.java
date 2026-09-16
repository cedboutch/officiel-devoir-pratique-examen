package ca.ulaval.glo4002.pratique.domaine.etablissement.numero;

import java.util.Objects;
import java.util.UUID;

public class NoEtablissementV2 implements NoEtablissement {
    private final UUID numero;

    public static NoEtablissementV2 genererV2() {
        return new NoEtablissementV2(UUID.randomUUID());
    }

    public NoEtablissementV2(UUID numero) {
        this.numero = numero;
    }

    @Override
    public String asString() {
        return this.numero.toString();
    }

    @Override
    public boolean equals(Object autre) {
        if (this == autre) {
            return true;
        }
        if (autre == null || getClass() != autre.getClass()) {
            return false;
        }

        NoEtablissementV2 that = (NoEtablissementV2) autre;
        return this.numero.equals(that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero);
    }
}
