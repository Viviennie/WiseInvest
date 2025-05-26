package com.seme.wiseinvest.chatbot.controller;

import com.seme.wiseinvest.chatbot.domain.dto.ChatRequestDTO;
import com.seme.wiseinvest.chatbot.domain.vo.ChatReplyVO;
import com.seme.wiseinvest.chatbot.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatbotController {
    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public ChatReplyVO chat(@RequestBody ChatRequestDTO requestDTO) {
        return chatbotService.getReply(requestDTO);
    }
}