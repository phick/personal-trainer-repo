package com.personaltrainer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity

@Table(name = "user_profile")
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "weight")
    private String weight;

    @Column(name = "height")
    private String height;

    @Column(name = "age")
    private String age;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public UserProfile() {
    }
}
