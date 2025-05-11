package com.altice.infra.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(
        title = "Labseq API",
        version = "1.0.0",
        description = "API para cálculo da sequência matemática labseq",
        contact = @Contact(
            name = "Suporte",
            email = "suporte@altice.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    tags = {
        @Tag(name = "sequence", description = "Operations related to the calculation of the labseq sequence")
    }
)
public class OpenApiConfig extends Application {
    // Configuration is done through annotations
}