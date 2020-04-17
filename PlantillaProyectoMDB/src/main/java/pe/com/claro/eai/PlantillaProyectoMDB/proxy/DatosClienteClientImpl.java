package pe.com.claro.eai.PlantillaProyectoMDB.proxy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.com.claro.eai.PlantillaProyectoMDB.exception.WSException;
import pe.com.claro.eai.PlantillaProyectoMDB.util.JAXBUtilitarios;
import pe.com.claro.eai.PlantillaProyectoMDB.util.PropertiesExterno;
import pe.com.claro.esb.services.datoscliente.ws.EbsDatosCliente;
import pe.com.claro.esb.services.schemas.datoscliente.AutenticacionType;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNRequest;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;

public class DatosClienteClientImpl implements DatosClienteClient{
    
    private static transient Logger logger= Logger.getLogger( DatosClienteClientImpl.class );
    
    @Autowired
    private EbsDatosCliente datosClienteWS;
    
    @Autowired
    private PropertiesExterno propertiesExterno;
    
    @Override
    public DatosClientexMSISDNResponse consultarTipoCliente( String mensajeTransaccion, String p_ipAplicacion, String p_idTransaccion, String p_msisdn )
            throws WSException{
        
        String mensajeMetodo = mensajeTransaccion +"[consultarTipoCliente]";
        
        DatosClientexMSISDNResponse response = null;
        
        DatosClientexMSISDNRequest request = new DatosClientexMSISDNRequest();
        
        try {
            AutenticacionType autenticacion = new AutenticacionType();
            autenticacion.setIpCliente( p_ipAplicacion );
            autenticacion.setUsuario( propertiesExterno.WS_DATOSCLIENTE_AUTENTICACION_USUARIO );
            autenticacion.setClave( propertiesExterno.WS_DATOSCLIENTE_AUTENTICACION_CLAVE );
            autenticacion.setCodigoServicioEAI( propertiesExterno.WS_DATOSCLIENTE_AUTENTICACION_CODIGOSERVICIOEAI );
            autenticacion.setCapacidadServicioEAI( propertiesExterno.WS_DATOSCLIENTE_AUTENTICACION_CAPACIDADSERVICIOEAI );         
            
            request.setAutenticacion(autenticacion);
            request.setAplicacionConsumidora( propertiesExterno.WS_DATOSCLIENTE_APLICACION_CONSUMIDORA );
            request.setIdTransaccionConsumidora(p_idTransaccion);
            request.setMsisdn(p_msisdn);
            request.setHlrflag( Integer.parseInt( propertiesExterno.WS_DATOSCLIENTE_HLRFLAG ) );
            request.setCodigoExternalOrigen( propertiesExterno.WS_DATOSCLIENTE_CODIGO_EXTERNAL_ORIGEN );
            request.setExternalOrigen( propertiesExterno.WS_DATOSCLIENTE_EXTERNAL_ORIGEN );
            
            logger.info( mensajeMetodo + "Se invocara al Servicio: " + propertiesExterno.WS_DATOSCLIENTE_ENDPOINTADDRESS );
            logger.info( mensajeMetodo + "Datos de entrada al Servicio: " + JAXBUtilitarios.anyObjectToXmlText( request ) );
            
            response = datosClienteWS.consultaClienteXMSISDN( request );
            
            logger.info( mensajeMetodo + "Datos de salida del Servicio: " + JAXBUtilitarios.anyObjectToXmlText( response ) );
            
        } catch (Exception e) {
            
            logger.error( mensajeMetodo + "ERROR: [Exception] - [" + e.getMessage() + "] ", e );
            
            String excepcion = e + "";
            
            String msjError = null;
            String codError = null;
            
            if(excepcion.contains( propertiesExterno.WS_ERROR_TIME_OUT ) ) {
                
                codError = propertiesExterno.WS_CODIGO_IDT1;
                msjError = String.format( propertiesExterno.WS_MENSAJE_IDT1, propertiesExterno.WS_DATOSCLIENTE_ENDPOINTADDRESS ) + " " + e.getMessage();
  
            } else {
                
                codError = propertiesExterno.WS_CODIGO_IDT2;
                msjError = String.format( propertiesExterno.WS_MENSAJE_IDT2, propertiesExterno.WS_DATOSCLIENTE_ENDPOINTADDRESS ) + " " + e.getMessage();
                
            }
            
            throw new WSException( codError, msjError, e );            
        }
        
        return response;

    }
}

