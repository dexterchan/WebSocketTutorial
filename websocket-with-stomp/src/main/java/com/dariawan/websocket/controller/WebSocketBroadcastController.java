package com.dariawan.websocket.controller;



import com.dariawan.websocket.dto.ChatMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class WebSocketBroadcastController {

    @GetMapping("/stomp-broadcast")
    public String getWebSocketBroadcast() {
        return "stomp-broadcast";
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public ChatMessage send(ChatMessage chatMessage) throws Exception {
        return new ChatMessage(chatMessage.getFrom(), chatMessage.getText(), "ALL");
    }

}