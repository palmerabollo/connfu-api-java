package es.tid.connfu.api;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

public class IncomingMessageListener implements PropertyChangeListener {
    private Object listener = null;

    public IncomingMessageListener(Object listener) {
        this.listener = listener;
    }

    public void propertyChange(PropertyChangeEvent ev) {
        Message message = (Message) ev.getNewValue();

        try {
            // XXX Find @EventHandler methods, could be cached in a Map. That
            // would drastically improve the performance.
            Method[] methods = listener.getClass().getMethods();
            for (Method method : methods) {
                EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                if (eventHandler != null) {
                    EventType eventType = eventHandler.event();
                    if (eventType == message.getEvent()) {
                        method.invoke(listener, message);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to deliver message : " + e.getMessage());
        }
    }
}
