package dev.magadiflo.specifications.app.web.api;

import dev.magadiflo.specifications.app.persistence.entity.Department;
import dev.magadiflo.specifications.app.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/departments")
public class DepartmentRestController {

    private final IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment() {
        return ResponseEntity.ok(this.departmentService.getAllDepartment());
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long departmentId) {
        return this.departmentService.getDepartment(departmentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
