package com.afc.ifc.services.impl;

import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Norme;
import com.afc.ifc.entities.TypeCalcul;
import com.afc.ifc.services.IfcCalculService;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service de calcul d'indemnités
 * 
 * @author Amstrong
 */
@Service
public class IfcCalculServiceImpl implements IfcCalculService {

    @Override
    public double calculerIndemnite(Employe employe, TypeCalcul typeCalcul) {
        if (employe == null || employe.getEntreprise() == null) {
            throw new IllegalArgumentException("L'employé et son entreprise ne peuvent pas être nuls");
        }
        
        if (typeCalcul == null) {
            throw new IllegalArgumentException("Le type de calcul ne peut pas être nul");
        }
        
        switch (typeCalcul) {
            case INDEMNITE_FIN_CARRIERE:
                return calculerIndemniteFinCarriere(employe);
            case INDEMNITE_LICENCIEMENT:
                return calculerIndemniteLicenciement(employe);
            default:
                throw new UnsupportedOperationException("Type de calcul non supporté: " + typeCalcul);
        }
    }

    @Override
    public double calculerIndemniteFinCarriere(Employe employe) {
        if (employe == null || employe.getEntreprise() == null) {
            throw new IllegalArgumentException("L'employé et son entreprise ne peuvent pas être nuls");
        }
        
        Norme norme = employe.getEntreprise().getNorme();
        double salaire = employe.getSalaire();
        int anciennete = employe.getAnciennete();
        
        switch (norme) {
            case CONVENTIONCOMMERCE:
                return calculerIndemniteFinCarriereConventionCommerce(salaire, anciennete);
            case CODETRAVAIL:
                return calculerIndemniteFinCarriereCodeTravail(salaire, anciennete);
            default:
                throw new UnsupportedOperationException("Norme non supportée: " + norme);
        }
    }

    @Override
    public double calculerIndemniteLicenciement(Employe employe) {
        if (employe == null || employe.getEntreprise() == null) {
            throw new IllegalArgumentException("L'employé et son entreprise ne peuvent pas être nuls");
        }
        
        Norme norme = employe.getEntreprise().getNorme();
        double salaire = employe.getSalaire();
        int anciennete = employe.getAnciennete();
        
        switch (norme) {
            case CONVENTIONCOMMERCE:
                return calculerIndemniteLicenciementConventionCommerce(salaire, anciennete);
            case CODETRAVAIL:
                return calculerIndemniteLicenciementCodeTravail(salaire, anciennete);
            default:
                throw new UnsupportedOperationException("Norme non supportée: " + norme);
        }
    }

    /**
     * Calcule l'indemnité de fin de carrière selon la Convention Commerce
     */
    private double calculerIndemniteFinCarriereConventionCommerce(double salaire, int anciennete) {
        if (salaire <= 0 || anciennete < 0) {
            throw new IllegalArgumentException("Le salaire doit être positif et l'ancienneté non négative");
        }
        
        double indemnite = 0;
        
        if (anciennete >= 1 && anciennete <= 5) {
            indemnite = 0.40 * salaire * anciennete;
        } else if (anciennete >= 6 && anciennete <= 10) {
            indemnite = 0.40 * salaire * 5 + 0.45 * salaire * (anciennete - 5);
        } else if (anciennete >= 11 && anciennete <= 15) {
            indemnite = (0.40 + 0.45) * salaire * 5 + 0.60 * salaire * (anciennete - 10);
        } else if (anciennete >= 16 && anciennete <= 20) {
            indemnite = (0.40 + 0.45 + 0.60) * salaire * 5 + 0.65 * salaire * (anciennete - 15);
        } else if (anciennete > 20) {
            indemnite = (0.40 + 0.45 + 0.60 + 0.65) * salaire * 5 + 0.75 * salaire * (anciennete - 20);
        }
        
        return Math.round(indemnite * 100.0) / 100.0;
    }

    /**
     * Calcule l'indemnité de fin de carrière selon le Code du Travail
     */
    private double calculerIndemniteFinCarriereCodeTravail(double salaire, int anciennete) {
        if (salaire <= 0 || anciennete < 0) {
            throw new IllegalArgumentException("Le salaire doit être positif et l'ancienneté non négative");
        }
        
        double indemnite = 0;
        
        if (anciennete >= 1 && anciennete <= 5) {
            indemnite = 0.25 * salaire * anciennete;
        } else if (anciennete >= 6 && anciennete <= 10) {
            indemnite = 0.25 * salaire * 5 + 0.30 * salaire * (anciennete - 5);
        } else if (anciennete >= 11 && anciennete <= 15) {
            indemnite = (0.25 + 0.30) * salaire * 5 + 0.40 * salaire * (anciennete - 10);
        } else if (anciennete >= 16 && anciennete <= 20) {
            indemnite = (0.25 + 0.30 + 0.40) * salaire * 5 + 0.45 * salaire * (anciennete - 15);
        } else if (anciennete > 20) {
            indemnite = (0.25 + 0.30 + 0.40 + 0.45) * salaire * 5 + 0.50 * salaire * (anciennete - 20);
        }
        
        return Math.round(indemnite * 100.0) / 100.0;
    }

    /**
     * Calcule l'indemnité de licenciement selon la Convention Commerce
     */
    private double calculerIndemniteLicenciementConventionCommerce(double salaire, int anciennete) {
        // TODO: Implémenter selon la réglementation en vigueur
        // Pour l'instant, retourne 0 car les règles de licenciement peuvent être différentes
        return 0.0;
    }

    /**
     * Calcule l'indemnité de licenciement selon le Code du Travail
     */
    private double calculerIndemniteLicenciementCodeTravail(double salaire, int anciennete) {
        // TODO: Implémenter selon la réglementation en vigueur
        // Pour l'instant, retourne 0 car les règles de licenciement peuvent être différentes
        return 0.0;
    }
}
