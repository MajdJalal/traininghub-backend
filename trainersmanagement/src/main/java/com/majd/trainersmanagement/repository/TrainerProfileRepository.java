package com.majd.trainersmanagement.repository;


import com.majd.trainersmanagement.entity.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {
    Optional<TrainerProfile> findByEmailAccount(String emailAccount);
}
