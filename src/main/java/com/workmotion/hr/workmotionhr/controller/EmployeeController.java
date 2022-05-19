package com.workmotion.hr.workmotionhr.controller;

import com.workmotion.hr.workmotionhr.model.Employee;
import com.workmotion.hr.workmotionhr.model.EmployeeState;
import com.workmotion.hr.workmotionhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    @GetMapping
    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable(value = "id") Long id) {
         Optional<Employee> employee = repository.findById(id);
         if(employee.isPresent()){
             return employee.get();
         }

         throw new RuntimeException("employee not found");
    }

    @PostMapping
    public ResponseEntity<String> saveEmployee(@Validated @RequestBody Employee employee) {
         Optional<Employee> e = repository.findById(employee.getId());
         if(!e.isPresent()){
             employee.setState(EmployeeState.ADDED);
             Employee addedEmployee =repository.save(employee);
             return new ResponseEntity<>(
                     "OK  " + employee.getId(),
                     HttpStatus.OK);
         }else{
             Employee e2 = e.get();
             if(!e2.getState().isNextStateValid(employee.getState())){
                 return new ResponseEntity<>(
                         "State Error",
                         HttpStatus.BAD_REQUEST);
             }
             repository.save(employee);
             return new ResponseEntity<>(
                     "OK  " + employee.getId(),
                     HttpStatus.OK);
         }

    }

    @DeleteMapping("/deleteEmployee/{id}")
    public Employee deleteEmployee(@PathVariable(value = "id") Long id){
        Optional<Employee> employee= repository.findById(id);
        if(employee.isPresent()){
            repository.delete(employee.get());
            return employee.get();
        }
       throw new RuntimeException("employee not exit");
    }

}
