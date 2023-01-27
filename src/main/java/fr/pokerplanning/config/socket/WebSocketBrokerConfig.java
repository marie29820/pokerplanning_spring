package fr.pokerplanning.config.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        log.debug("Creation des sockets");
        // - Message Mapping URL
        registry.setApplicationDestinationPrefixes("/room");
        // - Message Broker topic for pushing messages to the UI back.
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.debug("Creation du listener handshake");
        // - handshake
        registry.addEndpoint("/stomp").setAllowedOriginPatterns("*").withSockJS();
    }
}
