package com.andrzej.bookDatabase.Repository;

import com.andrzej.bookDatabase.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
    List<Role> findAll();
}
