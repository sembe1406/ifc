package com.afc.ifc.controllers;

import com.afc.ifc.entities.Employe;
import com.afc.ifc.services.impl.EmployeRessource;
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
 * Contrôleur REST pour la gestion des employés
 * 
 * @author Amstrong
 */
@RestController
@RequestMapping("/api/employes")
@Tag(name = "Employés", description = "API de gestion des employés")
@CrossOrigin(origins = "*")
public class EmployeController {

    @Autowired
    private EmployeRessource employeService;

    @GetMapping
    @Operation(summary = "Récupérer tous les employés", 
               description = "Récupère la liste paginée de tous les employés")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<Employe>> getAllEmployes(
            @Parameter(description = "Numéro de page (commence à 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page")
            @RequestParam(defaultValue = "20") int size) {
        
        return employeService.getAllEmployes(page, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un employé par ID", 
               description = "Récupère les détails d'un employé spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employé trouvé"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Employe> getEmployeById(
            @Parameter(description = "ID de l'employé", required = true)
            @PathVariable Long id) {
        
        return employeService.findEmploye(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher un employé par nom", 
               description = "Recherche un employé par son nom")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employé trouvé"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Employe> searchEmploye(
            @Parameter(description = "Nom de l'employé à rechercher", required = true)
            @RequestParam String nom) {
        
        return employeService.searchEmploye(nom);
    }
}
