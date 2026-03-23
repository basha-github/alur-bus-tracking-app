package com.example.demo.session.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.WebSocketSession;

public class SessionManager {
	
	
	 public static final Map<String, WebSocketSession> busSessions = new ConcurrentHashMap<>();
	 public static final Map<String, List<WebSocketSession>> busSubscribers = new ConcurrentHashMap<>();
	    
	   

	 public static final List<WebSocketSession> adminSessions = new CopyOnWriteArrayList<>();


}
