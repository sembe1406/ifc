/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.services.impl;

import com.afc.ifc.dao.EmployeDao;
import com.afc.ifc.dao.EntrepriseDao;
import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Entreprise;
import com.afc.ifc.entities.Statut;
import com.afc.ifc.services.DossierService;
import com.afc.ifc.services.IEntrepriseRessource;
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Amstrong
 */
@Service
@Transactional
public class EntrepriseRessource implements IEntrepriseRessource {

    @Autowired
    private EntrepriseDao entrepriseDao;

    @Autowired
    private EmployeDao employeDao;
    
    @Autowired
    private DossierService dossierService;
    

    @Override
    public Page<Entreprise> getAllEntreprises(int page, int pagesize) {
        return entrepriseDao.findAll(PageRequest.of(page, pagesize));
    }

    @Override
    public Page<Entreprise> searchEntreprises(String nom, int page, int pagesize) {
        return entrepriseDao.findByNomLikeIgnoreCase("%" + nom + "%", PageRequest.of(page, pagesize));
    }

    @Override
    public ResponseEntity<Entreprise> findEntreprise(long id) {
        Optional<Entreprise> entreprise = entrepriseDao.findById(id);
        if (entreprise.isPresent()) {
            return ResponseEntity.ok(entreprise.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Entreprise> updateEntreprise(long id, Entreprise entreprise) {

        return entrepriseDao.findById(id).map(
                c -> {
                    c.setAdresse(entreprise.getAdresse());
                    c.setNom(entreprise.getNom());
                    c.setNorme(entreprise.getNorme());
                    c.setSecteur(entreprise.getSecteur());
                    c.setTelephone(entreprise.getTelephone());
                    return ResponseEntity.ok(entrepriseDao.save(c));
                }
        ).orElse(
                ResponseEntity.notFound().build()
        );
    }

    @Override
    public ResponseEntity<Entreprise> addEntreprise(Entreprise entreprise) {
        Entreprise e = entrepriseDao.save(entreprise);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(e.getId())
                .toUri();
        return ResponseEntity.created(location).body(e);
    }

    @Override
    public void deleteEntreprise(long id) {
        entrepriseDao.deleteById(id);
    }

    @Override
    public ResponseEntity<List<Employe>> findEmployeEntreprise(long idEntreprise) {
        return entrepriseDao.findById(idEntreprise).map(
                c -> {
                    return ResponseEntity.ok(employeDao.findByEntreprise(c));
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Employe> ajouterEmployeEntreprise(long idEntreprise, Employe employe) {
        if (employe == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return entrepriseDao.findById(idEntreprise)
            .map(entreprise -> {
                // Associer l'employé à l'entreprise
                employe.setEntreprise(entreprise);
                Employe employeSauvegarde = employeDao.save(employe);
                
                // Créer le dossier associé (par défaut: indemnité de fin de carrière)
                dossierService.creerDossier(employeSauvegarde, entreprise, com.afc.ifc.entities.TypeCalcul.INDEMNITE_FIN_CARRIERE);
                
                // Créer l'URI de location
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(employeSauvegarde.getId())
                        .toUri();
                        
                return ResponseEntity.created(location).body(employeSauvegarde);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Employe> modifierEmployeEntreprise(long idEntreprise, long idEmploye, Employe employe) {
        return employeDao.findByIdAndEntrepriseId(idEntreprise, idEmploye).map(
                c -> {
                    c.setNom(employe.getNom());
                    c.setPrenom(employe.getPrenom());
                    c.setAnciennete(employe.getAnciennete());
                    c.setAge(employe.getAge());
                    c.setSalaire(employe.getSalaire());
                    employeDao.save(c);
                    return ResponseEntity.ok(c);
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public void supprimerEmployeEntreprise(long idEntreprise, long idEmploye) {
        employeDao.findByIdAndEntrepriseId(idEmploye, idEntreprise).map(
                c -> {
                    employeDao.delete(c);
                    return 1;
                }
        );
    }

    @Override
    public ResponseEntity<Employe> renvoyerEmployeEntreprise(long idEmploye, long idEntreprise) {
        return employeDao.findByIdAndEntrepriseId(idEmploye, idEntreprise).map(
                c -> {
                    return ResponseEntity.ok(c);
                }
        ).orElse(ResponseEntity.notFound().build());
    }

     @Override
    public List<Employe> CalculIfcEmployeEntreprise(long idEntreprise) {
        // TODO: Cette méthode devrait retourner une liste d'employés avec leurs IFC calculés
        // Pour l'instant, elle retourne simplement la liste des employés
        // Dans une version future, on pourrait créer un DTO avec les informations IFC
        return employeDao.findByEntrepriseId(idEntreprise);
    }
    
     @Override
    public ResponseEntity<Dossier> CalculIfconeDossierEntreprise(long idEntreprise, long idEmploye) {
        try {
            // Vérifier que l'employé existe et appartient à cette entreprise
            Optional<Employe> employeOpt = employeDao.findByIdAndEntrepriseId(idEmploye, idEntreprise);
            if (!employeOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            // Trouver le dossier associé
            Optional<Dossier> dossierOpt = dossierService.trouverDossierParEmployeEtEntreprise(idEmploye, idEntreprise);
            if (!dossierOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            // Calculer l'indemnité en utilisant notre service dédié
            Optional<Dossier> dossierCalcule = dossierService.calculerIndemniteDossier(dossierOpt.get().getId());
            
            return dossierCalcule
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
                
        } catch (Exception e) {
            // Log l'erreur (TODO: ajouter un logger)
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @Override
    public ResponseEntity<Dossier> calculerIndemniteDossier(long idEntreprise, long idEmploye, String typeCalcul) {
        try {
            // Vérifier que l'employé existe et appartient à cette entreprise
            Optional<Employe> employeOpt = employeDao.findByIdAndEntrepriseId(idEmploye, idEntreprise);
            if (!employeOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            // Convertir le type de calcul
            com.afc.ifc.entities.TypeCalcul type;
            try {
                type = com.afc.ifc.entities.TypeCalcul.valueOf(typeCalcul.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
            
            // Trouver le dossier associé
            Optional<Dossier> dossierOpt = dossierService.trouverDossierParEmployeEtEntreprise(idEmploye, idEntreprise);
            if (!dossierOpt.isPresent()) {
                // Créer un nouveau dossier si nécessaire
                Employe employe = employeOpt.get();
                Dossier nouveauDossier = dossierService.creerDossier(employe, employe.getEntreprise(), type);
                dossierOpt = Optional.of(nouveauDossier);
            } else {
                // Mettre à jour le type de calcul
                Dossier dossier = dossierOpt.get();
                dossier.setTypeCalcul(type);
            }
            
            // Calculer l'indemnité
            Optional<Dossier> dossierCalcule = dossierService.calculerIndemniteDossier(dossierOpt.get().getId());
            
            return dossierCalcule
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
                
        } catch (Exception e) {
            // Log l'erreur (TODO: ajouter un logger)
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<Dossier>> findDossierEntreprise(long idEntreprise) {
        try {
            List<Dossier> dossiers = dossierService.trouverDossiersParEntreprise(idEntreprise);
            return ResponseEntity.ok(dossiers);
        } catch (UnsupportedOperationException e) {
            // TODO: Implémenter cette méthode dans DossierService
            return ResponseEntity.status(501).build(); // Not Implemented
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Dossier> modifierStatutDossierEntreprise(long idEntreprise, long idDossier, Dossier dossier) {
        if (dossier == null || dossier.getStatut() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Optional<Dossier> dossierMisAJour = dossierService.mettreAJourStatutDossier(idDossier, dossier.getStatut());
            return dossierMisAJour
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public void supprimerDossierEntreprise(long idDossier, long idEntreprise) {
        try {
            // Marquer le dossier comme supprimé plutôt que le supprimer physiquement
            dossierService.mettreAJourStatutDossier(idDossier, Statut.SUP);
        } catch (Exception e) {
            // Log l'erreur (TODO: ajouter un logger)
        }
    }

    @Override
    public ResponseEntity<Dossier> renvoyerDossierEntreprise(long idEntreprise, long idDossier) {
        // TODO: Implémenter une méthode dans DossierService pour trouver un dossier par ID et entreprise
        return ResponseEntity.status(501).build(); // Not Implemented
    }

}
