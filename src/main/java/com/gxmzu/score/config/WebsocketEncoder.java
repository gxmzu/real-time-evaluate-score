package com.gxmzu.score.config;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.HashMap;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/25
 * @Description: Websocket编码器
 */
public class WebsocketEncoder implements Encoder.Text<HashMap> {

    private static final Logger log = LoggerFactory.getLogger(WebsocketEncoder.class);

    @Override
    public String encode(HashMap hashMap) throws EncodeException {
        try {
            return JSONObject.toJSONString(hashMap);
        } catch (Exception e) {
            log.error("Websocket编码器错误", e);
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
