package child.leisure.rpc.server;

import child.leisure.rpc.annotation.InjectService;
import child.leisure.rpc.annotation.Service;
import child.leisure.rpc.client.ClientProxyFactory;
import child.leisure.rpc.server.net.RpcServer;
import child.leisure.rpc.server.register.ServiceObject;
import child.leisure.rpc.server.register.ServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * RPC处理者，支持服务启动暴漏，自动注入Service
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 18:39
 */
public class DefaultRpcProcessor implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRpcProcessor.class);

    @Resource
    private ClientProxyFactory clientProxyFactory;

    @Resource
    private ServiceRegister serviceRegister;

    @Resource
    private RpcServer rpcServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (Objects.isNull(event.getApplicationContext().getParent())) {
            ApplicationContext context = event.getApplicationContext();
            // 开启服务
            startServer(context);
            // 注入Service
            injectService(context);
        }
    }

    private void startServer(ApplicationContext context) {
        Map<String, Object> beans = context.getBeansWithAnnotation(Service.class);
        if (beans.size() == 0) {
            logger.warn("Here is no service.");
            return;
        }
        boolean startServerFlag = true;
        for (Object obj : beans.values()) {
            try {
                Class<?> clazz = obj.getClass();
                Class<?>[] interfaces = clazz.getInterfaces();
                ServiceObject so;
                if (interfaces.length != 1) {
                    Service service = clazz.getAnnotation(Service.class);
                    String value = service.value();
                    if (value.equals("")) {
                        startServerFlag = false;
                        throw new UnsupportedOperationException("The exposed interface is not specific with " + obj.getClass().getName());
                    }
                    so = new ServiceObject(value, Class.forName(value), obj);
                } else {
                    Class<?> supperClass = interfaces[0];
                    so = new ServiceObject(supperClass.getName(), supperClass, obj);
                }
                // 注册服务
                serviceRegister.register(so);
            } catch (Exception e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
        if (startServerFlag) {
            rpcServer.start();
        }
    }

    private void injectService(ApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            Class<?> clazz = context.getType(name);
            if (Objects.isNull(clazz)) continue;
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                InjectService injectService = field.getAnnotation(InjectService.class);
                if (Objects.isNull(injectService)) continue;
                Class<?> fieldClass = field.getType();
                Object object = context.getBean(name);
                field.setAccessible(true);
                try {
                    field.set(object, clientProxyFactory.getProxy(fieldClass));
                } catch (IllegalAccessException e) {
                    logger.error("{}.", e.getMessage(), e);
                }
            }
        }
    }

}
