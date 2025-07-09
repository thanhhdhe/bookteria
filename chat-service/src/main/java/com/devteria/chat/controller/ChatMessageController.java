package com.devteria.chat.controller;

import com.devteria.chat.dto.ApiResponse;
import com.devteria.chat.dto.request.ChatMessageRequest;
import com.devteria.chat.dto.response.ChatMessageResponse;
import com.devteria.chat.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageController {
    ChatMessageService chatMessageService;

    @PostMapping("create")
    public ApiResponse<ChatMessageResponse> create(@RequestBody ChatMessageRequest chatMessageRequest) {
        return ApiResponse.<ChatMessageResponse>builder()
                .result(chatMessageService.create(chatMessageRequest))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ChatMessageResponse>> getMessages(@RequestParam("conversationId") String conversationId) {
        return ApiResponse.<List<ChatMessageResponse>>builder()
                .result(chatMessageService.getMessages(conversationId))
                .build();

    }

}
