package com.afc.ifc.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration OpenAPI/Swagger pour la documentation de l'API
 * 
 * @author Amstrong
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiDocumentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Calcul des Indemnités de Fin de Contrat (IFC)")
                        .description("Cette API permet de gérer les employés, entreprises et calculer les indemnités de fin de carrière et de licenciement selon différentes normes (Convention Commerce, Code du Travail, etc.)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Équipe AFC")
                                .url("https://afc.com")
                                .email("contact@afc.com"))
                        .license(new License()
                                .name("Licence Propriétaire")
                                .url("https://afc.com/licence")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation complète")
                        .url("https://afc.com/docs"));
    }
}
