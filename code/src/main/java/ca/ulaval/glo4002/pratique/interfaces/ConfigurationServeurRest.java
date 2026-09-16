package ca.ulaval.glo4002.pratique.interfaces;

import ca.ulaval.glo4002.pratique.domaine.etablissement.numero.NoEtablissementFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.pratique.application.ServiceInspection;
import ca.ulaval.glo4002.pratique.domaine.etablissement.EtablissementStockage;
import ca.ulaval.glo4002.pratique.infrastructure.persistence.memoire.EtablissementStockageEnMemoire;

public class ConfigurationServeurRest extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(ServiceInspection.class);
        bind(EtablissementStockageEnMemoire.class).to(EtablissementStockage.class);
        bindAsContract(NoEtablissementFactory.class);
    }
}
