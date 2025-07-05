package com.devteria.profile.controller;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {

    UserProfileService userProfileService;

    @GetMapping("/users/{profileId}")
    UserProfileCreationResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }
    @GetMapping("/users")
    List<UserProfileCreationResponse> getAllUserProfile() {
        return userProfileService.getAllProfile();
    }
}
