package com.example.demo.handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class LocationWebSocketHandler_Single extends TextWebSocketHandler {
	
	private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


	@Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("✅ Client connected: " + session.getId());
    }

    // 🔥 THIS is where you write your logic
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    	
    	 System.out.println("📩 Received: " + message.getPayload());

         // 🔥 Broadcast to ALL clients (driver + parent)
         for (WebSocketSession s : sessions) {
             if (s.isOpen()) {
                 s.sendMessage(new TextMessage(message.getPayload()));
             }
         }
    }

    // 🔥 Called when client disconnects
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("❌ Client Disconnected: " + session.getId());
        sessions.remove(session);
    }
}