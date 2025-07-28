package com.afc.ifc.services.impl;

import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Entreprise;
import com.afc.ifc.entities.Norme;
import com.afc.ifc.entities.TypeCalcul;
import com.afc.ifc.services.IfcCalculService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le service de calcul IFC
 * 
 * @author Amstrong
 */
@SpringBootTest
public class IfcCalculServiceTest {

    @Autowired
    private IfcCalculService ifcCalculService;

    private Employe employe;
    private Entreprise entreprise;

    @BeforeEach
    void setUp() {
        entreprise = new Entreprise();
        entreprise.setNom("Test Entreprise");
        entreprise.setNorme(Norme.CONVENTIONCOMMERCE);

        employe = new Employe();
        employe.setNom("Test");
        employe.setPrenom("Employe");
        employe.setSalaire(1000000.0); // 1 million FCFA
        employe.setAnciennete(5);
        employe.setAge(30);
        employe.setEntreprise(entreprise);
    }

    @Test
    void testCalculerIndemniteFinCarriereConventionCommerce() {
        // Test pour 5 ans d'ancienneté avec Convention Commerce
        double indemnite = ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        
        // 0.40 * 1,000,000 * 5 = 2,000,000
        assertEquals(2000000.0, indemnite, 0.01);
    }

    @Test
    void testCalculerIndemniteFinCarriereCodeTravail() {
        // Test pour 5 ans d'ancienneté avec Code du Travail
        entreprise.setNorme(Norme.CODETRAVAIL);
        double indemnite = ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        
        // 0.25 * 1,000,000 * 5 = 1,250,000
        assertEquals(1250000.0, indemnite, 0.01);
    }

    @Test
    void testCalculerIndemniteLicenciementConventionCommerce() {
        // Test pour 5 ans d'ancienneté avec Convention Commerce
        double indemnite = ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_LICENCIEMENT);
        
        // 0.40 * 1,000,000 * 5 = 2,000,000  
        assertEquals(2000000.0, indemnite, 0.01);
    }

    @Test
    void testCalculerIndemniteFinCarriere10Ans() {
        // Test pour 10 ans d'ancienneté avec Convention Commerce
        employe.setAnciennete(10);
        double indemnite = ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        
        // (0.40 * 1,000,000 * 5) + (0.45 * 1,000,000 * 5) = 4,250,000
        assertEquals(4250000.0, indemnite, 0.01);
    }

    @Test
    void testCalculerIndemniteEmployeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ifcCalculService.calculerIndemnite(null, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        });
    }

    @Test
    void testCalculerIndemniteTypeCalculNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ifcCalculService.calculerIndemnite(employe, null);
        });
    }

    @Test
    void testCalculerIndemniteEntrepriseNull() {
        employe.setEntreprise(null);
        assertThrows(IllegalArgumentException.class, () -> {
            ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        });
    }

    @Test
    void testCalculerIndemniteSalaireNegatif() {
        employe.setSalaire(-1000.0);
        assertThrows(IllegalArgumentException.class, () -> {
            ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        });
    }

    @Test
    void testCalculerIndemniteAncienneteNegative() {
        employe.setAnciennete(-1);
        assertThrows(IllegalArgumentException.class, () -> {
            ifcCalculService.calculerIndemnite(employe, TypeCalcul.INDEMNITE_FIN_CARRIERE);
        });
    }
}
