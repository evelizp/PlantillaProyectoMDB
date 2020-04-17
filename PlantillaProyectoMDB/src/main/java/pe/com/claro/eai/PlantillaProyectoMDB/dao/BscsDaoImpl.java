package pe.com.claro.eai.PlantillaProyectoMDB.dao;

import java.sql.SQLTimeoutException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.claro.eai.PlantillaProyectoMDB.bean.ResponseBean;
import pe.com.claro.eai.PlantillaProyectoMDB.exception.DBException;
import pe.com.claro.eai.PlantillaProyectoMDB.util.JAXBUtilitarios;
import pe.com.claro.eai.PlantillaProyectoMDB.util.PropertiesExterno;
import pe.com.claro.eai.PlantillaProyectoMDB.util.Util;

@Repository
public class BscsDaoImpl implements BscsDao{
    
    private static transient Logger logger= Logger.getLogger( BscsDaoImpl.class );
    
    @Autowired
    private PropertiesExterno propertiesExterno;
    
    @Autowired
    @Qualifier("bscsdbDS")
    private DataSource bscsdbDS;
    
    @Override
    public void consultarNroContrato( String mensajeTransaccion, String msisdn ) throws DBException {
        
        String mensajeMetodo = mensajeTransaccion + "[ consultarNroContrato ]";
        
        ResponseBean responseBean = new ResponseBean();
        
        try {
            
            logger.info(mensajeMetodo + "Consultando BD " + propertiesExterno.DB_BSCS  + ", con JNDI = [" + propertiesExterno.DB_BSCS_JNDI + "]" );
            
            bscsdbDS.setLoginTimeout( Integer.parseInt(propertiesExterno.DB_BSCS_TIMEOUT_CONNECTION_MAX_TIME) );
            
            SimpleJdbcCall procConsulta1 = new SimpleJdbcCall(bscsdbDS)
              .withoutProcedureColumnMetaDataAccess()
              .withSchemaName( propertiesExterno.DB_BSCS_OWNER )
              .withProcedureName( propertiesExterno.SP_CONSULTA_NRO_CONTRATO )
              .declareParameters(new SqlParameter( "p_dn_num", OracleTypes.VARCHAR ),
                                 new SqlOutParameter( "p_result", OracleTypes.VARCHAR ),
                                 new SqlOutParameter( "p_result_tipo", OracleTypes.VARCHAR ) );
              
              logger.info( mensajeMetodo + "Se invocara el SP: " + propertiesExterno.DB_BSCS_OWNER + "." + propertiesExterno.SP_CONSULTA_NRO_CONTRATO );
              logger.info( mensajeMetodo + "PARAMETROS [INPUT]: " );
              logger.info( mensajeMetodo + "[p_dn_num] = " + msisdn );
              
              SqlParameterSource objParametrosIN = new MapSqlParameterSource().addValue( "p_dn_num",  msisdn );
              
              procConsulta1.getJdbcTemplate().setQueryTimeout( Integer.parseInt( propertiesExterno.DB_BSCS_TIMEOUT_EXECUTION_MAX_TIME ) );
              
              Map<String, Object> resultMap = procConsulta1.execute( objParametrosIN );
                
              logger.info( mensajeMetodo + "Se invoco con exito el SP: " + propertiesExterno.DB_BSCS_OWNER + "." + propertiesExterno.SP_CONSULTA_NRO_CONTRATO );
              
              responseBean.setCo_id( resultMap.get("p_result").toString() );
              responseBean.setSn_code( resultMap.get("p_result_tipo").toString() );
              
              logger.info( mensajeMetodo +  "PARAMETROS [OUPUT]: "  );
              logger.info( mensajeMetodo + JAXBUtilitarios.anyObjectToXmlText( responseBean ) );
            
        }catch( NestedRuntimeException e ){
            String texto_error = Util.getStackTraceFromException( e );
            logger.error( mensajeTransaccion + "Se ha producido un error [NestedRuntimeException] definido como:\n" + texto_error, e );

            String mensajeError = "";
            if( e.contains( SQLTimeoutException.class ) ){
                mensajeError = propertiesExterno.SP_CONSULTA_NRO_CONTRATO_IDT2_MENSAJE.replace( "$nombre_BD", propertiesExterno.DB_BSCS_JNDI );
                mensajeError += e.getMessage();
                throw new DBException( propertiesExterno.SP_CONSULTA_NRO_CONTRATO_IDT2_CODIGO, mensajeError, e );
            }
            else{
                mensajeError = propertiesExterno.SP_CONSULTA_NRO_CONTRATO_IDT1_MENSAJE.replace( "$nombre_BD", propertiesExterno.DB_BSCS_JNDI );
                mensajeError += "Error ejecutando [" + propertiesExterno.SP_CONSULTA_NRO_CONTRATO + "] " + e.getMessage();
                throw new DBException( propertiesExterno.SP_CONSULTA_NRO_CONTRATO_IDT1_CODIGO, mensajeError, e );
            }
        }
        catch( Exception e ){
            String texto_error = Util.getStackTraceFromException( e );
            logger.error( mensajeTransaccion + "Se ha producido un error [Exception] definido como:\n" + texto_error, e );

            String mensajeError = "";
            mensajeError = propertiesExterno.SP_CONSULTA_NRO_CONTRATO_IDT1_MENSAJE.replace( "$nombre_BD", propertiesExterno.DB_BSCS_JNDI );
            mensajeError += "Error ejecutando [" + propertiesExterno.SP_CONSULTA_NRO_CONTRATO + "] " + e.getMessage();
            throw new DBException( propertiesExterno.SP_CONSULTA_NRO_CONTRATO_IDT1_CODIGO, mensajeError, e );
        }
        
        
    }
}

