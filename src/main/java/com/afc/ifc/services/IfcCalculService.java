package com.afc.ifc.services;

import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.TypeCalcul;

/**
 * Service pour les calculs d'indemnités
 * 
 * @author Amstrong
 */
public interface IfcCalculService {
    
    /**
     * Calcule une indemnité selon le type spécifié
     * 
     * @param employe L'employé pour lequel calculer l'indemnité
     * @param typeCalcul Le type d'indemnité à calculer
     * @return Le montant de l'indemnité calculée
     */
    double calculerIndemnite(Employe employe, TypeCalcul typeCalcul);
    
    /**
     * Calcule l'indemnité de fin de carrière selon la norme applicable
     * 
     * @param employe L'employé pour lequel calculer l'indemnité
     * @return Le montant de l'indemnité de fin de carrière
     */
    double calculerIndemniteFinCarriere(Employe employe);
    
    /**
     * Calcule l'indemnité de licenciement selon la norme applicable
     * 
     * @param employe L'employé pour lequel calculer l'indemnité
     * @return Le montant de l'indemnité de licenciement
     */
    double calculerIndemniteLicenciement(Employe employe);
}
