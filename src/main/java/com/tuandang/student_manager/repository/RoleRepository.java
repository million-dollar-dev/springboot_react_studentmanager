package com.tuandang.student_manager.repository;

import com.tuandang.student_manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select r from Role r inner join UserHasRole ur where r.id = ur.role.id")
    List<Role> findAll();
}
