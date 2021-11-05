package com.springframework.extensionpoint.scan;

import com.springframework.extensionpoint.model.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 扩展点扫描并注册为bean
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 15:26
 */
public class ExtensionPointScannerRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取扫包路径
        Set<String> scanPackages = getScanPackages(importingClassMetadata);
        // 将包路径下的类注册为bean
        doScanAndRegistryBean(registry, scanPackages);
    }

    /**
     * 获取扫描包路径
     */
    private Set<String> getScanPackages(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(ExtensionPointScan.class.getName()));
        if (attributes == null) {
            return new HashSet<>();
        }
        return Arrays.stream(attributes.getStringArray("basePackages")).collect(Collectors.toSet());
    }

    /**
     * 扫描并注册为bean
     */
    private void doScanAndRegistryBean(BeanDefinitionRegistry beanDefinitionRegistry, Set<String> scanPackages) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.addIncludeFilter(new AssignableTypeFilter(IExtensionPoint.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(RouterStrategy.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(ResultStrategy.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(ExceptionStrategy.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(RouterFeatureStrategy.class));
        scanner.scan(scanPackages.toArray(new String[]{}));
    }
}
