/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;

/**
 *
 * @author Amstrong
 */

@Entity
@Data
public class Entreprise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String adresse;
    
    @Column(nullable = false)
    private String telephone;
    
    @Column(nullable = false)
    private Norme norme;
    
    @Column (nullable = false)
    private String secteur;
    
    @JsonIgnore
    @XmlTransient
     @OneToMany(mappedBy = "entreprise")
     private List<Employe> employe;
    
    @JsonIgnore
    @XmlTransient
    @OneToMany(mappedBy = "entreprise")
    private List<Dossier> dossier;
    
}
