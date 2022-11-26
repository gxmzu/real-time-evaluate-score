package com.gxmzu.score.controller;

import com.gxmzu.score.config.WebsocketEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/25
 * @Description: 评分Websocket
 */
@ServerEndpoint(value = "/websocket/score", encoders = {WebsocketEncoder.class})
@Component
public class ScoreWebsocket{

    private static final Logger log = LoggerFactory.getLogger(ScoreWebsocket.class);

    @OnMessage
    public void onMessage(String str, Session session) throws IOException {
        session.getBasicRemote().sendText(str);
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("websocket已连接");
    }

    @OnClose
    public void onClose(Session session) throws IOException {

    }
}
