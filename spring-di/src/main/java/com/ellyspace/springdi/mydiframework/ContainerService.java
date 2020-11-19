package com.ellyspace.springdi.mydiframework;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerService {

    public static <T> T getObject(Class<T> classType) {
        T instance = createInstance(classType);
        Arrays.stream(classType.getDeclaredFields())
                .forEach(field -> {
                    Inject annotation = field.getAnnotation(Inject.class);
                    if (annotation != null) {
                        Object fieldInstance = createInstance(field.getType());
                        field.setAccessible(true); //public이 아닐 수도 있으니까
                        try {
                            field.set(instance, fieldInstance);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
