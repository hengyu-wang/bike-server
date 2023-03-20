package com.example.demo.config;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

//当new出一个线程时，子线程不能使用spring容器中的JPA服务，需要手动获取

@Configuration
public class SpringBeanUtil implements ApplicationContextAware{

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.context = applicationContext;
    }

    public static Object getBean(String className){
        if (context==null) {
            System.out.println("context==null");
            return null;
        }
        else
            return context.getBean(className);
    }
    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
    public static ApplicationContext getApplicationContext(){
        return context;
    }

}
