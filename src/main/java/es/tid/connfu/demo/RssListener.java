package es.tid.connfu.demo;

import es.tid.connfu.api.ChannelType;
import es.tid.connfu.api.EventHandler;
import es.tid.connfu.api.Listener;
import es.tid.connfu.api.Message;

@Listener(channel=ChannelType.RSS)
public class RssListener {
    @EventHandler
    public void on(Message message) {
        System.out.println(message);
    }
}
