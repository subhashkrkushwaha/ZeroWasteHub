package com.zerowastehub.backend.repository;

import com.zerowastehub.backend.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole ,Integer> {
    Optional<UserRole> findByUserRole(String userRole);

}
