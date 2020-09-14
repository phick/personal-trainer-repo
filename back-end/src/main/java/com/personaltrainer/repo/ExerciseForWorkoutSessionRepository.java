package com.personaltrainer.repo;

import com.personaltrainer.model.ExerciseForWorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseForWorkoutSessionRepository extends JpaRepository<ExerciseForWorkoutSession, Long> {

}
