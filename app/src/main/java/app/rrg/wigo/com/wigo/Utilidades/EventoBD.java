package app.rrg.wigo.com.wigo.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.DBHelper;
import app.rrg.wigo.com.wigo.Entities.Evento;

/**
 * Created by danri on 15/10/2017.
 */

public class EventoBD {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public EventoBD(Context context) {
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

    private ContentValues eventoMapperContentValues(Evento evento) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.NOMBRE_EVENTO, evento.getNombre());
        cv.put(Utilidades.DESCRIPCION_EVENTO, evento.getDescripcion());
        cv.put(Utilidades.HORA_EVENTO, evento.getHora());
        cv.put(Utilidades.FECHA_EVENTO, evento.getFecha());
        cv.put(Utilidades.PRECIO_EVENTO, evento.getPrecio());
        cv.put(Utilidades.DIRECCION_EVENTO, evento.getDireccion());
        cv.put(Utilidades.CREADOR_EVENTO, evento.getCreador());
        cv.put(Utilidades.IMAGEN_EVENTO, evento.getCreador());
        return cv;
    }

    public long insertEvento(Evento evento) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_EVENTO, null, eventoMapperContentValues(evento));
        this.closeDB();

        return rowID;
    }

    public void updateUsuario(Evento evento) {
        this.openWriteableDB();
        String where = Utilidades.ID_EVENTO + "= ?";
        db.update(Utilidades.TABLA_EVENTO, eventoMapperContentValues(evento), where, new String[]{String.valueOf(evento.getId())});
        db.close();
    }

    public void deleteEvento(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_EVENTO + "= ?";
        db.delete(Utilidades.TABLA_EVENTO, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadEventos() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_EVENTO, Utilidades.NOMBRE_EVENTO, Utilidades.DESCRIPCION_EVENTO, Utilidades.HORA_EVENTO,
                Utilidades.FECHA_EVENTO, Utilidades.PRECIO_EVENTO, Utilidades.DIRECCION_EVENTO, Utilidades.IMAGEN_EVENTO,Utilidades.CREADOR_EVENTO};
        Cursor c = db.query(Utilidades.TABLA_EVENTO, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Evento evento = new Evento();
                evento.setId(c.getInt(0));
                evento.setNombre(c.getString(1));
                evento.setDescripcion(c.getString(2));
                evento.setHora(c.getString(3));
                evento.setFecha(c.getString(4));
                evento.setPrecio(c.getString(5));
                evento.setDireccion(c.getString(6));
                evento.setImagen(c.getString(7));
                evento.setCreador(c.getInt(8));
                list.add(evento);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Evento buscarEventos(String nombre) {
        Evento evento = new Evento();
        this.openReadableDB();
        String where = Utilidades.NOMBRE_EVENTO + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = db.query(Utilidades.TABLA_EVENTO, null, where, whereArgs, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            evento.setId(c.getInt(0));
            evento.setNombre(c.getString(1));
            evento.setDescripcion(c.getString(2));
            evento.setHora(c.getString(3));
            evento.setFecha(c.getString(4));
            evento.setPrecio(c.getString(5));
            evento.setDireccion(c.getString(6));
            evento.setImagen(c.getString(7));
            evento.setCreador(c.getInt(8));
            c.close();
        }
        this.closeDB();
        return evento;
    }
}
