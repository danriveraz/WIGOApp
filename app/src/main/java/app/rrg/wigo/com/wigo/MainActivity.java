package app.rrg.wigo.com.wigo;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.Utilidades.AdaptadorEventos;
import app.rrg.wigo.com.wigo.Utilidades.EventoBD;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;
import app.rrg.wigo.com.wigo.Utilidades.UsuarioBD;

public class MainActivity extends AppCompatActivity {

    private UsuarioBD dbu;
    private EventoBD dbe;
    private Sesion sesion;
    ListView lista;
    ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lista = (ListView) findViewById(R.id.IdLista);
        setSupportActionBar(toolbar);
        dbu = new UsuarioBD(this);
        dbe = new EventoBD(this);
        sesion = new Sesion(context);

        if(!sesion.loggedin().equals("")){
            Intent iniciar = new Intent(this,VistaAdminEvento.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(iniciar);
        }

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.registro) {
            Intent registro = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(registro);
            return true;
        } else if(id == R.id.ingreso) {
            Intent inicio = new Intent(MainActivity.this,InicioSesion.class);
            startActivity(inicio);
            return true;
        }else if(id == R.id.ayuda) {
            Toast.makeText(this,"Proximamente",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
