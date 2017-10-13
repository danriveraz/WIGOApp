package app.rrg.wigo.com.wigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import app.rrg.wigo.com.wigo.Entities.Usuario;
import app.rrg.wigo.com.wigo.Utilidades.UsuarioBD;

public class MainActivity extends AppCompatActivity {

    private UsuarioBD db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new UsuarioBD(this);
        mostrarUsuariosLog();
    }

    private void mostrarUsuariosLog() {
        List list = db.loadUsuarios();
        if (list.size() != 0){
            for (int i = 0; i < list.size(); i++) {
                Log.i("---> Base de datos: ", list.get(i).toString());
            }
        }
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
