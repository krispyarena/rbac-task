package org.demo.amniltask.repository;

import org.demo.amniltask.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    public Admin findByEmail(String email);

    @Query("SELECT a from Admin a where a.phone = :phone")
    public Admin findByPhone(@Param("phone") String phone);
}
