package com.majd.traineesmanagement.repository;

import com.majd.traineesmanagement.entity.TraineeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository  extends JpaRepository<TraineeProfile, Long> {
    Optional<TraineeProfile> findByEmailAccount(String emailAccount);
}
