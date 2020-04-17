package pe.com.claro.eai.PlantillaProyectoMDB.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesExterno {

    @Value("${ws.datosCliente.endpointAddress}")
    public String WS_DATOSCLIENTE_ENDPOINTADDRESS;

    @Value("${ws.datosCliente.max.request.timeout}")
    public String WS_DATOSCLIENTE_MAX_REQUEST_TIMEOUT;

    @Value("${ws.datosCliente.max.connection.timeout}")
    public String WS_DATOSCLIENTE_MAX_CONNECTION_TIMEOUT;

    @Value("${ws.datosCliente.autenticacion.usuario}")
    public String WS_DATOSCLIENTE_AUTENTICACION_USUARIO;

    @Value("${ws.datosCliente.autenticacion.clave}")
    public String WS_DATOSCLIENTE_AUTENTICACION_CLAVE;

    @Value("${ws.datosCliente.autenticacion.codigoServicioEAI}")
    public String WS_DATOSCLIENTE_AUTENTICACION_CODIGOSERVICIOEAI;

    @Value("${ws.datosCliente.autenticacion.capacidadServicioEAI}")
    public String WS_DATOSCLIENTE_AUTENTICACION_CAPACIDADSERVICIOEAI;

    @Value("${ws.datosCliente.aplicacion.consumidora}")
    public String WS_DATOSCLIENTE_APLICACION_CONSUMIDORA;

    @Value("${ws.datosCliente.hlrflag}")
    public String WS_DATOSCLIENTE_HLRFLAG;

    @Value("${ws.datosCliente.codigo.external.origen}")
    public String WS_DATOSCLIENTE_CODIGO_EXTERNAL_ORIGEN;

    @Value("${ws.datosCliente.external.origen}")
    public String WS_DATOSCLIENTE_EXTERNAL_ORIGEN;

    @Value("${ws.error.time.out}")
    public String WS_ERROR_TIME_OUT;

    @Value("${ws.codigo.idt1}")
    public String WS_CODIGO_IDT1;

    @Value("${ws.mensaje.idt1}")
    public String WS_MENSAJE_IDT1;

    @Value("${ws.codigo.idt2}")
    public String WS_CODIGO_IDT2;

    @Value("${ws.mensaje.idt2}")
    public String WS_MENSAJE_IDT2;

    @Value("${db.bscs}")
    public String DB_BSCS;

    @Value("${db.bscs.jndi}")
    public String DB_BSCS_JNDI;

    @Value("${db.bscs.owner}")
    public String DB_BSCS_OWNER;

    @Value("${db.bscs.timeout.connection.max.time}")
    public String DB_BSCS_TIMEOUT_CONNECTION_MAX_TIME;

    @Value("${db.bscs.timeout.execution.max.time}")
    public String DB_BSCS_TIMEOUT_EXECUTION_MAX_TIME;

    @Value("${sp.consulta.nro.contrato}")
    public String SP_CONSULTA_NRO_CONTRATO;

    @Value("${sp.consulta.nro.contrato.idt1.codigo}")
    public String SP_CONSULTA_NRO_CONTRATO_IDT1_CODIGO;

    @Value("${sp.consulta.nro.contrato.idt1.mensaje}")
    public String SP_CONSULTA_NRO_CONTRATO_IDT1_MENSAJE;

    @Value("${sp.consulta.nro.contrato.idt2.codigo}")
    public String SP_CONSULTA_NRO_CONTRATO_IDT2_CODIGO;

    @Value("${sp.consulta.nro.contrato.idt2.mensaje}")
    public String SP_CONSULTA_NRO_CONTRATO_IDT2_MENSAJE;

}

