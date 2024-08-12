package com.ali.employees.repositories;

import com.ali.employees.domain.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // we dont really have to, bcs JpaRepository already did that annotation for us
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
}
