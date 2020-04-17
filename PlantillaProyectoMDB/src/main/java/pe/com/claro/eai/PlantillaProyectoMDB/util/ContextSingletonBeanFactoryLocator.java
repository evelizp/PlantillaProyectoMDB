package pe.com.claro.eai.PlantillaProyectoMDB.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

public class ContextSingletonBeanFactoryLocator extends SingletonBeanFactoryLocator{
    
    public static transient Logger logger = Logger.getLogger( ContextSingletonBeanFactoryLocator.class );
    
    @Override
    protected BeanFactory createDefinition( String resourceLocation, String factoryKey ){

              final ClassPathXmlApplicationContext objAplicationContext = new ClassPathXmlApplicationContext( new String[]{ resourceLocation }, false );
              final ClassLoader                    objClassLoader       = this.getClass().getClassLoader();
                
              objAplicationContext.setClassLoader( objClassLoader );
              
              return objAplicationContext;
    }

    private static final String UBICACION_EJB_SPRING_CONTEXT = "classpath*:beanRefContext.xml";
    
    private static final Map<Object, Object> tablaInstancias = new HashMap<Object, Object>();  


   /**
    * Returns an instance which uses the default "classpath*:beanEjbSpringRefContext.xml", as
    * the name of the definition file(s). All resources returned by the current
    * thread's context classloader's getResources() method with this name will be
    * combined to create a definition, which is just a BeanFactory.
    */
    public static BeanFactoryLocator getInstance() throws BeansException{
           BeanFactoryLocator objFactoryLocator = getInstance( UBICACION_EJB_SPRING_CONTEXT ); 
           logger.debug( "objFactoryLocator [Instancia]#1: " + objFactoryLocator );

           return objFactoryLocator;
    }

   /**
    * Returns an instance which uses the the specified selector, as the name of the
    * definition file(s). In the case of a name with a Spring "classpath*:" prefix,
    * or with no prefix, which is treated the same, the current thread's context class
    * loader's <code>getResources</code> method will be called with this value to get
    * all resources having that name. These resources will then be combined to form a
    * definition. In the case where the name uses a Spring "classpath:" prefix, or
    * a standard URL prefix, then only one resource file will be loaded as the
    * definition.
    * @param selector the name of the resource(s) which will be read and combine to
    * form the definition for the SingletonBeanFactoryLocator instance. The one file
    * or multiple fragments with this name must form a valid ApplicationContext
    * definition.
    */
    public static BeanFactoryLocator getInstance( String selector ) throws BeansException {
      
           if( ( ResourcePatternUtils.isUrl( selector ) ) == false ){
               selector = (ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + selector);               
           }
  
           synchronized( tablaInstancias ){
                if( logger.isDebugEnabled() ){
                    logger.debug( "ContextSingletonBeanFactoryLocator.getInstance(): instances.hashCode=[" +
                                   tablaInstancias.hashCode() + "], instances=[" + tablaInstancias + "]" );
                }
                
                BeanFactoryLocator objFactoryLocator = (BeanFactoryLocator)tablaInstancias.get( selector );
                logger.debug( "objFactoryLocator [Instancia]#2: " + objFactoryLocator );

                if( objFactoryLocator == null ){
                    objFactoryLocator = new ContextSingletonBeanFactoryLocator( selector );
                    tablaInstancias.put( selector, objFactoryLocator );
                }
                
                return objFactoryLocator;
           }
    }


   /**
    * Constructor which uses the default "classpath*:beanEjbSpringRefContext.xml", as the name of the
    * definition file(s). All resources returned by the definition classloader's
    * getResources() method with this name will be combined to create a definition.
    */
    protected ContextSingletonBeanFactoryLocator(){
              super( UBICACION_EJB_SPRING_CONTEXT );
    }

   /**
    * Constructor which uses the the specified name as the name of the
    * definition file(s). All resources returned by the definition classloader's
    * getResources() method with this name will be combined to create a definition.
    */
    protected ContextSingletonBeanFactoryLocator( String nombreRecurso ){ 
              super( nombreRecurso );
    }
    
   /**
    * Overrides default method to initialize definition object, which this method
    * assumes is a ConfigurableApplicationContext. This implementation simply invokes
    * {@link ConfigurableApplicationContext#refresh ConfigurableApplicationContext.refresh()}.
    */
    protected void initializeDefinition( BeanFactory objBeanFactory ) throws BeansException{
              if( objBeanFactory instanceof ConfigurableApplicationContext ){
                  ((ConfigurableApplicationContext) objBeanFactory).refresh();
              }
    }
    
   /**
    * Overrides default method to work with ApplicationContext
    */
    protected void destroyDefinition( BeanFactory objBeanFactory, String nombreRecurso ) throws BeansException{
              if( objBeanFactory instanceof ConfigurableApplicationContext ){
                  
                  //Para DEPURACION solamente: 
                  if( logger.isDebugEnabled() ){
                      logger.debug( "ContextSingletonBeanFactoryLocator group with resourceName=['" + nombreRecurso + "'] being released, as there are no more references" );  
                  }
                  
                  ( (ConfigurableApplicationContext)objBeanFactory ).close();
              }
    }
    
}

