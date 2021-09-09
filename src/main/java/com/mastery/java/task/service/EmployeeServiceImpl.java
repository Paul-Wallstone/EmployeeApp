package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee save(Employee employee) throws EmployeeNotSavedException {
        if (employee.getFirstName() != "" && employee.getLastName() != ""
                && employee.getDepartmentId() != null
                && employee.getJob_title() != "" && employee.getDate_of_birth() != null) {
            return employeeDao.save(employee);
        } else {
            throw new EmployeeNotSavedException("Employee was not saved");
        }
    }

    @Override
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        try {
            Employee employee = employeeDao.getEmployeeById(id);
            return employee;
        } catch (Exception e) {
            throw new EmployeeNotFoundException("Employee is not exist");
        }
    }

    @Override
    public List<Employee> listEmployee() throws EmployeesNotFoundException {
        List<Employee> employees = new ArrayList<>(employeeDao.listEmployee());
        if (employees.size() != 0) {
            Comparator<Employee> comparator = Comparator.comparing(obj -> obj.getEmployeeId());
            Collections.sort(employees, comparator);
            return employees;
        } else {
            throw new EmployeesNotFoundException("Database don't contain any employee");
        }
    }

    @Override
    public Boolean removeById(Long id) throws EmployeeNotDeletedException {
        if (employeeDao.removeById(id)) {
            return true;
        }
        throw new EmployeeNotDeletedException("Employee was not deleted");
    }

    @Override
    public Boolean update(Employee employee) throws EmployeeNotUpdatedException {
        if (employeeDao.update(employee)) {
            return true;
        }
        throw new EmployeeNotUpdatedException("Employee was not updated");
    }
}
