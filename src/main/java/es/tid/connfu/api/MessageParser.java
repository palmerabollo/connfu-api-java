package es.tid.connfu.api;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class MessageParser {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Message parse(String text) throws IOException {
        Map messageMap = mapper.readValue(text, Map.class);

        Message message = new Message();
        message.setChannel(ChannelType.RSS);
        message.setContent(getField(messageMap, "content"));
        message.setFrom(getField(messageMap, "from"));
        message.setTo(getField(messageMap, "to"));
        message.setId(getField(messageMap, "id"));

        message.setEvent(EventType.NEW); // TODO support voice ...

        return message;
    }

    private static String getField(Map messageMap, String field) {
        if (messageMap.containsKey(field)) {
            return messageMap.get(field).toString();
        } else {
            return null;
        }
    }
}
