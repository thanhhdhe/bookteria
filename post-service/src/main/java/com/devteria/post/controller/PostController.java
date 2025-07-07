package com.devteria.post.controller;

import com.devteria.post.dto.ApiResponse;
import com.devteria.post.dto.PageResponse;
import com.devteria.post.dto.request.PostRequest;
import com.devteria.post.dto.response.PostResponse;
import com.devteria.post.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @PostMapping("/create")
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.create(postRequest))
                .build();
    }

    @GetMapping("/my-posts")
    public ApiResponse<PageResponse<PostResponse>> getMyPost(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .result(postService.findAllByUserId(page, size))
                .build();
    }
}