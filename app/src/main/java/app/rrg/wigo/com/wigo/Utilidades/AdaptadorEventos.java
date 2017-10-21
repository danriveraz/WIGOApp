package app.rrg.wigo.com.wigo.Utilidades;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.R;

/**
 * Created by root on 19/10/17.
 */

public class AdaptadorEventos extends BaseAdapter{

    Context context;
    ArrayList<Evento> eventos;

    public AdaptadorEventos(Context context, ArrayList<Evento> eventos) {
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        vista = inflater.inflate(R.layout.item_listview,null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imagenEventos);
        ExpandableListView listaExpandible = (ExpandableListView) vista.findViewById(R.id.expandibleList);
        if (!eventos.get(position).getImagen().equals("")) {
                Uri uri = Uri.parse(eventos.get(position).getImagen());
                imagen.setImageURI(uri);
        }
        AdaptadorExpandableList adaptador = new AdaptadorExpandableList(eventos.get(position),context);
        listaExpandible.setAdapter(adaptador);
        return vista;
    }
}
