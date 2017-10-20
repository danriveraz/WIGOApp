package app.rrg.wigo.com.wigo.Utilidades;

/**
 * Created by root on 9/10/17.
 */

public class Utilidades {

    //General
    public static final String DB_NAME = "wigoBD.db";
    public static final int DB_VERSION = 1;

    // Constantes tabla usuarios
    public static final String TABLA_USUARIO = "usuarios";
    public static final String ID_USUARIO = "id";
    public static final String NOMBRE_USUARIO = "nombre";
    public static final String CORREO_USUARIO = "correo";
    public static final String DIRECCION_USUARIO = "direccion";
    public static final String TELEFONO_USUARIO = "telefono";
    public static final String CELULAR_USUARIO = "celular";
    public static final String EMPRESA_USUARIO = "empresa";
    public static final String CONTRASENA_USUARIO = "contrasena";
    public static final String FOTO_USUARIO = "foto";

    public static final String CREAR_USUARIO =
            "CREATE TABLE "+ TABLA_USUARIO + "(" +
            ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOMBRE_USUARIO      + " TEXT," +
            CORREO_USUARIO      + " TEXT," +
            DIRECCION_USUARIO   + " TEXT," +
            TELEFONO_USUARIO    + " TEXT," +
            CELULAR_USUARIO     + " TEXT," +
            EMPRESA_USUARIO     + " TEXT," +
            FOTO_USUARIO        + " TEXT," +
            CONTRASENA_USUARIO  + " TEXT);" ;

    // Constantes tabla eventos
    public static final String TABLA_EVENTO = "eventos";
    public static final String ID_EVENTO = "id";
    public static final String NOMBRE_EVENTO = "nombre";
    public static final String DESCRIPCION_EVENTO = "descripcion";
    public static final String HORA_EVENTO = "hora";
    public static final String FECHA_EVENTO = "fecha";
    public static final String PRECIO_EVENTO = "precio";
    public static final String DIRECCION_EVENTO = "direccion";
    public static final String FOTO_EVENTO = "foto";
    public static final String CREADOR_EVENTO = "creador";

    public static final String CREAR_EVENTO = "CREATE TABLE "+TABLA_EVENTO+"("+ID_EVENTO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NOMBRE_EVENTO+" TEXT, "+DESCRIPCION_EVENTO+" TEXT,"+HORA_EVENTO+" TEXT,"+FECHA_EVENTO+" TEXT,"+PRECIO_EVENTO+" TEXT,"+DIRECCION_EVENTO+" TEXT,"+CREADOR_EVENTO+" INTEGER,FOREIGN KEY("+CREADOR_EVENTO+") REFERENCES usuarios("+ID_USUARIO+"));";
}

