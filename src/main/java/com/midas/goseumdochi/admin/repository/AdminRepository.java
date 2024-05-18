package com.midas.goseumdochi.admin.repository;

import com.midas.goseumdochi.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findByname(String name);
}
