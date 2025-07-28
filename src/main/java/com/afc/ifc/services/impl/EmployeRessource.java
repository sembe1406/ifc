/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.services.impl;

import com.afc.ifc.dao.EmployeDao;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.services.IEmployeRessource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Amstrong
 */
@Service
public class EmployeRessource implements IEmployeRessource{
    
    @Autowired
    private EmployeDao employeDao;
     @Override
    public ResponseEntity<Page<Employe>> getAllEmployes(int page, int pagesize) {
        return ResponseEntity.ok(employeDao.findAll(PageRequest.of(page, pagesize)));
    }

    @Override
    public ResponseEntity<Employe> findEmploye(long id) {
        return employeDao.findById(id).map(
                c -> {
                    
                    return ResponseEntity.ok(c);
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Employe> searchEmploye(String nom) {
        return employeDao.findByNomLike(nom).map(
                c -> { 
                    return ResponseEntity.ok(c);
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    
     
    
}
