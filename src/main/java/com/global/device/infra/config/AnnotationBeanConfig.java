package com.global.device.infra.config;

import com.global.device.infra.annotations.UseCase;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Configuration
public class AnnotationBeanConfig implements BeanFactoryPostProcessor {
	
	public AnnotationBeanConfig() {
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Reflections reflections = new Reflections("com.global.device", new Scanner[0]);
		Set<Class<?>> lsComponent = new HashSet<>();
		List<Class<? extends Annotation>> lsAnnotations = List.of(UseCase.class);
		
		lsAnnotations.forEach((annotation) -> lsComponent.addAll(reflections.getTypesAnnotatedWith(annotation)));
		
		lsComponent.forEach((component) -> {
			GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
			genericBeanDefinition.setBeanClass(component);
			((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(component.getSimpleName(),
					genericBeanDefinition);
		});
	}
	
}
