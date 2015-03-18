package com.syouth.iocontainer;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by syouth on 12.03.15.
 */
public class IoCContainer {
    private static HashMap<AbstractMap.SimpleEntry<Class, Class>, AbstractMap.SimpleEntry<Object[], Object>> clazzObjectMap =
            new HashMap<AbstractMap.SimpleEntry<Class, Class>, AbstractMap.SimpleEntry<Object[], Object>>();

    private static<T> Object checkCreatedAndCreateIfNeeded(Class<T> clazz) throws Exception {
        // Check already created
        Map.Entry<AbstractMap.SimpleEntry<Class, Class>, AbstractMap.SimpleEntry<Object[], Object>> found = null;
        for (Map.Entry<AbstractMap.SimpleEntry<Class, Class>, AbstractMap.SimpleEntry<Object[], Object>> e : clazzObjectMap.entrySet()) {
            if (e.getKey().getKey().equals(clazz)) {
                found = e;
                if (e.getValue().getValue() != null) {
                    return e.getValue().getValue();
                } else {
                    break;
                }
            }
        }
        if (found == null) {
            throw new Exception("Can not resolve unregistered class");
        }

        // Create
        for (Constructor c : found.getKey().getValue().getConstructors()) {
            if (c.getAnnotation(ResolveAnnotation.class) != null) {
                Object o = c.newInstance(found.getValue().getKey());
                clazzObjectMap.put(new AbstractMap.SimpleEntry<Class, Class>(found.getKey().getKey(), found.getKey().getValue()),
                        new AbstractMap.SimpleEntry<Object[], Object>(null, o));
                return  o;
            }
        }

        throw new Exception("Can not find constructor with ResolveAnnotation");
    }

    synchronized public static<T> void register(Class<T> clazz, Object ... args) throws Exception {
        register(clazz, clazz, args);
    }

    synchronized public static<T, TT extends T> void register(Class<T> clazz, Class<TT> binded, Object ... args) throws Exception {
        if (clazzObjectMap.containsKey(new AbstractMap.SimpleEntry<Class, Class>(clazz, binded))) {
            // Can not register already registered clazz
            throw new Exception("Can not register registered class");
        } else {
            clazzObjectMap.put(new AbstractMap.SimpleEntry<Class, Class>(clazz, binded),
                    new AbstractMap.SimpleEntry<Object[], Object>(args, null));
        }
    }

    synchronized public static<T> T resolve(Class<T> clazz) throws Exception {
        return (T)checkCreatedAndCreateIfNeeded(clazz);
    }
}
