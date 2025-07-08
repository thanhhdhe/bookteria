package com.devteria.profile.controller;

import com.devteria.profile.dto.ApiResponse;
import com.devteria.profile.dto.request.SearchUserRequest;
import com.devteria.profile.dto.request.UpdateProfileRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/users/my-profile")
    ApiResponse<UserProfileCreationResponse> getMyProfile() {
        return ApiResponse.<UserProfileCreationResponse>builder()
                .result(userProfileService.getMyProfile())
                .build();
    }

    @PutMapping("/users/my-profile")
    ApiResponse<UserProfileCreationResponse> updateMyProfile(@RequestBody UpdateProfileRequest request) {
        return ApiResponse.<UserProfileCreationResponse>builder()
                .result(userProfileService.updateMyProfile(request))
                .build();
    }

    @PutMapping("/users/avatar")
    ApiResponse<UserProfileCreationResponse> updateMyAvatar(@RequestParam("file") MultipartFile file) {
        return ApiResponse.<UserProfileCreationResponse>builder()
                .result(userProfileService.updateAvatar(file))
                .build();
    }

    @PostMapping("/users/search")
    ApiResponse<List<UserProfileCreationResponse>> search(@RequestBody SearchUserRequest request) {
        return ApiResponse.<List<UserProfileCreationResponse>>builder()
                .result(userProfileService.search(request))
                .build();
    }
}
