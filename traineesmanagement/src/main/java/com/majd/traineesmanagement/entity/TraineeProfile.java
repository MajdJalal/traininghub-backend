package com.majd.traineesmanagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Builder
@Entity
@Table(name =  "trainees-profiles")
@NoArgsConstructor
@AllArgsConstructor
public class TraineeProfile {

    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;//this email that is gonna be shown on the website so trainers contact u with
    private String emailAccount;// the email of the account having this profile, gonna be inserted directly when creating a new profile
    private String phoneNumber;
    private LocalDate birthDay;
    private String country;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainee_id_seq")
    @SequenceGenerator(name = "trainee_id_seq", sequenceName = "trainee_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "trainee_id", updatable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "trainee_firstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "trainee_lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(name = "trainee_nickName", unique = true)
    public String getNickName() {
        return nickName;
    }

    @Column(name = "trainee_email", unique = true)
    public String getEmail() {
        return email;
    }

    @Column(name = "email_account", unique = true, nullable = false)
    public String getEmailAccount() {
        return emailAccount;
    }

    @Column(name = "trainee_phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "trainee_birthDay")
    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Column(name = "trainee_country")
    public String getCountry() {
        return country;
    }

}
