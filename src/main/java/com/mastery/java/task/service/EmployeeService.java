package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.*;

import java.util.List;


public interface EmployeeService {
    public Employee save(Employee employee) throws EmployeeNotSavedException;

    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException;

    public List<Employee> listEmployee() throws EmployeesNotFoundException;

    public Boolean removeById(Long id) throws EmployeeNotDeletedException;

    public Boolean update(Employee employee) throws EmployeeNotUpdatedException;
}
