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

    private <T> T encryptInstance(Object obj) throws Exception {
        return manager.encrypt((T)obj);
    }

    private <T> T encryptFields(Object obj) throws Exception {
        T instance = (T)obj.getClass().newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(SecurityField.class) != null) {
                if(ReflectionUtil.isCollectionType(field.get(obj))) {
                    Collection<?> collection = (Collection<?>) field.get(obj);
                    field.set(instance, (T) collection.stream()
                            .map(it -> getEncryptedObject(it))
                            .collect(Collectors.toList()));
                }else{
                    Object encrypted = manager.encrypt(field.get(obj));
                    field.set(instance, encrypted);
                }
            } else {
                field.set(instance, field.get(obj));
            }
        }
        return instance;
    }

    public <T> T getEncryptedObject(T obj) {
        if(obj == null) return obj;

        try {
            if (ReflectionUtil.isAbleToEncryptInstance(obj)) {
                return encryptInstance(obj);
            } else if(ReflectionUtil.isCollectionType(obj)) {
                Collection<?> result = (Collection<?>) obj;
                return (T) result.stream()
                        .map(it -> getEncryptedObject(it))
                        .collect(Collectors.toList());
            } else {
                return encryptFields(obj);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UnsupportedOperationException("Unable to encrypt: " + obj.getClass().getName());
        }
    }
}
