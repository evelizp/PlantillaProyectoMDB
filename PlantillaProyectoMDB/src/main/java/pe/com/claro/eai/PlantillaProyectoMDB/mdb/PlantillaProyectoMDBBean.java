package pe.com.claro.eai.PlantillaProyectoMDB.mdb;

import javax.ejb.MessageDriven;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.com.claro.eai.PlantillaProyectoMDB.service.PlantillaMDBServiceImpl;
import pe.com.claro.eai.PlantillaProyectoMDB.util.JAXBUtilitarios;
import pe.com.claro.eai.PlantillaProyectoMDB.util.SpringBeanInterceptors;
import pe.com.enviarjms.test.bean.MensajePlantilla;

/**
 * Message-Driven Bean implementation class for: EjecutarPlantillaMDBBean
 */
@MessageDriven
@Interceptors( SpringBeanInterceptors.class )
public class PlantillaProyectoMDBBean implements MessageListener {
	
private static Logger logger = Logger.getLogger(PlantillaProyectoMDBBean.class);
    
    @Autowired  
    private PlantillaMDBServiceImpl service;


    /**
     * Default constructor. 
     */
    public PlantillaProyectoMDBBean() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	String mensaje = "[onMessage ";
        final long vTiempoProceso = System.currentTimeMillis();

        int vDeliveryCount = 0;
        int vRedeliveryLimit = 0;

        try{
            
            logger.info( "--------[INICIO] - METODO: [onMessage]--------" );
            logger.info( "Leer mensaje de la cola" );
            if( message instanceof ObjectMessage){
                
                logger.info( "Tipo de mensaje: [ObjectMessage]" );

                ObjectMessage objMessage = (ObjectMessage)message;
                MensajePlantilla mensajeInput = (MensajePlantilla)objMessage.getObject();
                
                mensaje += "idTx=" + mensajeInput.getIdTransaccion() + "] ";
                
                logger.info( mensaje + "OBJETO [INPUT]: " + JAXBUtilitarios.anyObjectToXmlText( mensajeInput ));
                
                vDeliveryCount = objMessage.getIntProperty( "JMSXDeliveryCount" );
                vRedeliveryLimit = objMessage.getIntProperty( "JMS_BEA_RedeliveryLimit" );
                
                logger.info( mensaje + "JMSXDeliveryCount - Intento Actual=[" + vDeliveryCount + "]," + "JMS_BEA_RedeliveryLimit=[" + vRedeliveryLimit + "], " + "Maximo de intentos=[" + ( vRedeliveryLimit + 1 ) + "]" );
                
                service.ejecutarPrueba(mensaje, mensajeInput);
                
            } else {
                logger.info( "Tipo de mensaje: [TextMessage]" );

                TextMessage textMessage = (TextMessage)message;
                
                logger.info( mensaje + "OBJETO [INPUT]: " + textMessage.getText());
                
                vDeliveryCount = textMessage.getIntProperty( "JMSXDeliveryCount" );
                vRedeliveryLimit = textMessage.getIntProperty( "JMS_BEA_RedeliveryLimit" );
                
                logger.info( mensaje + "JMSXDeliveryCount - Intento Actual=[" + vDeliveryCount + "]," + "JMS_BEA_RedeliveryLimit=[" + vRedeliveryLimit + "], " + "Maximo de intentos=[" + ( vRedeliveryLimit + 1 ) + "]" );
                
                
            }
            
        }catch(Exception e){
            logger.error( mensaje + "Error: " + e );
            
        }finally{
            logger.info( mensaje + "Tiempo total de proceso(ms): " 
                                 + ( System.currentTimeMillis() - vTiempoProceso ) + " milisegundos." );
            logger.info( mensaje + "--------[FIN] - METODO: [onMessage]--------" );
        }

        
    }

}

