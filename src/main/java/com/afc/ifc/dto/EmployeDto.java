/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.dto;

import lombok.Data;

/**
 *
 * @author SEMBE
 */
@Data
public class EmployeDto {
    private int anciennete;
    private int age;
    private String nom;
    private String prenom;
    private double salaire;
    private double indemniteFinCarierre;
    private double indemniteLicenciement;
    
    
    
}
