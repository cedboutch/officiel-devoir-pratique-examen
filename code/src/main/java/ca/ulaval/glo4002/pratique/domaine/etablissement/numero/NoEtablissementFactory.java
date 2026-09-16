package ca.ulaval.glo4002.pratique.domaine.etablissement.numero;

import java.util.UUID;
import java.util.regex.Pattern;

public class NoEtablissementFactory {
    private static final Pattern REGEX_NO_V1 = Pattern.compile("\\d+");

    public NoEtablissement depuisString(String numero) {
        if (REGEX_NO_V1.matcher(numero).matches()) {
            return new NoEtablissementV1(Long.parseLong(numero));
        }

        return new NoEtablissementV2(UUID.fromString(numero));
    }


}
