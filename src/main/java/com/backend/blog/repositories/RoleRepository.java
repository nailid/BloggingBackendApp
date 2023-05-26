package com.backend.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
