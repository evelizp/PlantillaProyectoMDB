package pe.com.claro.eai.PlantillaProyectoMDB.service;

import pe.com.enviarjms.test.bean.MensajePlantilla;

public interface PlantillaMDBService {
    
    void ejecutarPrueba(String mensajeTran, MensajePlantilla mensaje);
    
}

