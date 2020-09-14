package com.personaltrainer.repo;


import com.personaltrainer.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {


    ArrayList<Workout> findByUserId(long userId);


}
