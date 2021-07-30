package com.uptc;

import java.util.Arrays;

import com.uptc.models.EUserRole;
import com.uptc.models.entities.Role;
import com.uptc.repo.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Arrays.asList(EUserRole.values()).stream()
				.map(Role::new)
				.forEach(roleRepository::save);
	}

}