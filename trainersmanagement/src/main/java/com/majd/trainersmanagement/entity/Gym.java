package com.majd.trainersmanagement.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Setter;

import java.util.Set;

@Setter
@Builder
@Entity
@Table(name =  "gyms")
public class Gym {


    private Long id;
    private String name;
    private String country;
    private String city;
    private String phoneNumber;
    private Set<TrainerProfile> trainersProfiles;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_id_seq")
    @SequenceGenerator(name = "gym_id_seq", sequenceName = "gym_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "gym_id")
    public Long getId() {
        return id;
    }

    @Column(name = "gym_name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "gym_country")
    public String getCountry() {
        return country;
    }

    @Column(name = "gym_city")
    public String getCity() {
        return city;
    }

    @Column(name = "gym_phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gyms")
    public Set<TrainerProfile> getTrainersProfiles() {
        return trainersProfiles;
    }
}
