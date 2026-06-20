package com.JobFindingPlatform.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JobFindingPlatform.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User>findByUserEmail(String userEmail);
}
