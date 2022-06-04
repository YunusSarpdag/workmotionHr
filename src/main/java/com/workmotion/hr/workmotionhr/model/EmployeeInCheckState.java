package com.workmotion.hr.workmotionhr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeInCheckState {
    @Id
    private Long employeeId;
    private EmployeeState employeeState1;
    private EmployeeState employeeState2;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeState getEmployeeState1() {
        return employeeState1;
    }

    public void setEmployeeState1(EmployeeState employeeState1) {
        this.employeeState1 = employeeState1;
    }

    public EmployeeState getEmployeeState2() {
        return employeeState2;
    }

    public void setEmployeeState2(EmployeeState employeeState2) {
        this.employeeState2 = employeeState2;
    }
}
