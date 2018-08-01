package net.honeyflower.lecturing.rest.swagger.swagger;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "Awesome Resources",
                version = "V0.0.1",
                title = "Awesome Resource API",
                contact = @Contact(
                   name = "Eugene Hanikblum", 
                   email = "my-email@supercorp.com", 
                   url = "http://honeyflower.net"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "Read This For Sure", url = "http://honeyflower.net")
)
public interface ApiDocumentationConfig {

}