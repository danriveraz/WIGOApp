package app.rrg.wigo.com.wigo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;
import app.rrg.wigo.com.wigo.Utilidades.UsuarioBD;

public class MainActivity extends AppCompatActivity {

    private UsuarioBD db;
    private Sesion sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new UsuarioBD(this);
        sesion = new Sesion(context);
        Log.i("---> Loggedin: ", sesion.loggedin());

        if(!sesion.loggedin().equals("")){
            Intent iniciar = new Intent(this,VistaAdminEvento.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(iniciar);
        }
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
