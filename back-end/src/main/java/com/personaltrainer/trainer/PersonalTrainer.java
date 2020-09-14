package com.personaltrainer.trainer;


import com.personaltrainer.model.*;
import com.personaltrainer.repo.ExerciseRepository;
import com.personaltrainer.repo.WorkoutRepository;
import com.personaltrainer.repo.WorkoutSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonalTrainer {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    WorkoutSessionRepository workoutSessionRepository;

    public Workout createWorkout(WorkoutSpecification workoutSpecification) {

        String type = workoutSpecification.getType();
        String level = workoutSpecification.getLevel();
        List<Exercise> exerciseList =
                exerciseRepository.findAllByType(type);

        Workout workout =
                new Workout();
        workout.setType(type);

        switch (level) {

            case "beginner": {
                for (Exercise exercise : exerciseList) {
                    if (type.equals("strength")) {
                        ExerciseForWorkout exerciseForWorkout = new ExerciseForWorkout(0L, exercise, 5, 5, 20.0);
                        workout.addExerciseForWorkout(exerciseForWorkout);
                    } else if (type.equals("endurance")) {
                        ExerciseForWorkout exerciseForWorkout = new ExerciseForWorkout(0L, exercise, 3, 10, 0.0);
                        workout.addExerciseForWorkout(exerciseForWorkout);
                    }
                }
                break;
            }
            case "intermediate": {
                for (Exercise exercise : exerciseList) {
                    if (type.equals("strength")) {
                        ExerciseForWorkout exerciseForWorkout = new ExerciseForWorkout(0L, exercise, 5, 5, 50.0);
                        workout.addExerciseForWorkout(exerciseForWorkout);
                    } else if (type.equals("endurance")) {
                        ExerciseForWorkout exerciseForWorkout = new ExerciseForWorkout(0L, exercise, 5, 10, 0.0);
                        workout.addExerciseForWorkout(exerciseForWorkout);
                    }
                }
                break;
            }
            case "advanced": {
                for (Exercise exercise : exerciseList) {
                    if (type.equals("strength")) {
                        ExerciseForWorkout exerciseForWorkout = new ExerciseForWorkout(0L, exercise, 5, 5, 80.0);
                        workout.addExerciseForWorkout(exerciseForWorkout);
                    } else if (type.equals("endurance")) {
                        ExerciseForWorkout exerciseForWorkout = new ExerciseForWorkout(0L, exercise, 8, 10, 0.0);
                        workout.addExerciseForWorkout(exerciseForWorkout);
                    }
                }
                break;
            }
        }

        return workout;
    }

    public Workout updateWorkout(Workout workout, WorkoutSession workoutSession) {

        List<ExerciseForWorkout> exerciseForWorkoutList = workout.getExerciseForWorkoutList();

        List<ExerciseForWorkoutSession> exerciseForWorkoutSessionList = workoutSession.getExerciseForWorkoutSessionList();

        for (int i = 0; i < exerciseForWorkoutList.size(); i++) {

            ExerciseForWorkout currentExercise = exerciseForWorkoutList.get(i);
            double plannedExerciseTotalReps = currentExercise.getReps() * currentExercise.getSets();
            double actualExerciseTotalReps = 0;

            for (ExerciseSet exerciseSet : exerciseForWorkoutSessionList.get(i).getExerciseSetList()) {
                actualExerciseTotalReps += exerciseSet.getReps();
            }

            if (actualExerciseTotalReps >= plannedExerciseTotalReps) {
                if (workout.getType().equals("strength")) {
                    if (currentExercise.getReps() >= 8) {
                        currentExercise.setWeight(currentExercise.getWeight() + 2.0);
                        currentExercise.setReps(5);
                    } else {
                        currentExercise.setReps(currentExercise.getReps() + 1);
                    }
                } else if (workout.getType().equals("endurance")) {
                    if (currentExercise.getReps() >= 15) {
                        currentExercise.setSets(currentExercise.getSets() + 1);
                        currentExercise.setReps(10);
                    } else {
                        currentExercise.setReps(currentExercise.getReps() + 1);
                    }
                }
            }
        }
        return workout;
    }
}
