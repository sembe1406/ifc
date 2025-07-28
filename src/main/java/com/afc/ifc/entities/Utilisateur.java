/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afc.ifc.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Amstrong
 */
@Entity
@Data
public class Utilisateur  implements Serializable {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (nullable = false)
    private String nom;
    
    @Column (nullable = false)
    @Email
    private String email;
    
    @Column (nullable = false)
    private String password;
    
    @Column (nullable = false)
    private Role role;
    
}
