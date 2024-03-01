package dev.magadiflo.specifications.app.service.impl;

import dev.magadiflo.specifications.app.persistence.entity.Department;
import dev.magadiflo.specifications.app.persistence.repository.IDepartmentRepository;
import dev.magadiflo.specifications.app.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Department> getAllDepartment() {
        return this.departmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Department> getDepartment(Long departmentId) {
        return this.departmentRepository.findById(departmentId);
    }
}
