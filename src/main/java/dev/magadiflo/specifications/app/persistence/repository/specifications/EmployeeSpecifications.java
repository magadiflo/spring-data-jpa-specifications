package dev.magadiflo.specifications.app.persistence.repository.specifications;

import dev.magadiflo.specifications.app.persistence.entity.Department_;
import dev.magadiflo.specifications.app.persistence.entity.Employee;
import dev.magadiflo.specifications.app.persistence.entity.Employee_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class EmployeeSpecifications {

    public static Specification<Employee> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Employee_.FIRST_NAME), firstName);
    }

    public static Specification<Employee> hasSalaryGreaterThan(Double salary) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(Employee_.SALARY), salary);
    }

    public static Specification<Employee> hasDepartmentName(String departmentName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Employee_.DEPARTMENT).get(Department_.NAME), departmentName);
    }
}
