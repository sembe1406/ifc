/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author Amstrong
 */
@Entity
@Data
public class Employe implements Serializable{ 
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;

    
    @Column(nullable = false)
    private String prenom;
    
    @Column(nullable = false)
    private int anciennete;
    
    @Column(nullable = false)
    private double salaire;
    
    @Column (nullable=false)
    private int age;
    
    @ManyToOne
    private Entreprise entreprise;
    
    
}
