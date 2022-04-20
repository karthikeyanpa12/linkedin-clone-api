package com.linkedin.ProfessionalNetworking.repository;

import com.linkedin.ProfessionalNetworking.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findByUserId(String userId);
    public Profile findByProfileId(String profileId);
}
