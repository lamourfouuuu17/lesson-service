package com.lesson.lessonservice.service;

import com.lesson.lessonservice.repo.LessonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lesson.lessonservice.repo.model.Lesson;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class LessonService {

    private final LessonRepo lessonRepo;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Lesson> fetchAll() {
        return lessonRepo.findAll();
    }

    public Lesson fetchLessonById(long id) throws IllegalArgumentException {
        final Optional<Lesson> maybeLesson = lessonRepo.findById(id);

        if (maybeLesson.isPresent())
            return maybeLesson.get();
        else
            throw new IllegalArgumentException("Wrong id");
    }

    public long create(String name, int price, String category){
        final Lesson lesson = new Lesson(name, price, category);
        final Lesson savedLesson = lessonRepo.save(lesson);

        return savedLesson.getId();
    }

    public void update(long id, String name, int price, String category)
            throws IllegalArgumentException
    {
        final Optional<Lesson> maybeLesson = lessonRepo.findById(id);

        if (maybeLesson.isEmpty())
            throw new IllegalArgumentException("Wrong id");

        final Lesson lesson = maybeLesson.get();
        if (name != null && !name.isBlank()) lesson.setName(name);
        if (price != 0) lesson.setPrice(price);
        if (category != null && !category.isBlank()) lesson.setCategory(category);
        lessonRepo.save(lesson);
    }

    public void deleteUser(long id) {
        restTemplate.delete("http://localhost:8200/users/" + id);
    }

    public void deleteLesson(long id){
        lessonRepo.deleteById(id);
    }
}
