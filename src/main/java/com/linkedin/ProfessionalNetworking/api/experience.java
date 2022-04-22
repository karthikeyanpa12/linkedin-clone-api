package com.linkedin.ProfessionalNetworking.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.linkedin.ProfessionalNetworking.dto.ExperienceRequest;
import com.linkedin.ProfessionalNetworking.model.Experience;
import com.linkedin.ProfessionalNetworking.response.ApiResponse;
import com.linkedin.ProfessionalNetworking.service.ExperienceService;
import com.linkedin.ProfessionalNetworking.util.Constants;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class experience {

    @Autowired
    ExperienceService experienceService;

    @GetMapping(value = "/experience/{userId}")
    public ResponseEntity<ApiResponse> getExperienceByUserId(@PathVariable String userId) {
        ApiResponse apiResponse = new ApiResponse();
        if (userId != null) {
            List<Experience> foundExperience = experienceService.getExperienceByUserId(userId);
            if (foundExperience != null && !foundExperience.isEmpty()) {
                apiResponse.setResponse(foundExperience);
                apiResponse.setStatus(HttpStatus.OK.toString());
                apiResponse.setMessage("Found Experience");
            } else {
                apiResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
                apiResponse.setMessage(Constants.USER_ID_NOT_EXIST);
            }
        } else {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            apiResponse.setMessage(Constants.EMPTY_USER_REQUEST);
        }

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(value = "/experience")
    public ResponseEntity<ApiResponse> createExperience(@RequestBody ExperienceRequest experienceRequest) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse();

        if (experienceRequest != null && StringUtils.isNotBlank(experienceRequest.getUserId())) {
            List<Experience> newExperience = experienceService.createExperience(experienceRequest);
            apiResponse.setResponse(newExperience);
            apiResponse.setStatus(HttpStatus.OK.toString());
            apiResponse.setMessage("new experience created");
        } else {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            apiResponse.setMessage(Constants.EMPTY_USER_REQUEST);
        }
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(value = "/experience")
    public ResponseEntity<ApiResponse> updateExperienceById(@RequestBody ExperienceRequest experienceRequest) throws JsonParseException {
        ApiResponse apiResponse = new ApiResponse();

        if (experienceRequest != null) {
            Experience updatedExperience = experienceService.updateExperienceByExperienceId(experienceRequest);
            apiResponse.setResponse(updatedExperience);
            apiResponse.setStatus(HttpStatus.OK.toString());
            apiResponse.setMessage("updated experience");
        } else {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            apiResponse.setMessage(Constants.EMPTY_USER_REQUEST);
        }
        return ResponseEntity.ok().body(apiResponse);
    }
}
