package app.rrg.wigo.com.wigo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.Utilidades.Adaptador2;
import app.rrg.wigo.com.wigo.Utilidades.EventoBD;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;

/**
 * Created by DiegoFGuty on 21/10/2017.
 */

public class MisEventos extends AppCompatActivity {
    private EventoBD dbe;
    private Sesion sesion;
    ListView lista;
    ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Se pone la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        lista = (ListView) findViewById(R.id.IdListaMisEventos);
        dbe = new EventoBD(this);

        sesion = new Sesion(this);

        eventos = (ArrayList<Evento>)dbe.loadEventos();
        Adaptador2 adaptador = new Adaptador2(getApplicationContext(),eventos);

        lista.setAdapter(adaptador);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent regresar = new Intent(getApplicationContext(), VistaAdminEvento.class);
        startActivityForResult(regresar, 0);

        return true;
    }
}