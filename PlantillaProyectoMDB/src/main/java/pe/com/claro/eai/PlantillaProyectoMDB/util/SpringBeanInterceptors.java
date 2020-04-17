package pe.com.claro.eai.PlantillaProyectoMDB.util;

import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

public class SpringBeanInterceptors extends SpringBeanAutowiringInterceptor {

    @Override
    protected BeanFactoryLocator getBeanFactoryLocator(Object target) {

        return ContextSingletonBeanFactoryLocator.getInstance();
    }

}

