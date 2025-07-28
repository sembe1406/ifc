package com.afc.ifc.services;

import com.afc.ifc.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * Interface du service Utilisateur
 * 
 * @author Amstrong
 */
public interface IUtilisateurRessource {
    
    /**
     * Récupérer tous les utilisateurs avec pagination
     */
    ResponseEntity<Page<Utilisateur>> findAllUtilisateurs(int page, int size);
    
    /**
     * Ajouter un nouvel utilisateur
     */
    ResponseEntity<Utilisateur> addUtilisateur(Utilisateur user);
    
    /**
     * Trouver un utilisateur par ID
     */
    ResponseEntity<Utilisateur> findUtilisateur(long id);
    
    /**
     * Mettre à jour un utilisateur
     */
    ResponseEntity<Utilisateur> updateUtilisateur(long id, Utilisateur user);
    
    /**
     * Supprimer un utilisateur
     */
    void deleteUtilisateur(long id);
}
