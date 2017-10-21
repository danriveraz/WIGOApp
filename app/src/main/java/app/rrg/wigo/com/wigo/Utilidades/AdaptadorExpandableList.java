package app.rrg.wigo.com.wigo.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.R;

/**
 * Created by root on 20/10/17.
 */

public class AdaptadorExpandableList extends BaseExpandableListAdapter {

    private Evento evento;
    private Context context;

    public AdaptadorExpandableList(Evento evento, Context context) {
        this.evento = evento;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        ArrayList<String> grupo = new ArrayList<String>();
        grupo.add(evento.getNombre().toString());
        grupo.add(evento.getFecha().toString());
        return grupo;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<String> detalles = new ArrayList<String>();
        detalles.add(evento.getHora().toString());
        detalles.add(evento.getPrecio().toString());
        detalles.add(evento.getDireccion().toString());
        detalles.add(evento.getDescripcion().toString());
        return detalles;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return evento.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.group_eventos,null);
        TextView textNombre = (TextView) convertView.findViewById(R.id.textNombre);
        TextView textFecha = (TextView) convertView.findViewById(R.id.textFecha);
        textNombre.setText(evento.getNombre().toString());
        textFecha.setText(evento.getFecha().toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.detalles,null);
        TextView textHora = (TextView) convertView.findViewById(R.id.textHora);
        TextView textPrecio = (TextView) convertView.findViewById(R.id.textPrecio);
        TextView textDireccion = (TextView) convertView.findViewById(R.id.textDireccion);
        TextView textDescripcion = (TextView) convertView.findViewById(R.id.textDescripcion);
        textHora.setText(evento.getHora().toString());
        textPrecio.setText(evento.getPrecio().toString());
        textDireccion.setText(evento.getDireccion().toString());
        textDescripcion.setText(evento.getDescripcion().toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
