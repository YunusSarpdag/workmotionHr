package com.workmotion.hr.workmotionhr;

import com.workmotion.hr.workmotionhr.controller.EmployeeController;
import com.workmotion.hr.workmotionhr.model.Employee;
import com.workmotion.hr.workmotionhr.model.EmployeeState;
import com.workmotion.hr.workmotionhr.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
class WorkmotionHrApplicationTests {

    @Autowired
    EmployeeController controller;

    @Autowired
    EmployeeRepository repository;

    @Test
    void TestSuccessStateUpdate() {
        Optional<Employee> employeeOptional = repository.findById(56L);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setState(EmployeeState.IN_CHECK);
            ResponseEntity<String> employeeResponseEntity = controller.saveEmployee(employee);
            assert(HttpStatus.OK.equals(employeeResponseEntity.getStatusCode()));
        }
    }

    @Test
    void TestFailStateUpdate() {
        Optional<Employee> employeeOptional = repository.findById(56L);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setState(EmployeeState.ADDED);
            ResponseEntity<String> employeeResponseEntity= controller.saveEmployee(employee);
            assert(HttpStatus.BAD_REQUEST.equals(employeeResponseEntity.getStatusCode()));
        }
    }

}
