package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private List<Employee> employees = new ArrayList<>();


    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> showAll() {
        employees = new ArrayList<>(employeeService.listEmployee());
        Comparator<Employee> comparator = Comparator.comparing(obj -> obj.getEmployeeId());
        Collections.sort(employees, comparator);
        return employees != null & !employees.isEmpty()
                ? new ResponseEntity<>(employees, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/employee/add")
    public ResponseEntity<?> add(@RequestBody Employee employee) {
        if (employee.getFirstName() != "" && employee.getLastName() != ""
                && employee.getDepartmentId() != null
                && employee.getJob_title() != "" && employee.getDate_of_birth() != null) {
            employeeService.save(employee);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> read(@PathVariable(name = "id") int id) {
        final Employee employee = employeeService.getEmployeeById((long) id);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {

        final boolean deleted = employeeService.removeById((long) id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
        final boolean updated = employeeService.update(employee);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ModelAttribute(value = "employee")
    public Employee newEntity() {
        return new Employee();
    }

}
