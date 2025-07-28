/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.dao;

import com.afc.ifc.dto.DossierDto;
import com.afc.ifc.entities.Dossier;
import com.afc.ifc.entities.Entreprise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MENGA BAMO Emmanuel
 */
public interface DossierDao extends JpaRepository<Dossier, Long> {

    
   

    public void save(DossierDto b);

    public Optional<Dossier> findByEmployeIdAndEntrepriseId(long idEmploye, long idEntreprise);

    
    
}
