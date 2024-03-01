package dev.magadiflo.specifications.app.service;

import dev.magadiflo.specifications.app.persistence.entity.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    List<Department> getAllDepartment();

    Optional<Department> getDepartment(Long departmentId);
}
