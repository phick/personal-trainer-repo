package com.personaltrainer.service;

import com.personaltrainer.model.Workout;
import com.personaltrainer.repo.WorkoutRepository;
import com.personaltrainer.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WorkoutService {
    @Autowired
    WorkoutRepository workoutRepository;


    public ArrayList getUsersWorkouts(UserPrincipal userPrincipal) {


        ArrayList<Workout> workouts = workoutRepository.findByUserId(userPrincipal.getId());


        return workouts;
    }


}
