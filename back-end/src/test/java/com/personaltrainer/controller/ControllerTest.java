package com.personaltrainer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personaltrainer.PersonalTrainerApplication;
import com.personaltrainer.model.User;
import com.personaltrainer.model.Workout;
import com.personaltrainer.security.CurrentUser;
import com.personaltrainer.security.UserPrincipal;
import com.personaltrainer.service.UserService;
import com.personaltrainer.trainer.WorkoutSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonalTrainerApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;


    @Test
    void test() throws Exception {

        //given
        UserRegisterValues userRegisterValues = new UserRegisterValues("user2", "user2@user2.com", "user2");

        //when-then
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRegisterValues)))
                .andExpect(content().string(containsString("{\"success\":true,\"message\":\"User registered successfully@\"}")));


        User user = userService.findAll().stream().filter(u -> u.getEmail().equals("user2@user2.com")).findFirst().orElseGet(null);

        Assertions.assertNotNull(user);

        user.setBalance(user.getBalance() + 25.0);

        user = userService.save(user);

        Assertions.assertEquals(25.0, user.getBalance());

        //given
        UserLoginValues userLoginValues = new UserLoginValues("user2@user2.com", "user2");

        //when-then
        ResultActions result
                = mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userLoginValues)))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String access_token = jsonParser.parseMap(resultString).get("accessToken").toString();


        //given
        WorkoutSpecification workoutSpecification = new WorkoutSpecification("strength", "beginner");
        //when-then
        mockMvc.perform(post("/workouts")
                .header("authorization", "Bearer " + access_token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(workoutSpecification)))
                .andExpect(status().isCreated());



        userService.deleteUserById(user.getId());

    }




}

@AllArgsConstructor
@Getter
@Setter
class UserRegisterValues {
    private String name;
    private String email;
    private String password;
}

@AllArgsConstructor
@Getter
@Setter
class UserLoginValues {
    private String email;
    private String password;
}