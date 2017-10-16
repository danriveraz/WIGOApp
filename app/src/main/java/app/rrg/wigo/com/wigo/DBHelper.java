package app.rrg.wigo.com.wigo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.rrg.wigo.com.wigo.Utilidades.Utilidades;

/**
 * Created by root on 9/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Utilidades.DB_NAME, null, Utilidades.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_USUARIO);
        db.execSQL(Utilidades.CREAR_EVENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS eventos");
        onCreate(db);
    }
}

