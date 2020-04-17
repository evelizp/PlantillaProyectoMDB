package pe.com.claro.eai.PlantillaProyectoMDB.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

public class JAXBUtilitarios{
    private static transient Logger logger = Logger.getLogger(JAXBUtilitarios.class);

    private static HashMap<Class, JAXBContext> mapContexts =
        new HashMap<Class, JAXBContext>();


    private static JAXBContext obtainJaxBContextFromClass(Class clas) {

        JAXBContext context;
        context = mapContexts.get(clas);

        if (context == null) {

            try {
                context =
                        JAXBContext.newInstance(clas); 
                mapContexts.put(clas, context);
            } catch (Exception e) {
                logger.error("Error creando JAXBContext:", e);
            }
        }
        return context;
    }

    public static String jaxBToXmlText(Object objJaxB) {
        String commandoRequestEnXml = null;

        JAXBContext context;

        try {
            context =
                    obtainJaxBContextFromClass(objJaxB.getClass()); 
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.valueOf(true));
            StringWriter xmlWriter = new StringWriter();
            marshaller.marshal(objJaxB, xmlWriter);
                        
            commandoRequestEnXml = xmlWriter.toString().trim();

        } catch (Exception e) {
            logger.error("Error parseando object to xml:", e);
        }

        return commandoRequestEnXml;
    }
    
    public static String anyObjectToXmlText(Object anyObject) {
        String commandoRequestEnXml = null;

        JAXBContext context;

        try {
            context =
                    obtainJaxBContextFromClass(anyObject.getClass()); 
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.valueOf(true));
            StringWriter xmlWriter = new StringWriter();
            marshaller.marshal(new JAXBElement(new QName("",
                                                         anyObject.getClass().getSimpleName()),
                                               anyObject.getClass(),
                                               anyObject), xmlWriter);

          commandoRequestEnXml = xmlWriter.toString();
        } catch (Exception e) {
            logger.error("Error parseando object to xml:", e);
        }

        return commandoRequestEnXml;
    }

    public static Object xmlTextToJaxB(String xmlText, Class clas) {

        JAXBContext context;
        Object object = null;

        try {
            context =
                    obtainJaxBContextFromClass(clas); 
            Unmarshaller um = context.createUnmarshaller();

            InputStream is =
                new ByteArrayInputStream(xmlText.getBytes("UTF-8"));

            object = um.unmarshal(is);

        } catch (Exception e) {
            logger.error("Error unmarshalling xmlObject:" + xmlText +
                         ". Detalle Error:", e);
        }
        return object;
    }    
}


