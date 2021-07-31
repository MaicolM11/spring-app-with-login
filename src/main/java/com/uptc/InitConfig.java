package com.uptc;

import java.util.Arrays;
import java.util.Collections;

import com.uptc.models.EUserRole;
import com.uptc.models.entities.Role;
import com.uptc.models.entities.User;
import com.uptc.repo.RoleRepository;
import com.uptc.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitConfig implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
	public void run(String... args) throws Exception {
		Arrays.asList(EUserRole.values()).stream()
				.map(Role::new)
				.forEach(roleRepository::save);

		User u = new User("maicol", "mmoarias4@gmail.com", encoder.encode("pass"));
		Role r = roleRepository.findByRole(EUserRole.ADMIN).get();
		u.setEnabled(true);
		u.setRoles(Collections.singletonList(r));
		userRepository.save(u);
	}

}
