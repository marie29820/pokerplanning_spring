package fr.pokerplanning.config.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonFactory {

    @Bean
    com.google.gson.Gson get(){
        return new com.google.gson.Gson();
    }
}
