package org.swcns.reflectivecipher.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swcns.reflectivecipher.annotation.SecurityField;
import org.swcns.reflectivecipher.core.EncryptionManager;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ObjectDecryptor {
    private final EncryptionManager manager;

    @SuppressWarnings("unchecked")
    private <T> T decryptInstance(Object obj) throws Exception {
        return manager.decrypt((T) obj);
    }

    protected <T> T decryptFields(T obj) throws Exception {
        //noinspection unchecked,deprecation
        T instance = (T) obj.getClass().newInstance();

        Field[] fields = instance.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(SecurityField.class) != null) {
                if (ReflectionUtil.isCollectionType(field.get(obj))) {
                    field.set(instance, castCollection((Collection<?>) field.get(obj)));
                } else {
                    T decrypted = decryptInstance(field.get(obj));
                    field.set(instance, decrypted);
                }
            } else {
                field.set(instance, field.get(obj));
            }
        }
        return instance;
    }

    public <T> T getDecryptedObject(T obj) {
        if(obj == null) return null;

        try {
            if(ReflectionUtil.isAbleToEncryptInstance(obj)) {
                return decryptInstance(obj);
            } else if(ReflectionUtil.isCollectionType(obj)) {
                Collection<?> result = (Collection<?>) obj;
                return castCollection(result);
            } else {
                return decryptFields(obj);
            }
        } catch (Exception ex) {
            log.error("unable to decrypt", ex);
            throw new UnsupportedOperationException("Unable to decrypt: " + obj.getClass().getName());
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T castCollection(Collection<?> collection) {
        return (T) collection.stream()
                .map(this::getDecryptedObject)
                .collect(Collectors.toList());
    }
}
