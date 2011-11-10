package es.tid.connfu.api;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class MessageParser {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Message parse(String text) throws IOException {
        JsonNode json = mapper.readTree(text);

        // TODO parse different kind of messages

        Message message = new Message();
        message.setChannel(ChannelType.RSS);
        message.setContent(getField(json, "message"));
        message.setFrom(getField(json, "from"));
        message.setTo(getField(json, "to"));
        message.setId(getField(json, "id"));

        message.setEvent(EventType.NEW); // TODO support voice ...

        return message;
    }

    private static String getField(JsonNode json, String field) {
        return json.findPath(field).getTextValue();
    }
}
