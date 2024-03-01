package dev.magadiflo.specifications.app.service;

import dev.magadiflo.specifications.app.persistence.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployee(Long employeeId);

    Page<Employee> searchEmployees(String firstName, Double minSalary, String departmentName, Pageable pageable);

    Page<Employee> searchEmployees(Integer xYears, String departmentName, Pageable pageable);

    List<Employee> searchEmployees(LocalDate initialDate, String firstName, String departmentName);
}
