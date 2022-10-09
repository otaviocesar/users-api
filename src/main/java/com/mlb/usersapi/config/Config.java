package com.mlb.usersapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mlb.usersapi.adapters.outbound.SaveUserAdapter;
import com.mlb.usersapi.application.core.services.SaveUserService;

@Configuration
public class Config {

    @Bean
	public SaveUserService saveUserService(SaveUserAdapter saveUserAdapter){
		return new SaveUserService(saveUserAdapter);
	}
}