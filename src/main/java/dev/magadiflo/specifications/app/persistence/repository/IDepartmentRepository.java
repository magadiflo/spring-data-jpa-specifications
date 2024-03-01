package dev.magadiflo.specifications.app.persistence.repository;

import dev.magadiflo.specifications.app.persistence.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {
}
