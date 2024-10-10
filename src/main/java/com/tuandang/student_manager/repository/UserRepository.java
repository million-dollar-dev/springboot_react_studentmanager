package com.tuandang.student_manager.repository;

import com.tuandang.student_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    @Query(value = "select r.name from Role r inner join UserHasRole ur on r.id = ur.id where ur.id= :username",
            nativeQuery = true)
    List<String> findAllRolesByUsername(String username);
}
