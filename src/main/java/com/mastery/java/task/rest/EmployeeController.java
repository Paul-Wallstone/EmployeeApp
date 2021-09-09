package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.*;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("employee")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity showAll() {
        try {
            return ResponseEntity.ok(employeeService.listEmployee());
        } catch (EmployeesNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }


    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.save(employee));
        } catch (EmployeeNotSavedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity read(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById((long) id));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {
        try {
            employeeService.removeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmployeeNotDeletedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Employee employee) {
        try {
            employeeService.update(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmployeeNotUpdatedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
