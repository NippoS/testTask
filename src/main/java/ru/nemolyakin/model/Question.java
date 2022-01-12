package ru.nemolyakin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {

    @Column(name = "text")
    private String text;

    @Column(name = "count_answer")
    private int countAnswer;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    private List<Survey> surveys;

    @Column(name = "time_updated")
    private LocalDateTime timeUpdated;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    @ToString.Exclude
    private List<Answer> answers;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "default 'ACTIVE'")
    private Status status;
}
