package dev.magadiflo.specifications.app.service;

import dev.magadiflo.specifications.app.persistence.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployee(Long employeeId);
}
