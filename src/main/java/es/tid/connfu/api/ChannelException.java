package es.tid.connfu.api;

import java.io.IOException;

public class ChannelException extends IOException {
    public ChannelException(String msg){
        super(msg);
    }

    public ChannelException(String msg, Throwable t){
        super(msg, t);
    }
}
