package com.afc.ifc.controllers;

import com.afc.ifc.entities.Entreprise;
import com.afc.ifc.entities.Employe;
import com.afc.ifc.entities.Norme;
import com.afc.ifc.entities.TypeCalcul;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests d'intégration pour EntrepriseController
 * 
 * @author Amstrong
 */
@SpringBootTest
@AutoConfigureWebMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class EntrepriseControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateEntreprise() throws Exception {
        Entreprise entreprise = new Entreprise();
        entreprise.setNom("Test Entreprise");
        entreprise.setAdresse("123 Rue Test");
        entreprise.setTelephone("123456789");
        entreprise.setNorme(Norme.CONVENTIONCOMMERCE);
        entreprise.setSecteur("IT");

        mockMvc.perform(post("/api/entreprises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entreprise)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom", is("Test Entreprise")))
                .andExpect(jsonPath("$.norme", is("CONVENTIONCOMMERCE")));
    }

    @Test
    void testGetAllEntreprises() throws Exception {
        // Créer d'abord une entreprise
        createTestEntreprise();

        mockMvc.perform(get("/api/entreprises")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    void testSearchEntreprises() throws Exception {
        // Créer d'abord une entreprise
        createTestEntreprise();

        mockMvc.perform(get("/api/entreprises/search")
                .param("nom", "Test")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEntrepriseById() throws Exception {
        // Créer d'abord une entreprise et récupérer son ID
        Long entrepriseId = createTestEntreprise();

        mockMvc.perform(get("/api/entreprises/{id}", entrepriseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Test Entreprise")));
    }

    @Test
    void testGetEntrepriseById_NotFound() throws Exception {
        mockMvc.perform(get("/api/entreprises/{id}", 99999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateEntreprise() throws Exception {
        // Créer d'abord une entreprise
        Long entrepriseId = createTestEntreprise();

        Entreprise updatedEntreprise = new Entreprise();
        updatedEntreprise.setNom("Test Entreprise Modifiée");
        updatedEntreprise.setAdresse("456 Nouvelle Rue");
        updatedEntreprise.setTelephone("987654321");
        updatedEntreprise.setNorme(Norme.CODETRAVAIL);
        updatedEntreprise.setSecteur("Finance");

        mockMvc.perform(put("/api/entreprises/{id}", entrepriseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEntreprise)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Test Entreprise Modifiée")));
    }

    @Test
    void testAddEmployeToEntreprise() throws Exception {
        // Créer d'abord une entreprise
        Long entrepriseId = createTestEntreprise();

        Employe employe = new Employe();
        employe.setNom("Dupont");
        employe.setPrenom("Jean");
        employe.setSalaire(1000000.0);
        employe.setAnciennete(5);
        employe.setAge(30);

        mockMvc.perform(post("/api/entreprises/{id}/employes", entrepriseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom", is("Dupont")))
                .andExpect(jsonPath("$.prenom", is("Jean")));
    }

    @Test
    void testGetEmployesEntreprise() throws Exception {
        // Créer une entreprise et un employé
        Long entrepriseId = createTestEntreprise();
        createTestEmploye(entrepriseId);

        mockMvc.perform(get("/api/entreprises/{id}/employes", entrepriseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testCalculerIndemnite() throws Exception {
        // Créer une entreprise et un employé
        Long entrepriseId = createTestEntreprise();
        Long employeId = createTestEmploye(entrepriseId);

        mockMvc.perform(post("/api/entreprises/{id}/employes/{idEmploye}/calcul-indemnite", entrepriseId, employeId)
                .param("typeCalcul", TypeCalcul.INDEMNITE_FIN_CARRIERE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montantIndemnite", greaterThan(0.0)))
                .andExpect(jsonPath("$.typeCalcul", is("INDEMNITE_FIN_CARRIERE")));
    }

    @Test
    void testCalculerIndemnite_EmployeNotFound() throws Exception {
        Long entrepriseId = createTestEntreprise();

        mockMvc.perform(post("/api/entreprises/{id}/employes/{idEmploye}/calcul-indemnite", entrepriseId, 99999L)
                .param("typeCalcul", TypeCalcul.INDEMNITE_FIN_CARRIERE.name()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEntreprise() throws Exception {
        Long entrepriseId = createTestEntreprise();

        mockMvc.perform(delete("/api/entreprises/{id}", entrepriseId))
                .andExpect(status().isNoContent());

        // Vérifier que l'entreprise n'existe plus
        mockMvc.perform(get("/api/entreprises/{id}", entrepriseId))
                .andExpect(status().isNotFound());
    }

    // === MÉTHODES UTILITAIRES ===

    private Long createTestEntreprise() throws Exception {
        Entreprise entreprise = new Entreprise();
        entreprise.setNom("Test Entreprise");
        entreprise.setAdresse("123 Rue Test");
        entreprise.setTelephone("123456789");
        entreprise.setNorme(Norme.CONVENTIONCOMMERCE);
        entreprise.setSecteur("IT");

        String response = mockMvc.perform(post("/api/entreprises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entreprise)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Entreprise createdEntreprise = objectMapper.readValue(response, Entreprise.class);
        return createdEntreprise.getId();
    }

    private Long createTestEmploye(Long entrepriseId) throws Exception {
        Employe employe = new Employe();
        employe.setNom("Dupont");
        employe.setPrenom("Jean");
        employe.setSalaire(1000000.0);
        employe.setAnciennete(5);
        employe.setAge(30);

        String response = mockMvc.perform(post("/api/entreprises/{id}/employes", entrepriseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employe)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Employe createdEmploye = objectMapper.readValue(response, Employe.class);
        return createdEmploye.getId();
    }
}
