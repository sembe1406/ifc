package com.afc.ifc.dao;

import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Entreprise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amstrong
 */
public interface EmployeDao extends JpaRepository<Employe, Long>{
    
    public Optional<Employe> findByNomLike(String nom);
    public Page<Employe> findByNomLikeIgnoreCase(String nom, Pageable page);
    public List<Employe> findByPrenomLike(String prenom);
    public Page<Employe> findByPrenomLikeIgnoreCase(String prenom, Pageable page);
    public List<Employe> findByEntreprise(Entreprise entreprise);
    public List<Employe> findByEntrepriseNom(String nom);
    public Optional<Employe> findByIdAndEntrepriseId(long idEmploye, long idEntreprise);

    public List<Employe> findByEntrepriseId(long idEntreprise);

    
}
