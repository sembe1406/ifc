package com.afc.ifc.controllers;

import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.Statut;
import com.afc.ifc.entities.TypeCalcul;
import com.afc.ifc.services.DossierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des dossiers IFC
 * 
 * @author Amstrong
 */
@RestController
@RequestMapping("/api/dossiers")
@Tag(name = "Dossiers", description = "API de gestion des dossiers d'indemnités")
@CrossOrigin(origins = "*")
public class DossierController {

    @Autowired
    private DossierService dossierService;

    @GetMapping("/employe/{employeId}/entreprise/{entrepriseId}")
    @Operation(summary = "Récupérer un dossier par employé et entreprise", 
               description = "Récupère le dossier d'un employé dans une entreprise spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dossier trouvé"),
        @ApiResponse(responseCode = "404", description = "Dossier non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Dossier> getDossierByEmployeAndEntreprise(
            @Parameter(description = "ID de l'employé", required = true)
            @PathVariable Long employeId,
            @Parameter(description = "ID de l'entreprise", required = true)
            @PathVariable Long entrepriseId) {
        
        Optional<Dossier> dossier = dossierService.trouverDossierParEmployeEtEntreprise(employeId, entrepriseId);
        return dossier.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entreprise/{entrepriseId}")
    @Operation(summary = "Récupérer tous les dossiers d'une entreprise", 
               description = "Récupère tous les dossiers d'une entreprise spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dossiers récupérés avec succès"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée"),
        @ApiResponse(responseCode = "501", description = "Fonctionnalité non encore implémentée"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<List<Dossier>> getDossiersByEntreprise(
            @Parameter(description = "ID de l'entreprise", required = true)
            @PathVariable Long entrepriseId) {
        
        try {
            List<Dossier> dossiers = dossierService.trouverDossiersParEntreprise(entrepriseId);
            return ResponseEntity.ok(dossiers);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(501).build(); // Not Implemented
        }
    }

    @PostMapping("/{dossierId}/calculer")
    @Operation(summary = "Calculer l'indemnité d'un dossier", 
               description = "Lance le calcul de l'indemnité pour un dossier existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Calcul effectué avec succès"),
        @ApiResponse(responseCode = "404", description = "Dossier non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur lors du calcul")
    })
    public ResponseEntity<Dossier> calculerIndemniteDossier(
            @Parameter(description = "ID du dossier", required = true)
            @PathVariable Long dossierId) {
        
        Optional<Dossier> dossierCalcule = dossierService.calculerIndemniteDossier(dossierId);
        return dossierCalcule.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{dossierId}/statut")
    @Operation(summary = "Mettre à jour le statut d'un dossier", 
               description = "Change le statut d'un dossier (CRE, SIM, ABN, SUP)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Dossier non trouvé"),
        @ApiResponse(responseCode = "400", description = "Statut invalide"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Dossier> updateStatutDossier(
            @Parameter(description = "ID du dossier", required = true)
            @PathVariable Long dossierId,
            @Parameter(description = "Nouveau statut", required = true)
            @RequestParam Statut statut) {
        
        Optional<Dossier> dossierMisAJour = dossierService.mettreAJourStatutDossier(dossierId, statut);
        return dossierMisAJour.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/types-calcul")
    @Operation(summary = "Récupérer les types de calcul disponibles", 
               description = "Retourne la liste des types de calcul d'indemnités disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Types de calcul récupérés avec succès")
    })
    public ResponseEntity<TypeCalcul[]> getTypesCalcul() {
        return ResponseEntity.ok(TypeCalcul.values());
    }

    @GetMapping("/statuts")
    @Operation(summary = "Récupérer les statuts disponibles", 
               description = "Retourne la liste des statuts de dossier disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statuts récupérés avec succès")
    })
    public ResponseEntity<Statut[]> getStatuts() {
        return ResponseEntity.ok(Statut.values());
    }
}
