package com.ead.course.api.controller;

import com.ead.course.api.model.LessonDTO;
import com.ead.course.domain.model.entity.LessonModel;
import com.ead.course.domain.model.entity.ModuleModel;
import com.ead.course.domain.repository.specs.SpecificationTemplate;
import com.ead.course.domain.service.interfaces.LessonService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/modules/{moduleId}/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public ResponseEntity<?> saveLesson(@PathVariable UUID moduleId, @RequestBody @Valid LessonDTO dto){
        Optional<ModuleModel> moduleOpt = moduleService.findById(moduleId);
        if (!moduleOpt.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found");

        var lesson = new LessonModel();
        BeanUtils.copyProperties(dto, lesson);
        lesson.setModule(moduleOpt.get());
        LessonModel savedLesson = lessonService.save(lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLesson);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId){
        Optional<LessonModel> lessonOpt = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module");

        lessonService.delete(lessonOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson Deleted Successfully");
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId,
                                          @RequestBody @Valid LessonDTO dto){

        Optional<LessonModel> lessonOpt = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module");

        var lesson = lessonOpt.get();
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setVideoUrl(dto.getVideoUrl());
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lesson));
    }

    @GetMapping
    public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable UUID moduleId, SpecificationTemplate.LessonSpec spec,
                                                           @PageableDefault(sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable){

        Page<LessonModel> pageLessons = lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable);
        return ResponseEntity.ok(pageLessons);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<?> getOneLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId){
        Optional<LessonModel> lessonOpt = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module");

        return ResponseEntity.status(HttpStatus.OK).body(lessonOpt.get());
    }
}
