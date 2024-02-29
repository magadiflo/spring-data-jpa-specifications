package dev.magadiflo.specifications.app.persistence.repository;

import dev.magadiflo.specifications.app.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
}
