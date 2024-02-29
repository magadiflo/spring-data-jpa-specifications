package dev.magadiflo.specifications.app.service.impl;

import dev.magadiflo.specifications.app.persistence.entity.Employee;
import dev.magadiflo.specifications.app.persistence.repository.IEmployeeRepository;
import dev.magadiflo.specifications.app.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
