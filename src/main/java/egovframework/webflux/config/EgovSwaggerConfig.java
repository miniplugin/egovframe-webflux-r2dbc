package egovframework.webflux.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EgovSwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .title("R2DBC(H2) 연동 API 명세서")
                .description("R2DBC(H2) 연동 API 명세서")
                .version("v1")
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.egovframe.go.kr/home/sub.do?menuNo=23"))
                .contact(new Contact()
                        .name("eGovFramework")
                        .email("egovframesupport@gmail.com"));
    }

}
