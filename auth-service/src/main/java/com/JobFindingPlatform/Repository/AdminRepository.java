package com.JobFindingPlatform.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JobFindingPlatform.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
	List<Admin> findByAdminId(Long AdminId);
	List<Admin> findByUserId(Long userId);
}
