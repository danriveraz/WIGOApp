package app.rrg.wigo.com.wigo.Utilidades;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.R;

/**
 * Created by DiegoFGuty on 21/10/2017.
 */

public class AdaptadorMisEventos extends BaseAdapter{

    Context context;
    ArrayList<Evento> eventos;

    public AdaptadorMisEventos(Context context, ArrayList<Evento> eventos) {
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
        vista = inflater.inflate(R.layout.item_listview_mis_eventos,null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imagenEvento);
        TextView texto = (TextView) vista.findViewById(R.id.textViewNombreEvento);
        if (!eventos.get(position).getImagen().equals("")) {
            Uri uri = Uri.parse(eventos.get(position).getImagen());
            Glide.with(context).load(uri).into(imagen);
            //imagen.setImageURI(uri);
        }
        texto.setText(eventos.get(position).getNombre());
        return vista;
    }
}
