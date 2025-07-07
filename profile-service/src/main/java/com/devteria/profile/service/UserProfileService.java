package com.devteria.profile.service;


import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.exception.AppException;
import com.devteria.profile.exception.ErrorCode;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper  userProfileMapper;

     public UserProfileCreationResponse createProfile(UserProfileCreationRequest request){
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile =  userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileCreationResponse(userProfile);
    }

    public UserProfileCreationResponse getProfile(String id){
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileCreationResponse(userProfile);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileCreationResponse> getAllProfile(){
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toUserProfileCreationResponse)
                .toList();
    }
    public UserProfileCreationResponse getMyProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        var profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userProfileMapper.toUserProfileCreationResponse(profile);
    }
}
