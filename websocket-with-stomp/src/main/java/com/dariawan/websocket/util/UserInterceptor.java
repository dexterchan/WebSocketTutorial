package com.dariawan.websocket.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class UserInterceptor implements ChannelInterceptor {
    private final String rejectUser = "intruder";
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message
                    .getHeaders()
                    .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            if (raw instanceof Map) {
                Object names = ((Map) raw).get("username");
                if (names instanceof List) {
                    List<String> userNameLst = (List<String>)names;
                    if (userNameLst.size()==0){
                        throw new RuntimeException("no user");
                    }
                    String userName = userNameLst.get(0);
                    if (!userName.equals(rejectUser))
                        accessor.setUser(new User(userName));
                    else
                        throw new RuntimeException(String.format("user %s is not found", userName));
                }
            }
        }
        return message;

    }
}
