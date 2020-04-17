package pe.com.claro.eai.PlantillaProyectoMDB.exception;

public class DBException extends BaseException {

    private static final long serialVersionUID = 3812762141784699905L;

    public DBException() {
        super();
    }

    public DBException(Exception objException) {
        super(objException);
    }

    public DBException(String msjError) {
        super(msjError);
    }

    public DBException(String codError, String msjError, Exception objException) {
        super(codError, msjError, objException);
    }

    public DBException(String codError, String msjError, String nombreSP, String nombreBD, Exception objException) {
        super(codError, msjError, nombreSP, nombreBD, objException);
    }

}

