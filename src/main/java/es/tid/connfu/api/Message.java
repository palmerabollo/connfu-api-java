package es.tid.connfu.api;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Message {
    private String id;
    private String content;
    private String from;
    private String to;
    private ChannelType channel;
    private EventType event;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public ChannelType getChannel() {
        return channel;
    }
    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }
    public EventType getEvent() {
        return event;
    }
    public void setEvent(EventType event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
