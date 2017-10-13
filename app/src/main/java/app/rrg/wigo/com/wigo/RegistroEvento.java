package app.rrg.wigo.com.wigo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.rrg.wigo.com.wigo.Utilidades.Utilidades;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegistroEvento extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNombreEventoView;
    private AutoCompleteTextView mDescripcionEventoView;
    private AutoCompleteTextView mHoraEventoView;
    private AutoCompleteTextView mFechaEventoView;
    private AutoCompleteTextView mPrecioEventoView;
    private AutoCompleteTextView mDireccionEventoView;
    private View mProgressView;
    private View mLoginFormView;

    ConexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_evento);
        // Set up the login form.

        conexion = new ConexionSQLiteHelper(getApplicationContext(),"bd_wigo",null,1);

        mNombreEventoView = (AutoCompleteTextView) findViewById(R.id.nombre_evento);
        mDescripcionEventoView = (AutoCompleteTextView) findViewById(R.id.descripcion_evento);
        mHoraEventoView = (AutoCompleteTextView) findViewById(R.id.hora_evento);
        mFechaEventoView = (AutoCompleteTextView) findViewById(R.id.fecha_evento);
        mPrecioEventoView = (AutoCompleteTextView) findViewById(R.id.precio_evento);
        mDireccionEventoView = (AutoCompleteTextView) findViewById(R.id.direccion_evento);


        Button mEmailSignInButton = (Button) findViewById(R.id.boton_registro_evento);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNombreEventoView.setError(null);
        mDescripcionEventoView.setError(null);
        mHoraEventoView.setError(null);
        mFechaEventoView.setError(null);
        mPrecioEventoView.setError(null);
        mDireccionEventoView.setError(null);

        // Store values at the time of the login attempt.
        String nombre = mNombreEventoView.getText().toString();
        String descripcion = mDescripcionEventoView.getText().toString();
        String hora = mHoraEventoView.getText().toString();
        String fecha = mFechaEventoView.getText().toString();
        String precio = mPrecioEventoView.getText().toString();
        String direccion = mDireccionEventoView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        if (TextUtils.isEmpty(nombre)) {
            mNombreEventoView.setError(getString(R.string.error_field_required));
            focusView = mNombreEventoView;
            cancel = true;
        }else if (TextUtils.isEmpty(descripcion)) {
            mDescripcionEventoView.setError(getString(R.string.error_field_required));
            focusView = mDescripcionEventoView;
            cancel = true;
        }else if (TextUtils.isEmpty(hora)) {
            mHoraEventoView.setError(getString(R.string.error_field_required));
            focusView = mHoraEventoView;
            cancel = true;
        }else if (TextUtils.isEmpty(fecha)) {
            mFechaEventoView.setError(getString(R.string.error_field_required));
            focusView = mFechaEventoView;
            cancel = true;
        }else if (TextUtils.isEmpty(direccion)) {
            mDireccionEventoView.setError(getString(R.string.error_field_required));
            focusView = mDireccionEventoView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(nombre, descripcion, hora, fecha, precio, direccion);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mNombre;
        private final String mDescripcion;
        private final String mHora;
        private final String mFecha;
        private final String mPrecio;
        private final String mDireccion;

        UserLoginTask(String nombre, String descripcion, String hora, String fecha, String precio, String direccion) {
            mNombre = nombre;
            mDescripcion = descripcion;
            mHora = hora;
            mFecha = fecha;
            mPrecio = precio;
            mDireccion = direccion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }


            // TODO: register the new account here.

            registrarEvento();

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void registrarEvento() {
        EditText nombre = (EditText)findViewById(R.id.nombre_evento);
        EditText descripcion = (EditText)findViewById(R.id.descripcion_evento);
        EditText hora = (EditText)findViewById(R.id.hora_evento);
        EditText fecha = (EditText)findViewById(R.id.fecha_evento);
        EditText precio = (EditText)findViewById(R.id.precio_evento);
        EditText direccion = (EditText)findViewById(R.id.direccion_evento);

        SQLiteDatabase bd = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.NOMBRE_EVENTO,nombre.getText().toString());
        values.put(Utilidades.DESCRIPCION_EVENTO, descripcion.getText().toString());
        values.put(Utilidades.HORA_EVENTO, hora.getText().toString());
        values.put(Utilidades.FECHA_EVENTO, fecha.getText().toString());
        values.put(Utilidades.PRECIO_EVENTO, precio.getText().toString());
        values.put(Utilidades.DIRECCION_EVENTO, direccion.getText().toString());
        values.put(Utilidades.CREADOR_EVENTO, 1);
        Long resultado = bd.insert(Utilidades.TABLA_EVENTO,Utilidades.NOMBRE_EVENTO,values);
        bd.close();
    }
}


