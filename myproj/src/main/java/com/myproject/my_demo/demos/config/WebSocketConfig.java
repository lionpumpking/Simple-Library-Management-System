package com.myproject.my_demo.demos.config;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;

/**
 * Description:
 * Author:
 * Data:
 */

@Configuration
public class WebSocketConfig {

@Bean
    public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
}

}