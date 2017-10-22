package app.rrg.wigo.com.wigo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.Utilidades.AdaptadorEventos;
import app.rrg.wigo.com.wigo.Utilidades.EventoBD;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;
import app.rrg.wigo.com.wigo.Utilidades.Utilidades;

public class VistaAdminEvento extends AppCompatActivity {

    DBHelper conexion;
    private Sesion sesion;
    private EventoBD dbe;
    ListView lista;
    ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_admin_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lista = (ListView) findViewById(R.id.IdLista);
        setSupportActionBar(toolbar);
        sesion = new Sesion(this);
        dbe = new EventoBD(this);
        //Toast.makeText(VistaAdminEvento.this,"Resultado: ", Toast.LENGTH_LONG).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(VistaAdminEvento.this,RegistroEvento.class);
                startActivity(registro);
            }
        });

        conexion = new DBHelper(this);

        eventos = (ArrayList<Evento>)dbe.loadEventos();
        AdaptadorEventos adaptador = new AdaptadorEventos(getApplicationContext(),eventos);

        lista.setAdapter(adaptador);
        lista.setClickable(true);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout details = (LinearLayout)view.findViewById(R.id.layoutDetalles);
                if(details.getVisibility() == View.VISIBLE){
                    details.setVisibility(View.GONE);
                }else{
                    details.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_eventos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.perfil) {
            Intent iniciar = new Intent(VistaAdminEvento.this, ModificarUsuario.class);
            startActivity(iniciar);
            return true;
        } else if (id == R.id.mis_eventos) {
            Toast.makeText(this, "Proximamente", Toast.LENGTH_LONG).show();
            /*Intent inicio = new Intent(VistaAdminEvento.this,InicioSesion.class);
            startActivity(inicio);*/
            return true;
        } else if (id == R.id.cerrar_sesion) {
            sesion.setLoggedin("");
            Intent iniciar = new Intent(VistaAdminEvento.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(iniciar);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
