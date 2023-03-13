package org.swcns.reflectivecipher.util;

import lombok.RequiredArgsConstructor;
import org.swcns.reflectivecipher.annotation.SecurityField;
import org.swcns.reflectivecipher.core.EncryptionManager;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ObjectEncryptor {
    private final EncryptionManager manager;

    @SuppressWarnings("unchecked")
    private <T> T encryptInstance(Object obj) throws Exception {
        return manager.encrypt((T) obj);
    }

    protected <T> T encryptFields(T obj) throws Exception {
        //noinspection unchecked,deprecation
        T instance = (T) obj.getClass().newInstance();

        Field[] fields = instance.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(SecurityField.class) != null) {
                if(ReflectionUtil.isCollectionType(field.get(obj))) {
                    field.set(instance, castCollection((Collection<?>) field.get(obj)));
                } else {
                    T encrypted = encryptInstance(field.get(obj));
                    field.set(instance, encrypted);
                }
            } else {
                field.set(instance, field.get(obj));
            }
        }
        return instance;
    }

    public <T> T getEncryptedObject(T obj) {
        if(obj == null) return null;

        try {
            if (ReflectionUtil.isAbleToEncryptInstance(obj)) {
                return encryptInstance(obj);
            } else if(ReflectionUtil.isCollectionType(obj)) {
                Collection<?> result = (Collection<?>) obj;
                return castCollection(result);
            } else {
                return encryptFields(obj);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UnsupportedOperationException("Unable to encrypt: " + obj.getClass().getName());
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T castCollection(Collection<?> collection) {
        return (T) collection.stream()
                .map(this::getEncryptedObject)
                .collect(Collectors.toList());
    }
}
