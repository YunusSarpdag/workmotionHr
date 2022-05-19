package com.workmotion.hr.workmotionhr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String emailAddress;
    private EmployeeState state;

    public Employee(){

    }

    public Employee(Long id, String name, int age, String emailAddress, EmployeeState state) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmployeeState getState() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state = state;
    }
}
