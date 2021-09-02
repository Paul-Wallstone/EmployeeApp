package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;

import java.util.List;


public interface EmployeeService {
    public Employee save(Employee employee);

    public Employee getEmployeeById(Long id);

    public List<Employee> listEmployee();

    public Boolean removeById(Long id);

    public Boolean update(Employee employee);
}
