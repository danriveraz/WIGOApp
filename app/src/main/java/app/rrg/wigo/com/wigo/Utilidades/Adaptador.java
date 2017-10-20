package app.rrg.wigo.com.wigo.Utilidades;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.R;

/**
 * Created by root on 19/10/17.
 */

public class Adaptador extends BaseAdapter{

    Context context;
    ArrayList<Evento> eventos;

    public Adaptador(Context context, ArrayList<Evento> eventos) {
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

        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView2);
        TextView texto = (TextView) vista.findViewById(R.id.textView3);
        Log.i("mensaje",eventos.get(position).getNombre());
        texto.setText(eventos.get(position).getNombre());
        return vista;
    }
}
