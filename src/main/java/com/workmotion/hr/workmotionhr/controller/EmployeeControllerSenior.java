package com.workmotion.hr.workmotionhr.controller;

import com.workmotion.hr.workmotionhr.model.Employee;
import com.workmotion.hr.workmotionhr.model.EmployeeInCheckState;
import com.workmotion.hr.workmotionhr.model.EmployeeState;
import com.workmotion.hr.workmotionhr.repository.EmployeeInCheckStateRepository;
import com.workmotion.hr.workmotionhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employeeSenior")
public class EmployeeControllerSenior {
    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeeInCheckStateRepository checkStateRepository;

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

            if(employee.getState().equals(EmployeeState.ADDED)){
                repository.save(employee);
            }

            else if(employee.getState().equals(EmployeeState.IN_CHECK)){
                if(!e2.getState().equals(EmployeeState.ADDED)){
                    throw new RuntimeException("Security State is not ADDED");
                }
                EmployeeInCheckState checkState = new EmployeeInCheckState();
                checkState.setEmployeeId(e2.getId());
                checkState.setEmployeeState1(EmployeeState.SECURITY_CHECK_STARTED);
                checkState.setEmployeeState2(EmployeeState.WORK_PERMIT_CHECK_STARTED);
                checkStateRepository.save(checkState);
                e2.setState(employee.getState());
                repository.save(e2);
            }

            else if(employee.getState().equals(EmployeeState.SECURITY_CHECK_STARTED)){
                e2.setState(EmployeeState.IN_CHECK);
                repository.save(e2);
            }

            else if(employee.getState().equals(EmployeeState.SECURITY_CHECK_FINISHED)){
                e2.setState(EmployeeState.IN_CHECK);
                EmployeeInCheckState checkState = checkStateRepository.getByEmployeeId(e2.getId());
                if(checkState.getEmployeeState1() == null){
                    throw new RuntimeException("State need to IN_CHECK");
                }
                if(!checkState.getEmployeeState1().equals(EmployeeState.SECURITY_CHECK_STARTED)){
                    throw new RuntimeException("Security State is not SECURITY_CHECK_STARTED");
                }
                checkState.setEmployeeState1(EmployeeState.SECURITY_CHECK_FINISHED);
                repository.save(e2);
                checkStateRepository.save(checkState);
            }

            else if(e2.getState().equals(EmployeeState.WORK_PERMIT_CHECK_STARTED)){
                e2.setState(EmployeeState.IN_CHECK);
                repository.save(e2);
            }

            else if(employee.getState().equals(EmployeeState.WORK_PERMIT_CHECK_PENDING_VERIFICATION)){
                e2.setState(EmployeeState.IN_CHECK);
                EmployeeInCheckState checkState = checkStateRepository.getByEmployeeId(e2.getId());
                if(checkState.getEmployeeState2() == null){
                    throw new RuntimeException("State need to IN_CHECK");
                }
                if(!checkState.getEmployeeState2().equals(EmployeeState.WORK_PERMIT_CHECK_STARTED)){
                    throw new RuntimeException("Security State is not WORK_PERMIT_CHECK_STARTED");
                }
                checkState.setEmployeeState2(EmployeeState.WORK_PERMIT_CHECK_PENDING_VERIFICATION);
                repository.save(e2);
                checkStateRepository.save(checkState);
            }

            else if(employee.getState().equals(EmployeeState.WORK_PERMIT_CHECK_FINISHED)){
                e2.setState(EmployeeState.IN_CHECK);
                EmployeeInCheckState checkState = checkStateRepository.getByEmployeeId(e2.getId());
                if(checkState.getEmployeeState2() == null){
                    throw new RuntimeException("State need to IN_CHECK");
                }
                if(!checkState.getEmployeeState2().equals(EmployeeState.WORK_PERMIT_CHECK_PENDING_VERIFICATION)){
                    throw new RuntimeException("Security State is not WORK_PERMIT_CHECK_PENDING_VERIFICATION");
                }
                checkState.setEmployeeState2(EmployeeState.WORK_PERMIT_CHECK_FINISHED);
                repository.save(e2);
                checkStateRepository.save(checkState);
            }

            else if(employee.getState().equals(EmployeeState.APPROVED)){
                if(!e2.getState().equals(EmployeeState.IN_CHECK)){
                    throw new RuntimeException("Security State is not IN_CHECK");
                }
                EmployeeInCheckState checkState = checkStateRepository.getByEmployeeId(e2.getId());
                if(checkState.getEmployeeState1().equals(EmployeeState.SECURITY_CHECK_FINISHED) && checkState.getEmployeeState2().equals(EmployeeState.WORK_PERMIT_CHECK_FINISHED)){
                    repository.save(employee);
                }
            }


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
