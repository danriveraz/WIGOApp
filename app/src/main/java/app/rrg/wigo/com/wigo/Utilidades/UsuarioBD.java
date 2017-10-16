package app.rrg.wigo.com.wigo.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Usuario;

/**
 * Created by danri on 11/10/2017.
 */

public class UsuarioBD {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public UsuarioBD(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if(db!=null){
            db.close();
        }
    }

    //CRUD

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, Utilidades.DB_NAME, null, Utilidades.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Utilidades.CREAR_USUARIO);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private ContentValues usuarioMapperContentValues(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.NOMBRE_USUARIO, usuario.getNombre());
        cv.put(Utilidades.CORREO_USUARIO, usuario.getCorreo());
        cv.put(Utilidades.DIRECCION_USUARIO, usuario.getDireccion());
        cv.put(Utilidades.TELEFONO_USUARIO, usuario.getTelefono());
        cv.put(Utilidades.CELULAR_USUARIO, usuario.getCelular());
        cv.put(Utilidades.EMPRESA_USUARIO, usuario.getNombreEmpresa());
        cv.put(Utilidades.CONTRASENA_USUARIO, usuario.getContrasena());
        return cv;
    }

    public long insertUsuario(Usuario usuario) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_USUARIO, null, usuarioMapperContentValues(usuario));
        this.closeDB();

        return rowID;
    }

    public void updateUsuario(Usuario usuario) {
        this.openWriteableDB();
        String where = Utilidades.ID_USUARIO + "= ?";
        db.update(Utilidades.TABLA_USUARIO, usuarioMapperContentValues(usuario), where, new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    public void deleteUsuario(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_USUARIO + "= ?";
        db.delete(Utilidades.TABLA_USUARIO, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadUsuarios() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_USUARIO, Utilidades.NOMBRE_USUARIO, Utilidades.CORREO_USUARIO, Utilidades.DIRECCION_USUARIO,
                Utilidades.TELEFONO_USUARIO, Utilidades.CELULAR_USUARIO, Utilidades.EMPRESA_USUARIO, Utilidades.CONTRASENA_USUARIO};
        Cursor c = db.query(Utilidades.TABLA_USUARIO, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(c.getInt(0));
                usuario.setNombre(c.getString(1));
                usuario.setCorreo(c.getString(2));
                usuario.setDireccion(c.getString(3));
                usuario.setTelefono(c.getString(4));
                usuario.setCelular(c.getString(5));
                usuario.setNombreEmpresa(c.getString(6));
                usuario.setContrasena(c.getString(7));
                list.add(usuario);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Usuario buscarUsuarios(String correo) {
        Usuario usuario = new Usuario();
        this.openReadableDB();
        String where = Utilidades.CORREO_USUARIO + "= ?";
        String[] whereArgs = {correo};
        Cursor c = db.query(Utilidades.TABLA_USUARIO, null, where, whereArgs, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            usuario.setId(c.getInt(0));
            usuario.setNombre(c.getString(1));
            usuario.setCorreo(c.getString(2));
            usuario.setDireccion(c.getString(3));
            usuario.setTelefono(c.getString(4));
            usuario.setCelular(c.getString(5));
            usuario.setNombreEmpresa(c.getString(6));
            usuario.setContrasena(c.getString(7));
            c.close();
        }
        this.closeDB();
        return usuario;
    }
}
