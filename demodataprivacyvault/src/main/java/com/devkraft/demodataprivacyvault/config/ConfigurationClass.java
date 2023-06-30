package com.devkraft.demodataprivacyvault.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationClass {

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }

}
