package pe.com.claro.eai.PlantillaProyectoMDB.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {

    public Util() {
        super();
    }

    public static String getStackTraceFromException(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }

}

