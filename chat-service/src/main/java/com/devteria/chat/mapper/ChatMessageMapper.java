package com.devteria.chat.mapper;

import com.devteria.chat.dto.request.ChatMessageRequest;
import com.devteria.chat.dto.response.ChatMessageResponse;
import com.devteria.chat.entity.ChatMessage;
import com.devteria.chat.entity.Conversation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);
    ChatMessage  toChatMessage(ChatMessageRequest chatMessageRequest);
    List<ChatMessageResponse> toChatMessageResponse(List<ChatMessage> chatMessages);
}
