package com.example.blog_system.Config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
import org.springframework.stereotype.Component;  
  
@Component  
public class BeanFetcher implements ApplicationContextAware {  
  
    private static ApplicationContext applicationContext;  
  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        BeanFetcher.applicationContext = applicationContext;  
    }  
  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    public static <T> T getBean(Class<T> clazz) {  
        return applicationContext.getBean(clazz);  
    }  
}