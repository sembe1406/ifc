package com.afc.ifc.services.impl;

import com.afc.ifc.dao.DossierDao;
import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Entreprise;
import com.afc.ifc.entities.Statut;
import com.afc.ifc.entities.TypeCalcul;
import com.afc.ifc.services.DossierService;
import com.afc.ifc.services.IfcCalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des dossiers
 * 
 * @author Amstrong
 */
@Service
@Transactional
public class DossierServiceImpl implements DossierService {
    
    @Autowired
    private DossierDao dossierDao;
    
    @Autowired
    private IfcCalculService ifcCalculService;

    @Override
    public Dossier creerDossier(Employe employe, Entreprise entreprise, TypeCalcul typeCalcul) {
        if (employe == null || entreprise == null || typeCalcul == null) {
            throw new IllegalArgumentException("L'employé, l'entreprise et le type de calcul ne peuvent pas être nuls");
        }
        
        Dossier dossier = new Dossier();
        dossier.setEmploye(employe);
        dossier.setEntreprise(entreprise);
        dossier.setTypeCalcul(typeCalcul);
        dossier.setStatut(Statut.CRE);
        dossier.setMontantIndemnite(0.0);
        
        return dossierDao.save(dossier);
    }

    @Override
    public Optional<Dossier> calculerIndemniteDossier(Long dossierId) {
        if (dossierId == null) {
            throw new IllegalArgumentException("L'ID du dossier ne peut pas être nul");
        }
        
        return dossierDao.findById(dossierId).map(dossier -> {
            Employe employe = dossier.getEmploye();
            TypeCalcul typeCalcul = dossier.getTypeCalcul();
            
            // Calculer l'indemnité selon le type
            double montant = ifcCalculService.calculerIndemnite(employe, typeCalcul);
            dossier.setMontantIndemnite(montant);
            
            // Mettre à jour le statut
            dossier.setStatut(Statut.SIM);
            
            return dossierDao.save(dossier);
        });
    }

    @Override
    public Optional<Dossier> trouverDossierParEmployeEtEntreprise(Long employeId, Long entrepriseId) {
        if (employeId == null || entrepriseId == null) {
            throw new IllegalArgumentException("Les IDs ne peuvent pas être nuls");
        }
        
        return dossierDao.findByEmployeIdAndEntrepriseId(employeId, entrepriseId);
    }

    @Override
    public List<Dossier> trouverDossiersParEntreprise(Long entrepriseId) {
        if (entrepriseId == null) {
            throw new IllegalArgumentException("L'ID de l'entreprise ne peut pas être nul");
        }
        
        // TODO: Ajouter cette méthode dans DossierDao
        // return dossierDao.findByEntrepriseId(entrepriseId);
        throw new UnsupportedOperationException("Méthode à implémenter dans DossierDao");
    }

    @Override
    public Optional<Dossier> mettreAJourStatutDossier(Long dossierId, Statut nouveauStatut) {
        if (dossierId == null || nouveauStatut == null) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être nuls");
        }
        
        return dossierDao.findById(dossierId).map(dossier -> {
            dossier.setStatut(nouveauStatut);
            return dossierDao.save(dossier);
        });
    }
}
