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
public class Parametrage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom_norme;
    
    @Column(nullable = false)
    private double taux_progression ;
    
    @Column(nullable = false)
    private double taux_actualisation ;
    
    @Column(nullable = false)
    private double taux_mortalite ;

    @Column(nullable = false)
    private double taux_turnover ;
    
    @Column(nullable = false)
    private String pays;
    
    
}
