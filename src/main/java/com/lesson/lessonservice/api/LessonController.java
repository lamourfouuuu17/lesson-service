package com.lesson.lessonservice.api;

import com.lesson.lessonservice.repo.model.Lesson;
import com.lesson.lessonservice.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lessons")
public final class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<com.lesson.lessonservice.repo.model.Lesson>> index() {
        final List<com.lesson.lessonservice.repo.model.Lesson> lessons = lessonService.fetchAll();
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.lesson.lessonservice.repo.model.Lesson> showById(@PathVariable long id) {
        try {
            final com.lesson.lessonservice.repo.model.Lesson lesson = lessonService.fetchLessonById(id);
            return ResponseEntity.ok(lesson);
        }
        catch (IllegalArgumentException error){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.lesson.lessonservice.api.dto.Lesson lesson) {
        final String name = lesson.getName();
        final int price = lesson.getPrice();
        final String category = lesson.getCategory();
        final long lessonId = lessonService.create(name, price, category);
        final String lessonUri = String.format("/lessons/%d", lessonId);

        return ResponseEntity.created(URI.create(lessonUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.lesson.lessonservice.api.dto.Lesson lesson) {
        final String name = lesson.getName();
        final int price = lesson.getPrice();
        final String category = lesson.getCategory();
        try {
            lessonService.update(id, name, price, category);
            return ResponseEntity.noContent().build();
        }
        catch (IllegalArgumentException error){
            return ResponseEntity.notFound().build();
        }
    }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteById(@PathVariable long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable long id) {
        lessonService.deleteUser(id);
    }
}
