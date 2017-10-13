package app.rrg.wigo.com.wigo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.rrg.wigo.com.wigo.Utilidades.Utilidades;

public class VistaAdminEvento extends AppCompatActivity {

    ConexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_admin_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Toast.makeText(VistaAdminEvento.this,"Resultado: ", Toast.LENGTH_LONG).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(VistaAdminEvento.this,RegistroEvento.class);
                startActivity(registro);
            }
        });

        conexion = new ConexionSQLiteHelper(this,"bd_wigo",null,1);
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
            Toast.makeText(this,"Proximamente",Toast.LENGTH_LONG).show();
            /*Intent registro = new Intent(VistaAdminEvento.this,LoginActivity.class);
            startActivity(registro);*/
            return true;
        } else if(id == R.id.mis_eventos) {
            Toast.makeText(this,"Proximamente",Toast.LENGTH_LONG).show();
            /*Intent inicio = new Intent(VistaAdminEvento.this,InicioSesion.class);
            startActivity(inicio);*/
            return true;
        }else if(id == R.id.cerrar_sesion) {
            Toast.makeText(this,"Proximamente",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        if(view.getId()==R.id.button) {
            consultar();
        }
    }

    private void consultar() {
        TextView info = (TextView)findViewById(R.id.textView);
        EditText nombre = (EditText)findViewById(R.id.editText);
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[] parametros = {nombre.getText().toString()};
        String[] campos = {Utilidades.CORREO_USUARIO};

        try{
            Cursor cursor =  db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.NOMBRE_USUARIO+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            info.setText(cursor.getString(0));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(this,"No encontrado",Toast.LENGTH_LONG).show();
        }
    }
}