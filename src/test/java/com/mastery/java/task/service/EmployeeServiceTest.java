package com.mastery.java.task.service;

import com.mastery.java.task.config.AppConfiguration;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.exceptions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = AppConfiguration.class)
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService service;

    @Test
    @Order(1)
    public void shouldReturnAllEmployeesTest() throws EmployeesNotFoundException {
        int actual = 0;
        actual = service.listEmployee().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    public void shouldDeleteOneEmployeeTest() throws EmployeeNotDeletedException {
        Boolean actual = service.removeById(3L);
        Boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    public void shouldStayTwoEmployeeTest() throws EmployeesNotFoundException {
        int actual = 0;
        actual = service.listEmployee().size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    public void shouldGetEmployeeTest() throws EmployeeNotFoundException {
        LocalDate localDate = LocalDate.of(1989, 2, 28);
        Employee actual = service.getEmployeeById(2L);
        Employee expected = Employee.builder()
                .employeeId(2L)
                .firstName("Vova")
                .lastName("Ivanov")
                .departmentId(2L)
                .gender(Gender.MALE)
                .job_title("Developer")
                .date_of_birth(localDate)
                .build();
        assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    public void shouldUpdateEmployeeTest() throws EmployeeNotFoundException, EmployeeNotUpdatedException {
        Employee expected = service.getEmployeeById(2L);
        expected.setFirstName("Roma");
        service.update(expected);
        Employee actual = service.getEmployeeById(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    public void shouldSaveEmployeeTest() throws EmployeeNotSavedException, EmployeeNotFoundException {
        LocalDate localDate = LocalDate.of(1991, 4, 28);
        Employee employee = Employee.builder()
                .employeeId(2L)
                .firstName("Anna")
                .lastName("Sviridova")
                .departmentId(4L)
                .gender(Gender.FEMALE)
                .job_title("Developer")
                .date_of_birth(localDate)
                .build();
        Employee expected = service.save(employee);
        Employee actual = service.getEmployeeById(expected.getEmployeeId());
        assertEquals(expected, actual);
    }
}
