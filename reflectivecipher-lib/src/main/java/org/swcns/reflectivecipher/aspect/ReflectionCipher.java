package org.swcns.reflectivecipher.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.swcns.reflectivecipher.annotation.SecurityParam;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;
import org.swcns.reflectivecipher.util.ReflectionUtil;

@Aspect
@RequiredArgsConstructor
public class ReflectionCipher {
    private final ObjectEncryptor encryptor;
    private final ObjectDecryptor decryptor;

    @Around("@annotation(org.swcns.reflectivecipher.annotation.EncryptParams)")
    public Object encryptParams(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for(int i = 0; i < args.length; i++) {
            if(ReflectionUtil.isParamHasAnnotation(i, joinPoint, SecurityParam.class))
                args[i] = encryptor.getEncryptedObject(args[i]);
        }

        return joinPoint.proceed(args);
    }

    @Around("@annotation(org.swcns.reflectivecipher.annotation.EncryptReturns)")
    public Object encryptReturns(ProceedingJoinPoint joinPoint) throws Throwable {
        return encryptor.getEncryptedObject(joinPoint.proceed(joinPoint.getArgs()));
    }

    @Around("@annotation(org.swcns.reflectivecipher.annotation.DecryptParams)")
    public Object decryptParams(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for(int i = 0; i < args.length; i++) {
            if(ReflectionUtil.isParamHasAnnotation(i, joinPoint, SecurityParam.class))
                args[i] = decryptor.getDecryptedObject(args[i]);
        }

        return joinPoint.proceed(args);
    }

    @Around("@annotation(org.swcns.reflectivecipher.annotation.DecryptReturns)")
    public Object decryptReturns(ProceedingJoinPoint joinPoint) throws Throwable {
        return decryptor.getDecryptedObject(joinPoint.proceed(joinPoint.getArgs()));
    }
}
