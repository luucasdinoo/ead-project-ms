package com.ead.course.api.controller;

import com.ead.course.api.model.ModuleDTO;
import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.model.entity.ModuleModel;
import com.ead.course.domain.repository.specs.SpecificationTemplate;
import com.ead.course.domain.service.interfaces.CourseService;
import com.ead.course.domain.service.interfaces.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> saveModule(@PathVariable UUID courseId, @RequestBody @Valid ModuleDTO dto){
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");

        var module = new ModuleModel();
        BeanUtils.copyProperties(dto, module);
        module.setCourse(courseOpt.get());
        ModuleModel savedModule = moduleService.save(module);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModule);
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID courseId, @PathVariable UUID moduleId){
        Optional<ModuleModel> moduleOpt = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");

        moduleService.delete(moduleOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module Deleted Successfully");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> updateModule(@PathVariable UUID courseId, @PathVariable UUID moduleId,
                                          @RequestBody @Valid ModuleDTO dto){

        Optional<ModuleModel> moduleOpt = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");

        var module = moduleOpt.get();
        module.setTitle(dto.getTitle());
        module.setDescription(dto.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable UUID courseId, SpecificationTemplate.ModuleSpec spec,
                                                           @PageableDefault(sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable){
        Page<ModuleModel> modulePages = moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);
        return ResponseEntity.ok(modulePages);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> getOneModule(@PathVariable UUID courseId, @PathVariable UUID moduleId){
        Optional<ModuleModel> moduleOpt = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");

        return ResponseEntity.status(HttpStatus.OK).body(moduleOpt.get());
    }
}
