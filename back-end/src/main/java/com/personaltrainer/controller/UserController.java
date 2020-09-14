package com.personaltrainer.controller;

import com.personaltrainer.model.User;
import com.personaltrainer.model.UserProfile;
import com.personaltrainer.model.Workout;
import com.personaltrainer.model.WorkoutSession;
import com.personaltrainer.security.CurrentUser;
import com.personaltrainer.security.UserPrincipal;
import com.personaltrainer.service.UserService;
import com.personaltrainer.trainer.PersonalTrainer;
import com.personaltrainer.trainer.WorkoutSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private PersonalTrainer personalTrainer;


    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentUser(userPrincipal);
    }


    @GetMapping("/user/profile")
    @PreAuthorize("hasRole('USER')")
    public UserProfile getUserProfile(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserProfile(userPrincipal);
    }


    @GetMapping("/workouts")
    @PreAuthorize("hasRole('USER')")
    public List<Workout> getUsersWorkout(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getWorkoutsList(userPrincipal);
    }


    @GetMapping("/workouts/{id}")
    @PreAuthorize("hasRole('USER')")
    public Workout getSingleWorkout(@CurrentUser UserPrincipal userPrincipal, @PathVariable int id) {
        return userService.getSingleWorkout(userPrincipal, id);
    }

    @PostMapping("/workouts/{id}/sessions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WorkoutSession> addNewWorkoutSession(@CurrentUser UserPrincipal userPrincipal, @PathVariable int id, @RequestBody WorkoutSession workoutSession) {
        User user = userService.getCurrentUser(userPrincipal);
        workoutSession.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")));
        Workout workout = userService.getSingleWorkout(userPrincipal, id);
        workout.addWorkoutSession(workoutSession);
        personalTrainer.updateWorkout(workout, workoutSession);
        userService.save(user);
        return new ResponseEntity<WorkoutSession>(workoutSession, HttpStatus.OK);
    }

    @GetMapping("/workouts/{id}/sessions")
    @PreAuthorize("hasRole('USER')")
    public List<WorkoutSession> getWorkoutSessionList(@CurrentUser UserPrincipal userPrincipal, @PathVariable int id) {
        User user = userService.getCurrentUser(userPrincipal);
        Workout workout = userService.getSingleWorkout(userPrincipal, id);
        return workout.getWorkoutSessionList();
    }

    @PostMapping("/workouts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Workout> addNewWorkout(@CurrentUser UserPrincipal userPrincipal, @RequestBody WorkoutSpecification workoutSpecification) {
        User user = userService.getCurrentUser(userPrincipal);
        if (user.getBalance() < 25.0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        user.setBalance(user.getBalance() - 25.0);
        Workout workout = personalTrainer.createWorkout(workoutSpecification);
        user.addWorkout(workout);
        workout.setUserId(user.getId());
        workout.setName(user.getUsername() + "'s workout plan");
        user = userService.save(user);
        workout = user.getWorkoutList().get(user.getWorkoutList().size() - 1);
        return new ResponseEntity<>(workout, HttpStatus.CREATED);
    }

}
