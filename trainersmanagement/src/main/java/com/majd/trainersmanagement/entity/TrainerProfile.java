package com.majd.trainersmanagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * represents the trainer entity
 */
@Setter
@Builder
@Entity
@Table(name =  "trainers-profiles")
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfile {


    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;//this email that is gonna be shown on the website so trainees contact u with
    private String emailAccount;// the email of the account having this profile, gonna be inserted directly when creating a new profile
    private String phoneNumber;
    private LocalDate birthDay;
    private String country;

    private Set<Gym> gyms;

    private CV cv;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_id_seq")
    @SequenceGenerator(name = "trainer_id_seq", sequenceName = "trainer_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "trainer_id", updatable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "trainer_firstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "trainer_lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * nickName is an identifier of a trainer
     * not mandatory, but having it make u stand out in search better as it is unique
     */
    @Column(name = "trainer_nickName", unique = true)
    public String getNickName() {
        return nickName;
    }

    @Column(name = "trainer_email", unique = true)
    public String getEmail() {
        return email;
    }

    @Column(name = "email_account", unique = true, nullable = false)
    public String getEmailAccount() {
        return emailAccount;
    }

    @Column(name = "trainer_phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "trainer_birthDay")
    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Column(name = "trainer_country")
    public String getCountry() {
        return country;
    }

    @OneToOne
    @JoinColumn(name = "trainer_cv")
    public CV getCv() {
        return cv;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trainers_gyms",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "gym_id")
    )
    public Set<Gym> getGyms() {
        return gyms;
    }
}
