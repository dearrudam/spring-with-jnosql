package com.github.dearrudam.springwithjnosqlmongodb;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Qualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class WeldAutowireCandidateResolver extends ContextAnnotationAutowireCandidateResolver {

    private final static Logger logger = LoggerFactory.getLogger(WeldAutowireCandidateResolver.class);

    @Override
    public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, String beanName) {
        Object result = super.getLazyResolutionProxyIfNecessary(descriptor, beanName);
        if (result == null) {
            try {
                Class<?> rawClass = descriptor.getResolvableType().getRawClass();
                Annotation[] qualifierAnnotations = retainJakartaQualifierAnnotationsOnly(descriptor.getAnnotations());
                Instance<?> instance = CDI.current().select(rawClass,qualifierAnnotations);
                if (instance.isResolvable()) {
                    result = instance.get();
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        return result;
    }

    private Annotation[] retainJakartaQualifierAnnotationsOnly(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType().getAnnotation(Qualifier.class) != null)
                .toArray(Annotation[]::new);
    }
}
