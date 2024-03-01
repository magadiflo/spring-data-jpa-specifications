package dev.magadiflo.specifications.app.persistence.repository.specifications;

import dev.magadiflo.specifications.app.persistence.entity.Department_;
import dev.magadiflo.specifications.app.persistence.entity.Employee;
import dev.magadiflo.specifications.app.persistence.entity.Employee_;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

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

    public static Specification<Employee> hasAHiringDateGreaterThanXYears(Integer xYears) {
        return (root, query, criteriaBuilder) -> {
            LocalDate localDate = LocalDate.now().minusYears(xYears);
            log.info("Fecha actual: {}, Años de antigüedad mayor a: {}", LocalDate.now(), xYears);
            return criteriaBuilder.lessThan(root.get(Employee_.HIRE_DATE), localDate);
        };
    }

    public static Specification<Employee> hasHireDateBetweenTwoDatesOrGetByDepartmentName(LocalDate initialDate, String firstName, String departmentName) {
        return (root, query, criteriaBuilder) -> {
            /**
             * criteriaBuilder.conjunction(), crea una conjunción (con cero conjunciones).
             * Una conjunción con cero conjunciones es verdadera.
             *
             * criteriaBuilder.disjunction(), crea una disyunción (con cero disyunciones).
             * Una disyunción con cero disyunciones es falsa.
             *
             * Predicate, el tipo de predicado simple o compuesto: una conjunción o disyunción de restricciones.
             * Se considera que un predicado simple es una conjunción con una sola conjunción.
             */
            Predicate conjunction = criteriaBuilder.conjunction();
            Predicate disjunction = criteriaBuilder.disjunction();
            LocalDate currentDate = LocalDate.now();

            log.info("{} es menor que la fecha actual {}", initialDate, currentDate);

            if (Objects.nonNull(initialDate) && initialDate.isBefore(currentDate)) {
                Predicate between = criteriaBuilder.between(root.get(Employee_.HIRE_DATE), initialDate, currentDate);
                conjunction = criteriaBuilder.and(conjunction, between);
            }

            if (StringUtils.hasText(firstName)) {
                Predicate like = criteriaBuilder.like(root.get(Employee_.FIRST_NAME), "%" + firstName + "%");
                conjunction = criteriaBuilder.and(conjunction, like);
            }

            if (StringUtils.hasText(departmentName)) {
                Predicate equal = criteriaBuilder.equal(root.get(Employee_.DEPARTMENT).get(Department_.NAME), departmentName);
                disjunction = criteriaBuilder.or(disjunction, equal);
            }

            query.orderBy(
                    criteriaBuilder.desc(root.get(Employee_.FIRST_NAME)),
                    criteriaBuilder.asc(root.get(Employee_.ID))
            );

            return criteriaBuilder.or(conjunction, disjunction);
        };
    }
}
