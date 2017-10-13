package app.rrg.wigo.com.wigo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.rrg.wigo.com.wigo.Utilidades.Utilidades;

/**
 * Created by root on 9/10/17.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        /*bd.execSQL(Utilidades.CREAR_USUARIO);
        bd.execSQL(Utilidades.CREAR_EVENTO);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionAnterior, int versionNueva) {
        bd.execSQL("DROP TABLE IF EXISTS usuarios");
        bd.execSQL("DROP TABLE IF EXISTS eventos");
        onCreate(bd);
    }
}

