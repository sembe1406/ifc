/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.dao;


import com.afc.ifc.entities.Entreprise;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Amstrong
 */
public interface EntrepriseDao extends JpaRepository<Entreprise, Long> {
    
    
      public List<Entreprise> findByNomLike(String nom);
      public Page<Entreprise> findByNomLikeIgnoreCase(String string, PageRequest of);
      //public Entreprise findById(long id);

    
}
