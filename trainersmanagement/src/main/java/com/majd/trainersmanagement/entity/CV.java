package com.majd.trainersmanagement.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@Entity
@Table(name =  "CVs")
public class CV {


    private Long id;
    private String aboutMe;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cv_id_seq")
    @SequenceGenerator(name = "cv_id_seq", sequenceName = "cv_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "cv_id")
    public Long getId() {
        return id;
    }
    @Column(name = "aboutMe")
    public String getAboutMe() {
        return aboutMe;
    }


}
