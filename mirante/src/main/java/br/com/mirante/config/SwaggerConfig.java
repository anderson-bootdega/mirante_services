package br.com.mirante.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Value("${project.version}")
    private String projectVersion;
    
    @Value("${project.description}")
    private String projectDescription;
    
    @Value("${project.name}")
    private String projectName;
    
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(projectName)
                        .description(projectDescription)
                        .version(projectVersion));
    }
    
}
