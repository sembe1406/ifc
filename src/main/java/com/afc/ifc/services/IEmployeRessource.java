package com.afc.ifc.services;

import com.afc.ifc.entities.Employe;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * Interface du service Employe
 * 
 * @author Amstrong
 */
public interface IEmployeRessource {
    
    /**
     * Récupérer tous les employés avec pagination
     */
    ResponseEntity<Page<Employe>> getAllEmployes(int page, int pagesize);
    
    /**
     * Trouver un employé par ID
     */
    ResponseEntity<Employe> findEmploye(long id);
    
    /**
     * Rechercher un employé par nom
     */
    ResponseEntity<Employe> searchEmploye(String nom);
}
