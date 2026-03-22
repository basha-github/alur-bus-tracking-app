package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.demo.handler.LocationWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements  WebSocketConfigurer {

	private final LocationWebSocketHandler handler;

    public WebSocketConfig(LocationWebSocketHandler handler) {
        this.handler = handler;
    }

	
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/ws")
                .setAllowedOrigins("*");
    }

       

    
}
