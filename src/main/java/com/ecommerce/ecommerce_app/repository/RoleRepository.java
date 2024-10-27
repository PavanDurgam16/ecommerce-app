package com.ecommerce.ecommerce_app.repository;

import com.ecommerce.ecommerce_app.enums.RoleType;
import com.ecommerce.ecommerce_app.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleType name);
}
