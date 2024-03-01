package dev.magadiflo.specifications.app.service.impl;

import dev.magadiflo.specifications.app.persistence.entity.Employee;
import dev.magadiflo.specifications.app.persistence.repository.IEmployeeRepository;
import dev.magadiflo.specifications.app.persistence.repository.specifications.EmployeeSpecifications;
import dev.magadiflo.specifications.app.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployee(Long employeeId) {
        return this.employeeRepository.findById(employeeId);
    }

    @Override
    public Page<Employee> searchEmployees(String firstName, Double minSalary, String departmentName, Pageable pageable) {
        Specification<Employee> employeeSpec = Specification.where(null);

        if (StringUtils.hasText(firstName)) {
            employeeSpec = employeeSpec.and(EmployeeSpecifications.hasFirstName(firstName));
        }

        if (Objects.nonNull(minSalary) && minSalary > 0) {
            employeeSpec = employeeSpec.and(EmployeeSpecifications.hasSalaryGreaterThan(minSalary));
        }

        if (StringUtils.hasText(departmentName)) {
            employeeSpec = employeeSpec.and(EmployeeSpecifications.hasDepartmentName(departmentName));
        }

        return this.employeeRepository.findAll(employeeSpec, pageable);
    }

    @Override
    public Page<Employee> searchEmployees(Integer xYears, String departmentName, Pageable pageable) {
        Specification<Employee> employeeSpec = Specification.where(null);

        // Suponiendo que la lógica de negocio dice que los xYears deben estar entre [1 - 20] años
        if (Objects.nonNull(xYears) && xYears <= 20 && xYears >= 1) {
            employeeSpec = employeeSpec.and(EmployeeSpecifications.hasAHiringDateGreaterThanXYears(xYears));
        }

        if (StringUtils.hasText(departmentName)) {
            employeeSpec = employeeSpec.and(EmployeeSpecifications.hasDepartmentName(departmentName));
        }

        return this.employeeRepository.findAll(employeeSpec, pageable);
    }

    @Override
    public List<Employee> searchEmployees(LocalDate initialDate, String firstName, String departmentName) {
        Specification<Employee> specification = EmployeeSpecifications.hasHireDateBetweenTwoDatesOrGetByDepartmentName(initialDate, firstName, departmentName);
        return this.employeeRepository.findAll(specification);
    }
}
