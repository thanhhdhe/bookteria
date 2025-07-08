package com.devteria.profile.mapper;

import com.devteria.profile.dto.request.UpdateProfileRequest;
import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest userProfileCreationRequest);
    UserProfileCreationResponse toUserProfileCreationResponse(UserProfile userProfile);
    void update(@MappingTarget UserProfile entity, UpdateProfileRequest request);
}
