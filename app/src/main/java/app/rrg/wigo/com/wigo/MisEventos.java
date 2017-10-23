package app.rrg.wigo.com.wigo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.Entities.Usuario;
import app.rrg.wigo.com.wigo.Utilidades.AdaptadorMisEventos;
import app.rrg.wigo.com.wigo.Utilidades.EventoBD;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;
import app.rrg.wigo.com.wigo.Utilidades.UsuarioBD;

/**
 * Created by DiegoFGuty on 21/10/2017.
 */

public class MisEventos extends AppCompatActivity {
    private EventoBD dbe;
    private UsuarioBD dbu;
    private Sesion sesion;
    ListView lista;
    ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);
        final Context context = this;

        //Se pone la flecha hacia atrás
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        lista = (ListView) findViewById(R.id.IdListaMisEventos);
        dbu = new UsuarioBD(this);
        dbe = new EventoBD(this);
        sesion = new Sesion(context);
        Usuario usuario = dbu.buscarUsuarios(sesion.loggedin());

        eventos = (ArrayList<Evento>)dbe.loadEventos();
        ArrayList<Evento> filtroEventos = new ArrayList<Evento>();
        for (int i=0;i<eventos.size();i++){
            if(usuario.getId()==eventos.get(i).getCreador()){
                filtroEventos.add(eventos.get(i));
            }
        }
        AdaptadorMisEventos adaptador = new AdaptadorMisEventos(this,filtroEventos);
        lista.setAdapter(adaptador);

        /*ImageButton botonModificar = (ImageButton) findViewById(R.id.buttonModificar);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarEventoButtonListener();
            }
        });
        ImageButton botonEliminar = (ImageButton) findViewById(R.id.buttonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarEventoButtonListener();
            }
        });*/
    }

    private void modificarEventoButtonListener() {
        Toast modificar = Toast.makeText(getApplicationContext(), "Modificar",Toast.LENGTH_SHORT);
        modificar.show();
        finish();
    }

    private void eliminarEventoButtonListener() {
        Toast eliminar = Toast.makeText(getApplicationContext(), "Eliminar",Toast.LENGTH_SHORT);
        eliminar.show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent regresar = new Intent(getApplicationContext(), VistaAdminEvento.class);
        startActivityForResult(regresar, 0);

        return true;
    }
}
