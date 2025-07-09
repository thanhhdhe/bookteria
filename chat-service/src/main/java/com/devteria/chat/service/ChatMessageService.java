package com.devteria.chat.service;

import com.devteria.chat.dto.request.ChatMessageRequest;
import com.devteria.chat.dto.response.ChatMessageResponse;
import com.devteria.chat.entity.ChatMessage;
import com.devteria.chat.entity.Conversation;
import com.devteria.chat.entity.ParticipantInfo;
import com.devteria.chat.exception.AppException;
import com.devteria.chat.exception.ErrorCode;
import com.devteria.chat.mapper.ChatMessageMapper;
import com.devteria.chat.repository.ChatMessageRepository;
import com.devteria.chat.repository.ConversationRepository;
import com.devteria.chat.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageService {
    ChatMessageRepository chatMessageRepository;
    ChatMessageMapper chatMessageMapper;
    ConversationRepository  conversationRepository;
    ProfileClient profileClient;

    public List<ChatMessageResponse> getMessages(String conversationId) {
        //validate conversation
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //validate conversation id
        var conversation = conversationRepository.findById(conversationId).orElseThrow(
                () -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND)
        );
        conversation.getParticipants().stream().filter(participantInfo -> userId.equals(participantInfo.getUserId()))
                .findAny().orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        var messages = chatMessageRepository.findAllByConversationIdOrderByCreatedDateDesc(conversationId);
        return messages.stream().map(this::toChatMessageResponse).toList();
    }
    public ChatMessageResponse create(ChatMessageRequest request){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //validate conversation id
        var conversation = conversationRepository.findById(request.getConversationId()).orElseThrow(
                () -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND)
        );
        conversation.getParticipants().stream().filter(participantInfo -> userId.equals(participantInfo.getUserId()))
                .findAny().orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));
        //Get user info profile
        var userResponse = profileClient.getProfile(userId);
        if(Objects.isNull(userResponse)){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        var userInfo = userResponse.getResult();
        //Build message
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(request);
        chatMessage.setSender(ParticipantInfo.builder()
                        .userId(userInfo.getUserId())
                        .username(userInfo.getUsername())
                        .firstName(userInfo.getFirstName())
                        .lastName(userInfo.getLastName())
                        .avatar(userInfo.getAvatar())
                .build());
        chatMessage.setCreatedDate(Instant.now());

        //create chat message
        chatMessage = chatMessageRepository.save(chatMessage);
        //Convert to response
        return  toChatMessageResponse(chatMessage);
    }

    private ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);
        chatMessageResponse.setMe(userId.equals(chatMessage.getSender().getUserId()));
        return chatMessageResponse;
    }
}
