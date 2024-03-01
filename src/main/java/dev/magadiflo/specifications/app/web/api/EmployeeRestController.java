package dev.magadiflo.specifications.app.web.api;

import dev.magadiflo.specifications.app.persistence.entity.Employee;
import dev.magadiflo.specifications.app.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/employees")
public class EmployeeRestController {

    private final IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        return this.employeeService.getEmployee(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/search-specifications")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Sort sort) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok(this.employeeService.searchEmployees(firstName, minSalary, departmentName, pageable));
    }

    @GetMapping(path = "/search-old-employees")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam(required = false) Integer xYears,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Sort sort) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok(this.employeeService.searchEmployees(xYears, departmentName, pageable));
    }

    @GetMapping(path = "/search-no-pagination")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) LocalDate initialDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String departmentName) {

        return ResponseEntity.ok(this.employeeService.searchEmployees(initialDate, firstName, departmentName));
    }
}
