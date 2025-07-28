/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.dao;

import com.afc.ifc.entities.Role;
import com.afc.ifc.entities.Utilisateur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Amstrong
 */
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long>{
    public List<Utilisateur> findByNomLike(String nom);
    public Optional<Utilisateur> findByEmail(String email);
    public List<Utilisateur> findByRole (Role role);

    
}
