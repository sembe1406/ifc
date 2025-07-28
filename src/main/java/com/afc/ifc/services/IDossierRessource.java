package com.afc.ifc.services;

import com.afc.ifc.entities.Dossier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * Interface du service Dossier
 * 
 * @author MENGA BAMO Emmanuel
 */
public interface IDossierRessource {
    
    /**
     * Récupérer tous les dossiers avec pagination
     */
    ResponseEntity<Page<Dossier>> getAllDossiers(int page, int pagesize);
    
    /**
     * Trouver un dossier par ID
     */
    ResponseEntity<Dossier> findDossier(long id);
    
    /**
     * Rechercher un dossier par nom
     */
    ResponseEntity<Dossier> searchDossier(String nom);
}
