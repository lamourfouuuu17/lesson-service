package com.lesson.lessonservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Lesson {
    private String name;
    private int price;
    private String category;
}
