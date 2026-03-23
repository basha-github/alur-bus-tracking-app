package com.example.demo.handler;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MultiBus_LocationWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> busSessions = new ConcurrentHashMap<>();
    private final Map<String, List<WebSocketSession>> busSubscribers = new ConcurrentHashMap<>();
    
   

    private final Map<String, List<WebSocketSession>> adminSessions = new ConcurrentHashMap<>();
    
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 🔌 CONNECT
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Map<String, String> params = getQueryParams(session.getUri());

        String type = params.get("type");   // bus / parent
        String busId = params.get("busId");

        if ("bus".equals(type)) {
            busSessions.put(busId, session);
            System.out.println("🚌 Bus connected: " + busId);
        } else if ("parent".equals(type)) {
            busSubscribers
                    .computeIfAbsent(busId, k -> new CopyOnWriteArrayList<>())
                    .add(session);
            System.out.println("👨‍👩‍👧 Parent subscribed to bus: " + busId);
        }
        else if ("admin".equals(type)) {
        	 System.out.println("for admin:::::::::::::::::UMAR RA:::::::::::;");
        	adminSessions
                    .computeIfAbsent(busId, k -> new CopyOnWriteArrayList<>())
                    .add(session);
            System.out.println("👨‍👩‍👧 Admin subscribed to bus: " + busId);
        }
        
        
       
    }
    

    // 📩 MESSAGE HANDLING
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        System.out.println("📩 Received: " + message.getPayload());

        // Parse JSON
        Map<String, Object> data = objectMapper.readValue(message.getPayload(), Map.class);
        String busId = (String) data.get("busId");
        //String typeVal = (String) data.get("type");
        //System.out.println(" typeVal....."+typeVal);
        
        System.out.println("before sending to Parent....busId--->"+busId);

        if (busId == null) return;

        //if (typeVal.contains("ping")) {
          //  System.out.println("  returning!!!!!!!");
           // return;
        //}
        // Send only to parents of that bus
        List<WebSocketSession> parents = busSubscribers.get(busId);

        if (parents != null) {
        	 System.out.println("found parent!!!!!");
            for (WebSocketSession parent : parents) {
                if (parent.isOpen()) {
                	 System.out.println("parent connection is opened!!!!!!!!!!!");
                    parent.sendMessage(new TextMessage(message.getPayload()));
                    System.out.println("successfully send meaasge--->");
                    System.out.println(new TextMessage(message.getPayload()));
                }
            }
        }// parent
        
        
        List<WebSocketSession> admin = adminSessions.get(busId);
        if (admin != null) {
       	 System.out.println("Bus found for Admin !!!!!");
           for (WebSocketSession eachBus : admin) {
               if (eachBus.isOpen()) {
               	 System.out.println("eachBus connection is opened!!!!!!!!!!!");
               	eachBus.sendMessage(new TextMessage(message.getPayload()));
                   System.out.println("successfully send meaasge---> for bus Id-->"+busId);
                   System.out.println(new TextMessage(message.getPayload()));
               }
           }
       }// parent
    }

    // ❌ DISCONNECT
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        // Remove from bus sessions
        busSessions.values().remove(session);

        // Remove from parent lists
        busSubscribers.values().forEach(list -> list.remove(session));

        adminSessions.values().forEach(list -> list.remove(session));

        System.out.println("❌ Disconnected: " + session.getId());
    }

    // 🔧 Helper to parse query params
    private Map<String, String> getQueryParams(URI uri) {
        Map<String, String> map = new HashMap<>();

        if (uri.getQuery() == null) return map;

        String[] params = uri.getQuery().split("&");
        for (String param : params) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }
}