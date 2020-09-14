package com.personaltrainer.repo;


import com.personaltrainer.model.ExerciseForWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseForWorkoutRepository extends JpaRepository<ExerciseForWorkout, Long> {
}
