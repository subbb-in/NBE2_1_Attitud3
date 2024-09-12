package edu.example.dev_coffee2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "gc-coffee",
                version = "ver 0.1",
                description = "3팀 커피 CRUD 프로젝트 "
        ),
        servers ={
                @Server(
                        description = "CoffeeServer",
                        url = "http://localhost:8080/"
                )
        }

)
public class swaggerConfig {

}