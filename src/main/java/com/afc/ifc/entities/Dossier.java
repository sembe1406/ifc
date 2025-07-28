package com.afc.ifc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Entity
@Data
public class Dossier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable=false)
    private Statut statut;
    
    @Column (nullable=false)
    private TypeCalcul typeCalcul;
    
    @Column (nullable=false)
    private double montantIndemnite;
    
    @JsonIgnore
    @XmlTransient
    @OneToOne
    private Employe employe ;
    
    @JsonIgnore
    @XmlTransient
    @ManyToOne
    private Entreprise entreprise;

    
    
}
