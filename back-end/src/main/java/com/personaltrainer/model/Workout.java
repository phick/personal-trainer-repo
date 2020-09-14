package com.personaltrainer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "workout")
public class Workout {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String type;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_for_workout_id")
    private List<ExerciseForWorkout> exerciseForWorkoutList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "workout_session_id")
    private List<WorkoutSession> workoutSessionList;

    @Column(name = "user_id")
    private long userId;

    public void addExerciseForWorkout(ExerciseForWorkout exerciseForWorkout) {
        if (this.exerciseForWorkoutList == null) {
            this.exerciseForWorkoutList = new ArrayList<>();
        }
        this.exerciseForWorkoutList.add(exerciseForWorkout);
    }

    public void addWorkoutSession(WorkoutSession workoutSession) {
        if (this.workoutSessionList == null) {
            this.workoutSessionList = new ArrayList<>();
        }
        this.workoutSessionList.add(workoutSession);
    }

}
