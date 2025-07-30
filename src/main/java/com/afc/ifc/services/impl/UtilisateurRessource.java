/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.services.impl;

import com.afc.ifc.dao.UtilisateurDao;
import com.afc.ifc.entities.Utilisateur;
import com.afc.ifc.services.IUtilisateurRessource;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Amstrong
 */
@Service
public class UtilisateurRessource implements IUtilisateurRessource {
    
    @Autowired
    private UtilisateurDao utilisateurDao;
    
    @Override
    public ResponseEntity<Page<Utilisateur>> findAllUtilisateurs(int page, int size) {
        return ResponseEntity.ok(utilisateurDao.findAll(PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<Utilisateur> addUtilisateur(Utilisateur user) {
        Utilisateur u = utilisateurDao.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(u.getId())
                .toUri();
        return ResponseEntity.created(location).body(u);
    }

    @Override
    public ResponseEntity<Utilisateur> findUtilisateur(long id) {
        return utilisateurDao.findById(id).map(
                u -> {
                    return ResponseEntity.ok(u);
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Utilisateur> updateUtilisateur(long id, Utilisateur user) {
        return utilisateurDao.findById(id).map(
                u -> {
                    u.setEmail(user.getEmail());
                    u.setNom(user.getNom());
                    u.setPassword(user.getPassword());
                    u.setRole(user.getRole());
                    return ResponseEntity.ok(utilisateurDao.save(u));
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public void deleteUtilisateur(long id) {
        utilisateurDao.deleteById(id);
    }
}
