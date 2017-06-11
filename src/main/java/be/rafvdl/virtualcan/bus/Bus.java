package be.rafvdl.virtualcan.bus;

import be.rafvdl.virtualcan.ecu.ECU;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bus {

    private Map<Integer, List<Handler>> listeners = new HashMap<>();

    private static class Handler {
        private Object object;
        private Method method;

        private Handler(Object object, Method method) {
            this.object = object;
            this.method = method;
        }

    }

    public boolean addDevice(Object listener) {
        if (!listener.getClass().isAnnotationPresent(ECU.class))
            throw new IllegalArgumentException();
        for (Method m : listener.getClass().getMethods()) {
            Listener aListener = m.getAnnotation(Listener.class);
            if (aListener == null)
                continue;
            if (m.getParameterCount() != 1 && m.getParameterTypes()[0].equals(Message.class))
                throw new IllegalArgumentException();
            listeners.putIfAbsent(aListener.value(), new ArrayList<>());
            listeners.get(aListener.value()).add(new Handler(listener, m));
        }
        return false;
    }

    public boolean broadcast(Message message) {
        listeners.get(message.getId()).forEach((handler -> {
            try {
                handler.method.invoke(handler.object, message);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }));
        return true;
    }

    public InputStream getInputStream() {
        return null;
    }

    public OutputStream getOutputStream() {
        return null;
    }
}
