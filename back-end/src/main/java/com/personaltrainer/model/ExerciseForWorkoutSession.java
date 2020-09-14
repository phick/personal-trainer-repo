package com.personaltrainer.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercise_for_workout_session")
public class ExerciseForWorkoutSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "weight_for_set")
    private double weightForSet;


    @OneToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_set_id")
    private List<ExerciseSet> exerciseSetList;
}
