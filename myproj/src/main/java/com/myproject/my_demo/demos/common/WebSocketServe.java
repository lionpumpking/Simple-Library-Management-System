package com.myproject.my_demo.demos.common;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import nonapi.io.github.classgraph.json.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.server.WebSocketService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Author:
 * Data:
 */

@ServerEndpoint(value="/ws/{id}")
@Component
public class WebSocketServe {

    private static Map<String ,Session> sessions = new HashMap();


    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        System.out.println(id + " opened");
        sessions.put(id, session);
    }

    @OnClose
    public void onClose() {
        System.out.println(sessions.get("id")+ " closed");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try{
            System.out.println(message);
            session.getBasicRemote().sendText("收到"+sessions.get("id")+"的消息");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}