package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDaoNamedImpl implements EmployeeDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;


    @Transactional
    @Override
    public Employee save(Employee employee) {
        Map<String, Object> params = new HashMap<>();
        params.put("first_name", employee.getFirstName());
        params.put("last_name", employee.getLastName());
        params.put("department_id", employee.getDepartmentId());
        params.put("gender", employee.getGender().getTitle());
        params.put("job_title", employee.getJob_title());
        params.put("date_of_birth", employee.getDate_of_birth());
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update("insert into employeedb.employee (first_name, last_name, department_id, gender, job_title,date_of_birth) values (:first_name, :last_name, :department_id, :gender, :job_title,:date_of_birth)", paramSource, holder, new String[]{"employee_id"});
        employee.setEmployeeId(holder.getKey().longValue());
        return employee;
    }

    @Transactional
    @Override
    public Employee getEmployeeById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("select * from employeedb.employee where employee_id=:id", params, new EmployeeMapper());
    }

    @Transactional
    @Override
    public List<Employee> listEmployee() {
        return jdbc.query("select * from employeedb.employee", new EmployeeMapper());
    }

    @Transactional
    @Override
    public Boolean removeById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.update("delete from employeedb.employee where employee_id = :id", params) == 1;
    }

    @Transactional
    @Override
    public Boolean update(Employee employee) {
        Map<String, Object> params = new HashMap<>();
        params.put("employee_id", employee.getEmployeeId());
        params.put("first_name", employee.getFirstName());
        params.put("last_name", employee.getLastName());
        params.put("department_id", employee.getDepartmentId());
        params.put("gender", employee.getGender().getTitle());
        params.put("job_title", employee.getJob_title());
        params.put("date_of_birth", employee.getDate_of_birth());
        return jdbc.update("update employeedb.employee set first_name = :first_name, " +
                "last_name = :last_name, department_id = :department_id," +
                " gender = :gender , job_title = :job_title,date_of_birth = :date_of_birth " +
                "where employee_id = :employee_id ", params) == 1;
    }
}
