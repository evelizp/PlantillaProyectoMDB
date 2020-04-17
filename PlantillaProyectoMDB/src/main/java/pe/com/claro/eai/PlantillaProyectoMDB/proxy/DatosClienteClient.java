package pe.com.claro.eai.PlantillaProyectoMDB.proxy;

import pe.com.claro.eai.PlantillaProyectoMDB.exception.WSException;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;

public interface DatosClienteClient {

    DatosClientexMSISDNResponse consultarTipoCliente(String mensajeTransaccion, String p_ipAplicacion,
            String p_idTransaccion, String p_msisdn) throws WSException;

}
