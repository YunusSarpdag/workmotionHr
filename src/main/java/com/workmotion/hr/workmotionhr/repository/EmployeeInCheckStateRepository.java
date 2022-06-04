package com.workmotion.hr.workmotionhr.repository;

import com.workmotion.hr.workmotionhr.model.EmployeeInCheckState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInCheckStateRepository extends JpaRepository<EmployeeInCheckState,Long> {
    EmployeeInCheckState getByEmployeeId(Long employeeId);
}
