package pe.com.claro.eai.PlantillaProyectoMDB.dao;

import pe.com.claro.eai.PlantillaProyectoMDB.exception.DBException;

public interface BscsDao {
    
    void consultarNroContrato(String mensajeTransaccion, String msisdn) throws DBException;
    
}

