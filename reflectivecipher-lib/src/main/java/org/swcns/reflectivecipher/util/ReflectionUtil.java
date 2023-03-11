package org.swcns.reflectivecipher.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.Collection;

public class ReflectionUtil {
    public static boolean isClassContainsIn(Object obj, Class[] classes) {
        if(obj == null) return false;
        for(Class clazz : classes) {
            if(obj.getClass().isAssignableFrom(clazz)) return true;
        }
        return false;
    }

    /**
     * 자체로 암호화가 가능한 클래스(String, byte[])인지 확인합니다
     * @param obj
     * @return
     */
    public static boolean isAbleToEncryptInstance(Object obj) {
        return isBinaryType(obj) || isCharacterType(obj);
    }

    /**
     * String 계열인지 확인합니다
     * @param obj
     * @return
     */
    public static boolean isCharacterType(Object obj) {
        if(obj == null) return false;

        final Class<?>[] qualified = new Class[] { String.class };
        return isClassContainsIn(obj, qualified);
    }

    /**
     * 바이너리 계열인지 확인합니다
     * @param obj
     * @return
     */
    public static boolean isBinaryType(Object obj) {
        if(obj == null) return false;

        final Class<?>[] qualified = new Class[] { byte[].class, Byte[].class };
        return isClassContainsIn(obj, qualified);
    }

    /**
     * 해당 파라미터가 특정 어노테이션을 지니고 있는지 확인합니다
     * @param paramPosition 파라미터의 위치
     * @param joinPoint joinPoint
     * @param annotation 찾을 어노테이션 클래스
     * @return
     */
    public static boolean isParamHasAnnotation(int paramPosition, ProceedingJoinPoint joinPoint, Class<?> annotation) {
        return Arrays.stream(((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations()[paramPosition])
                .anyMatch(it -> it.annotationType().isAssignableFrom(annotation));
    }

    public static <T> boolean isCollectionType(T obj) {
        if(obj == null) return false;
        return Collection.class.isAssignableFrom(obj.getClass());
    }
}
