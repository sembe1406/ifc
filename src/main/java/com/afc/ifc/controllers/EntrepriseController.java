package com.afc.ifc.controllers;

import com.afc.ifc.entities.Entreprise;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.TypeCalcul;
import com.afc.ifc.services.impl.EntrepriseRessource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des entreprises
 * 
 * @author Amstrong
 */
@RestController
@RequestMapping("/api/entreprises")
@Tag(name = "Entreprises", description = "API de gestion des entreprises et de leurs employés")
public class EntrepriseController {

    @Autowired
    private EntrepriseRessource entrepriseService;

    @GetMapping
    @Operation(summary = "Lister toutes les entreprises", description = "Récupère une liste paginée de toutes les entreprises")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des entreprises récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public Page<Entreprise> getAllEntreprises(
            @Parameter(description = "Numéro de la page (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page")
            @RequestParam(defaultValue = "20") int size) {
        return entrepriseService.getAllEntreprises(page, size);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des entreprises", description = "Recherche des entreprises par nom")
    public Page<Entreprise> searchEntreprises(
            @Parameter(description = "Nom de l'entreprise à rechercher")
            @RequestParam String nom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return entrepriseService.searchEntreprises(nom, page, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une entreprise", description = "Récupère les détails d'une entreprise par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entreprise trouvée"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    public ResponseEntity<Entreprise> getEntreprise(
            @Parameter(description = "ID de l'entreprise")
            @PathVariable long id) {
        return entrepriseService.findEntreprise(id);
    }

    @PostMapping
    @Operation(summary = "Créer une entreprise", description = "Crée une nouvelle entreprise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entreprise créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseService.addEntreprise(entreprise);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une entreprise", description = "Met à jour les informations d'une entreprise")
    public ResponseEntity<Entreprise> updateEntreprise(
            @PathVariable long id,
            @RequestBody Entreprise entreprise) {
        return entrepriseService.updateEntreprise(id, entreprise);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une entreprise", description = "Supprime une entreprise")
    @ApiResponse(responseCode = "204", description = "Entreprise supprimée avec succès")
    public void deleteEntreprise(@PathVariable long id) {
        entrepriseService.deleteEntreprise(id);
    }

    // === ENDPOINTS POUR LES EMPLOYÉS ===

    @GetMapping("/{id}/employes")
    @Operation(summary = "Lister les employés d'une entreprise", description = "Récupère tous les employés d'une entreprise")
    public ResponseEntity<List<Employe>> getEmployesEntreprise(@PathVariable long id) {
        return entrepriseService.findEmployeEntreprise(id);
    }

    @PostMapping("/{id}/employes")
    @Operation(summary = "Ajouter un employé", description = "Ajoute un nouvel employé à l'entreprise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employé ajouté avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    public ResponseEntity<Employe> addEmploye(
            @Parameter(description = "ID de l'entreprise")
            @PathVariable long id,
            @RequestBody Employe employe) {
        return entrepriseService.ajouterEmployeEntreprise(id, employe);
    }

    @PutMapping("/{id}/employes/{idEmploye}")
    @Operation(summary = "Modifier un employé", description = "Met à jour les informations d'un employé")
    public ResponseEntity<Employe> updateEmploye(
            @PathVariable long id,
            @PathVariable long idEmploye,
            @RequestBody Employe employe) {
        return entrepriseService.modifierEmployeEntreprise(id, idEmploye, employe);
    }

    @DeleteMapping("/{id}/employes/{idEmploye}")
    @Operation(summary = "Supprimer un employé", description = "Supprime un employé de l'entreprise")
    public void deleteEmploye(
            @PathVariable long id,
            @PathVariable long idEmploye) {
        entrepriseService.supprimerEmployeEntreprise(id, idEmploye);
    }

    @GetMapping("/{id}/employes/{idEmploye}")
    @Operation(summary = "Récupérer un employé", description = "Récupère les détails d'un employé")
    public ResponseEntity<Employe> getEmploye(
            @PathVariable long id,
            @PathVariable long idEmploye) {
        return entrepriseService.renvoyerEmployeEntreprise(idEmploye, id);
    }

    // === ENDPOINTS POUR LES CALCULS D'INDEMNITÉS ===

    @PostMapping("/{id}/employes/{idEmploye}/calcul-indemnite")
    @Operation(summary = "Calculer une indemnité", description = "Calcule l'indemnité de fin de carrière ou de licenciement pour un employé")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Indemnité calculée avec succès"),
        @ApiResponse(responseCode = "404", description = "Entreprise ou employé non trouvé"),
        @ApiResponse(responseCode = "400", description = "Type de calcul invalide")
    })
    public ResponseEntity<Dossier> calculerIndemnite(
            @Parameter(description = "ID de l'entreprise")
            @PathVariable long id,
            @Parameter(description = "ID de l'employé")
            @PathVariable long idEmploye,
            @Parameter(description = "Type de calcul à effectuer")
            @RequestParam TypeCalcul typeCalcul) {
        return entrepriseService.calculerIndemniteDossier(id, idEmploye, typeCalcul.name());
    }

    @GetMapping("/{id}/employes/calculs-ifc")
    @Operation(summary = "Calculer IFC pour tous les employés", description = "Calcule l'indemnité de fin de carrière pour tous les employés de l'entreprise")
    public List<Employe> calculerIfcTousEmployes(@PathVariable long id) {
        return entrepriseService.CalculIfcEmployeEntreprise(id);
    }
}
