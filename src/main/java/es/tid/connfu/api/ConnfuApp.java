package es.tid.connfu.api;

import java.beans.Beans;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.reflections.Reflections;

import es.tid.connfu.utils.SecurityUtils;

public class ConnfuApp {
    private PropertyChangeSupport handlers = new PropertyChangeSupport(this);

    private static final String CONNFU_ENDPOINT = "https://stream.connfu.com/connfu-stream-testing-emc2";

    private String token;

    public ConnfuApp(String token) {
        this.token = token;
        findListeners();
    }

    private void findListeners() {
        try {
            Reflections reflections = new Reflections(""); // search from root package
            Set<Class<?>> listenerClazzes = reflections.getTypesAnnotatedWith(Listener.class);

            for (Class<?> listenerClazz : listenerClazzes) {
                Object o = Beans.instantiate(listenerClazz.getClassLoader(), listenerClazz.getName());

                Listener listener = listenerClazz.getAnnotation(Listener.class);
                ChannelType channelType = listener.channel();

                handlers.addPropertyChangeListener(channelType.name(), new IncomingMessageListener(o));
                System.out.println("Found listener " + listenerClazz + " for channel " + channelType);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate listeners", e);
        }
    }

    public void start() throws ChannelException {
        try {
            HttpsURLConnection conn = openEndpointConnection();

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String line = null;
            while ((line = br.readLine()) != null) {
                handleMessage(line);
            }

            conn.getInputStream().close();
        } catch (Exception e) {
            throw new ChannelException("An IO problem was found", e);
        }
    }

    private HttpsURLConnection openEndpointConnection() throws Exception {
        System.out.println("Open connection with token " + token);

        SecurityUtils.trustEveryone();

        URL url = new URL(CONNFU_ENDPOINT);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Backchat " + token);

        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }

    private void handleMessage(String line) throws Exception {
        Message message = MessageParser.parse(line);
        handlers.firePropertyChange(message.getChannel().name(), null, message);
    }
}
