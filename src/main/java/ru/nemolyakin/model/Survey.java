package ru.nemolyakin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "surveys")
@NoArgsConstructor
@AllArgsConstructor
public class Survey extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "surveys_questions",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @ToString.Exclude
    private List<Question> questions;

    @ManyToMany(mappedBy = "completedSurveys", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<User> users;

    @Column(name = "time_updated")
    private LocalDateTime timeUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "survey_status", columnDefinition = "default 'OPEN'")
    private SurveyStatus surveyStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "default 'ACTIVE'")
    private Status status;
}
