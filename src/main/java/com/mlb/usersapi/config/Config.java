package com.mlb.usersapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mlb.usersapi.adapters.outbound.FindAllUsersAdapter;
import com.mlb.usersapi.adapters.outbound.FindByIdUserAdapter;
import com.mlb.usersapi.adapters.outbound.DeleteUserAdapter;
import com.mlb.usersapi.adapters.outbound.SaveUserAdapter;
import com.mlb.usersapi.adapters.outbound.UpdateUserAdapter;
import com.mlb.usersapi.application.core.services.FindAllUsersService;
import com.mlb.usersapi.application.core.services.FindByIdUserService;
import com.mlb.usersapi.application.core.services.DeleteUserService;
import com.mlb.usersapi.application.core.services.SaveUserService;
import com.mlb.usersapi.application.core.services.UpdateUserService;

@Configuration
public class Config {

    @Bean
	public SaveUserService saveUserService(SaveUserAdapter saveUserAdapter){
		return new SaveUserService(saveUserAdapter);
	}

	@Bean
	public FindAllUsersService findAllUsersService(FindAllUsersAdapter findAllUsersAdapter){
		return new FindAllUsersService(findAllUsersAdapter);
	}

	@Bean
	public FindByIdUserService findByIdUserService(FindByIdUserAdapter findByIdUserAdapter){
		return new FindByIdUserService(findByIdUserAdapter);
	}

	@Bean
	public DeleteUserService deleteUserService(DeleteUserAdapter deleteUserAdapter){
		return new DeleteUserService(deleteUserAdapter);
	}

	@Bean
	public UpdateUserService updateUserService(UpdateUserAdapter updateUserAdapter){
		return new UpdateUserService(updateUserAdapter);
	}
}