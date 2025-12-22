package com.nt.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nt.hms.Entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{

}
