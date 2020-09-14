package com.personaltrainer.service;

import com.personaltrainer.exeption.ResourceNotFoundException;
import com.personaltrainer.model.User;
import com.personaltrainer.model.UserProfile;
import com.personaltrainer.model.Workout;
import com.personaltrainer.repo.UserRepository;
import com.personaltrainer.repo.WorkoutRepository;
import com.personaltrainer.security.UserPrincipal;
import lombok.Getter;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkoutRepository workoutRepository;


    public List<User> findAll() {


        return userRepository.findAll();

    }

    public User findById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getCurrentUser(UserPrincipal userPrincipal) {


        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));


    }

    public List<Workout> getWorkoutsList(UserPrincipal userPrincipal) {


        return getCurrentUser(userPrincipal).getWorkoutList();
    }


    public UserProfile getUserProfile(UserPrincipal userPrincipal) {

        return getCurrentUser(userPrincipal).getUserProfile();

    }


    public Workout getSingleWorkout(UserPrincipal userPrincipal, long id) {

        return getWorkoutsList(userPrincipal).stream().filter(workout -> workout.getId().equals(id)).findAny().orElse(null);

    }

    public void deleteUserById(long id){

        userRepository.deleteById(id);
    }


}
