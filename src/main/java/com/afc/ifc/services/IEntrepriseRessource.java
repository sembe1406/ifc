package com.afc.ifc.services;

import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Entreprise;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * Interface du service Entreprise
 * 
 * @author Amstrong
 */
public interface IEntrepriseRessource {
    
    /**
     * Récupérer toutes les entreprises avec pagination
     */
    Page<Entreprise> getAllEntreprises(int page, int pagesize);
    
    /**
     * Rechercher des entreprises par nom
     */
    Page<Entreprise> searchEntreprises(String nom, int page, int pagesize);
    
    /**
     * Trouver une entreprise par ID
     */
    ResponseEntity<Entreprise> findEntreprise(long id);
    
    /**
     * Mettre à jour une entreprise
     */
    ResponseEntity<Entreprise> updateEntreprise(long id, Entreprise entreprise);
    
    /**
     * Ajouter une nouvelle entreprise
     */
    ResponseEntity<Entreprise> addEntreprise(Entreprise entreprise);
    
    /**
     * Supprimer une entreprise
     */
    void deleteEntreprise(long id);
    
    // Méthodes pour la gestion des employés
    ResponseEntity<List<Employe>> findEmployeEntreprise(long idEntreprise);
    List<Employe> CalculIfcEmployeEntreprise(long idEntreprise);
    ResponseEntity<Employe> ajouterEmployeEntreprise(long idEntreprise, Employe employe);
    ResponseEntity<Employe> modifierEmployeEntreprise(long idEntreprise, long idEmploye, Employe employe);
    void supprimerEmployeEntreprise(long idEntreprise, long idEmploye);
    ResponseEntity<Employe> renvoyerEmployeEntreprise(long idEmploye, long idEntreprise);
    
    // Méthodes pour la gestion des dossiers
    ResponseEntity<List<Dossier>> findDossierEntreprise(long idEntreprise);
    ResponseEntity<Dossier> CalculIfconeDossierEntreprise(long idEntreprise, long idEmploye);
    ResponseEntity<Dossier> calculerIndemniteDossier(long idEntreprise, long idEmploye, String typeCalcul);
    ResponseEntity<Dossier> modifierStatutDossierEntreprise(long idEntreprise, long idDossier, Dossier dossier);
    void supprimerDossierEntreprise(long idDossier, long idEntreprise);
    ResponseEntity<Dossier> renvoyerDossierEntreprise(long idEntreprise, long idDossier);
}
