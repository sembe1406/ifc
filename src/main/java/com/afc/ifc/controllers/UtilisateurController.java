package com.afc.ifc.controllers;

import com.afc.ifc.entities.Utilisateur;
import com.afc.ifc.services.impl.UtilisateurRessource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des utilisateurs
 * 
 * @author Amstrong
 */
@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    @Autowired
    private UtilisateurRessource utilisateurService;

    @GetMapping
    @Operation(summary = "Récupérer tous les utilisateurs", 
               description = "Récupère la liste paginée de tous les utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<Utilisateur>> getAllUtilisateurs(
            @Parameter(description = "Numéro de page (commence à 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page")
            @RequestParam(defaultValue = "20") int size) {
        
        return utilisateurService.findAllUtilisateurs(page, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur par ID", 
               description = "Récupère les détails d'un utilisateur spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Utilisateur> getUtilisateurById(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable Long id) {
        
        return utilisateurService.findUtilisateur(id);
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel utilisateur", 
               description = "Crée un nouvel utilisateur dans le système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Utilisateur> createUtilisateur(
            @Parameter(description = "Données de l'utilisateur à créer", required = true)
            @RequestBody Utilisateur utilisateur) {
        
        return utilisateurService.addUtilisateur(utilisateur);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un utilisateur", 
               description = "Met à jour les informations d'un utilisateur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données de l'utilisateur", required = true)
            @RequestBody Utilisateur utilisateur) {
        
        return utilisateurService.updateUtilisateur(id, utilisateur);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", 
               description = "Supprime un utilisateur du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Void> deleteUtilisateur(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable Long id) {
        
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
