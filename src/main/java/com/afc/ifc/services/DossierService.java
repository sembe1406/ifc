package com.afc.ifc.services;

import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Entreprise;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des dossiers IFC
 * 
 * @author Amstrong
 */
public interface DossierService {
    
    /**
     * Créer un nouveau dossier pour un employé
     * 
     * @param employe L'employé
     * @param entreprise L'entreprise
     * @param typeCalcul Le type de calcul à effectuer
     * @return Le dossier créé
     */
    Dossier creerDossier(Employe employe, Entreprise entreprise, com.afc.ifc.entities.TypeCalcul typeCalcul);
    
    /**
     * Calculer l'indemnité pour un dossier
     * 
     * @param dossierId L'ID du dossier
     * @return Le dossier mis à jour avec l'indemnité calculée
     */
    Optional<Dossier> calculerIndemniteDossier(Long dossierId);
    
    /**
     * Trouver un dossier par employé et entreprise
     * 
     * @param employeId L'ID de l'employé
     * @param entrepriseId L'ID de l'entreprise
     * @return Le dossier s'il existe
     */
    Optional<Dossier> trouverDossierParEmployeEtEntreprise(Long employeId, Long entrepriseId);
    
    /**
     * Trouver tous les dossiers d'une entreprise
     * 
     * @param entrepriseId L'ID de l'entreprise
     * @return La liste des dossiers
     */
    List<Dossier> trouverDossiersParEntreprise(Long entrepriseId);
    
    /**
     * Mettre à jour le statut d'un dossier
     * 
     * @param dossierId L'ID du dossier
     * @param nouveauStatut Le nouveau statut
     * @return Le dossier mis à jour
     */
    Optional<Dossier> mettreAJourStatutDossier(Long dossierId, com.afc.ifc.entities.Statut nouveauStatut);
}
