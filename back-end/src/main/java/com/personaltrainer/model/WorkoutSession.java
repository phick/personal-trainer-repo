package com.personaltrainer.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "workout_session")
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private String date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_for_workout_session_id")
    private List<ExerciseForWorkoutSession> exerciseForWorkoutSessionList;

}
