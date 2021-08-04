package com.uptc;

import java.util.Arrays;

import com.uptc.models.EUserRole;
import com.uptc.models.entities.Role;
import com.uptc.models.entities.User;
import com.uptc.repo.RoleRepository;
import com.uptc.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CrudApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Arrays.stream(EUserRole.values()).map(Role::new).forEach(roleRepository::save);

		User u = new User("maicol", "mmoarias4@gmail.com", encoder.encode("pass11"));
		Role r = roleRepository.findByRole(EUserRole.ADMIN).get();
		u.setEnabled(true);
		u.setRoles(Arrays.asList(r));
		userRepository.save(u);
		log.info("App in running on http://127.0.0.1:3000/");
	}

}