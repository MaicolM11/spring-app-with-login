package com.uptc.repo;

import java.util.Optional;

import com.uptc.models.EUserRole;
import com.uptc.models.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional  // use for DML query
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRole(EUserRole role);
}
