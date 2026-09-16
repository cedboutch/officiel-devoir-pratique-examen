package ca.ulaval.glo4002.pratique.application;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;

import ca.ulaval.glo4002.pratique.interfaces.rest.dto.EtatEquipement;
import ca.ulaval.glo4002.pratique.domaine.etablissement.EtablissementStockage;
import ca.ulaval.glo4002.pratique.domaine.etablissement.Etablissement;
import ca.ulaval.glo4002.pratique.domaine.equipement.StatutEquipement;
import ca.ulaval.glo4002.pratique.domaine.etablissement.numero.NoEtablissement;
import ca.ulaval.glo4002.pratique.domaine.equipement.Equipement;
import jakarta.inject.Inject;

@Service
public class ServiceInspection {

    private final EtablissementStockage etablissementStockage;

    @Inject
    public ServiceInspection(
        EtablissementStockage etablissementStockage
    ) {
        this.etablissementStockage = etablissementStockage;
    }

    public List<EtatEquipement> listerEtatEquipement(
            NoEtablissement noEtablissement,
            boolean inspectionSeulement
    ) {
        Etablissement etablissement = this.etablissementStockage.trouverEtablissement(noEtablissement);

        List<EtatEquipement> equipementARemplacer = inspectionSeulement
                ? List.of()
                : trouverEquipementARemplacer(etablissement.obtenirEquipements());

        List<EtatEquipement> equipementAInspecter =
                this.trouverEquipementAInspecter(etablissement.obtenirEquipements(), inspectionSeulement);

        List<EtatEquipement> etats = equipementAInspecter.stream()
                .filter(inspection ->
                        equipementARemplacer.stream()
                                .noneMatch(remplacement -> remplacement.estPourMemeEquipement(inspection))
                )
                .collect(Collectors.toList());
        etats.addAll(equipementARemplacer);
        return etats;
    }

    private List<EtatEquipement> trouverEquipementARemplacer (List<Equipement> equipements) {
        List<EtatEquipement> equipementARemplacer = new LinkedList<>();

        LocalDateTime aujourdhui = LocalDateTime.now();

        for (var equipement : equipements) {
            if (equipement.estPerimable() && equipement.datePeremption().isAfter(aujourdhui)) {
                EtatEquipement etat = new EtatEquipement(equipement.getNoSerie(), equipement.getDescription(), StatutEquipement.A_REMPLACER);
                equipementARemplacer.add(etat);
            }

            if (equipement.estUnContenant()) {
                equipementARemplacer.addAll(this.trouverEquipementARemplacer(equipement.getEquipementDansContenant()));
            }
        }

        return equipementARemplacer;
    }

    private List<EtatEquipement> trouverEquipementAInspecter(List<Equipement> equipements, boolean inspectionSeulement) {
        List<EtatEquipement> equipementAInspecter = new LinkedList<>();

        for (var equipement : equipements) {
            StatutEquipement statutInspection = equipement.getStatutInspection();
            if (statutInspection != StatutEquipement.OK) {
                EtatEquipement etat = new EtatEquipement(equipement.getNoSerie(), equipement.getDescription(), statutInspection);
                equipementAInspecter.add(etat);
            }

            if (equipement.estUnContenant()) {
                this.trouverEquipementAInspecter(equipement.getEquipementDansContenant(), inspectionSeulement
                    );
            }
        }
        return equipementAInspecter;
    }
}
