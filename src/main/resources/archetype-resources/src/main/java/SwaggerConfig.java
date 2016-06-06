package ${package};

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration for API documentation.
 * 
 * @author ${author}
 * @since 1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Value("${swagger.info.version}")
    private String version;
    @Value("${swagger.info.title}")
    private String title;
    @Value("${swagger.info.description}")
    private String description;
    @Value("${swagger.info.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger.info.contact}")
    private String contact;
    @Value("${swagger.info.license}")
    private String license;
    @Value("${swagger.info.licenseUrl}")
    private String licenseUrl;

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.ant("/${domainName.toLowerCase()}s/**"))
		.build()
		.apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
	ApiInfo apiInfo = new ApiInfo(
		title,
		description,
		version,
		termsOfServiceUrl,
		contact,
		license,
		licenseUrl);
	return apiInfo;
    }


}
