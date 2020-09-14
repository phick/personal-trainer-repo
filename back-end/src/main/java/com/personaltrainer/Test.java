package com.personaltrainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personaltrainer.model.Exercise;
import com.personaltrainer.model.ExerciseForWorkoutSession;
import com.personaltrainer.model.ExerciseSet;
import com.personaltrainer.model.WorkoutSession;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"));
        ExerciseSet exerciseSet = new ExerciseSet(1L, 10, 0.0);
        Exercise exercise = new Exercise(1L, "crunches", "endurance");
        ExerciseForWorkoutSession exerciseForWorkoutSession =
                new ExerciseForWorkoutSession(1L, 20.0, exercise, new ArrayList<>());

        for (long i = 1; i <= 5; i++) {
            exerciseSet = new ExerciseSet(i, 10, 0.0);
            exerciseForWorkoutSession.getExerciseSetList().add(exerciseSet);
        }


        WorkoutSession workoutSession = new WorkoutSession(1L, date, new ArrayList<>());
        workoutSession.getExerciseForWorkoutSessionList().add(exerciseForWorkoutSession);

        objectMapper.writeValue(new File("target/workoutSession.json"), workoutSession);
    }
}
