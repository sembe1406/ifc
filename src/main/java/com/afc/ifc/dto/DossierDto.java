/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.dto;
import com.afc.ifc.entities.Entreprise;
import com.afc.ifc.entities.Statut;
import lombok.Data;
/**
 *
 * @author MENGA BAMO Emmanuel
 */
@Data
public class DossierDto {
    private int anciennete;
    private int age;
    private String nom;
    private String prenom;
    private double salaire;
    private double indemniteFinCarierre;
    private double indemniteLicenciement;
    private Statut statut;
    private Entreprise entreprise ;
    
}
