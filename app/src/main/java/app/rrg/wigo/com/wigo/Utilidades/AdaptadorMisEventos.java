package app.rrg.wigo.com.wigo.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.MisEventos;
import app.rrg.wigo.com.wigo.ModificarEvento;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        ImageButton botonModificar = (ImageButton) vista.findViewById(R.id.buttonModificar);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModificarEvento.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                intent.putExtra("id",eventos.get(position).getId());
                context.startActivity(intent);
            }
        });
        ImageButton botonEliminar = (ImageButton) vista.findViewById(R.id.buttonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventoBD dbe = new EventoBD(context);
                dbe.deleteEvento(eventos.get(position).getId());
                Intent intent = new Intent(context, MisEventos.class);
                //Activity activity = (Activity) context;
                Activity activity = (Activity)context;
                activity.finish();
                context.startActivity(intent);
                //activity.finish();
                //context.startActivity(intent);
            }
        });

        return vista;
    }
}
