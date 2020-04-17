package pe.com.claro.eai.PlantillaProyectoMDB.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.com.claro.eai.PlantillaProyectoMDB.dao.BscsDaoImpl;
import pe.com.claro.eai.PlantillaProyectoMDB.exception.DBException;
import pe.com.claro.eai.PlantillaProyectoMDB.exception.WSException;
import pe.com.claro.eai.PlantillaProyectoMDB.proxy.DatosClienteClientImpl;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;
import pe.com.enviarjms.test.bean.MensajePlantilla; 

public class PlantillaMDBServiceImpl implements PlantillaMDBService{
    
    private static transient Logger logger = Logger.getLogger(PlantillaMDBServiceImpl.class);
    
    @Autowired
    private DatosClienteClientImpl datosCliente;
    
    @Autowired
    private BscsDaoImpl bscsDaoImpl;
    
    @Override
    public void ejecutarPrueba( String mensajeTran, MensajePlantilla mensaje ){
        
        String mensajeTransaccionMetodo= mensajeTran + "[ejecutarPrueba] ";
        DatosClientexMSISDNResponse responseDatosCliente = null;
        
        logger.info( mensajeTransaccionMetodo + "-----INICIO-----" );
        
        logger.info( mensajeTransaccionMetodo + "Consulta WS" );
        try{
            
            responseDatosCliente= datosCliente.consultarTipoCliente( mensajeTransaccionMetodo, 
                                                                     mensaje.getIpSistemaOrigen(), 
                                                                     mensaje.getIdTransaccion(), 
                                                                     mensaje.getMsisdnPostpago() );
            
        }catch(WSException e){
            logger.error( mensajeTransaccionMetodo + "Error al consultar servicio");
            logger.error( mensajeTransaccionMetodo + "Codigo:  "+ e.getCodError());
            logger.error( mensajeTransaccionMetodo + "Mensaje:  "+ e.getMsjError());
        }
        
        logger.info( mensajeTransaccionMetodo + "Consulta a base de datos" );
        
        try{

            bscsDaoImpl.consultarNroContrato( mensajeTransaccionMetodo, mensaje.getMsisdnPostpago() );
            
        }catch(DBException e){
            logger.error( mensajeTransaccionMetodo + "Error al consultar base de datos");
            logger.error( mensajeTransaccionMetodo + "Codigo:  "+ e.getCodError());
            logger.error( mensajeTransaccionMetodo + "Mensaje:  "+ e.getMsjError());
        }
        
    }
}

