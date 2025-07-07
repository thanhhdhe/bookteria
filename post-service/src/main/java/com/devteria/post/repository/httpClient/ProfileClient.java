package com.devteria.post.repository.httpClient;

import com.devteria.post.dto.ApiResponse;
import com.devteria.post.dto.response.UserProfileCreationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", url = "${app.services.profile.url}")
public interface ProfileClient {

    @GetMapping("/internal/users/{userId}")
    ApiResponse<UserProfileCreationResponse> getUserProfile(@PathVariable String userId);

}
