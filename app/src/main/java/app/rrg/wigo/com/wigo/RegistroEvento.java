package app.rrg.wigo.com.wigo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import app.rrg.wigo.com.wigo.Entities.Evento;
import app.rrg.wigo.com.wigo.Entities.Usuario;
import app.rrg.wigo.com.wigo.Utilidades.EventoBD;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;
import app.rrg.wigo.com.wigo.Utilidades.UsuarioBD;

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

    private static String APP_DIRECTORY = "WIGO/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "images";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private String mPath;

    private ImageView imagenViewEvento;
    private Button botonCargaImagen;
    // UI references.
    private Sesion sesion;
    private EventoBD db;
    private String imgEvento;
    private AutoCompleteTextView mNombreEventoView;
    private AutoCompleteTextView mDescripcionEventoView;
    private AutoCompleteTextView mHoraEventoView;
    private AutoCompleteTextView mFechaEventoView;
    private AutoCompleteTextView mPrecioEventoView;
    private AutoCompleteTextView mDireccionEventoView;
    private View mProgressView;
    private View mLoginFormView;

    DBHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_evento);
        sesion = new Sesion(this);
        // Set up the login form.

        conexion = new DBHelper(this);

        imagenViewEvento = (ImageView) findViewById(R.id.imageViewEvento);
        botonCargaImagen = (Button) findViewById(R.id.buttonImgEvento);

        imgEvento = "";
        //Se decodifica la ruta de la fofto de perfil almacenada
        /*
        if(usuario.getFoto().equals("")){
            Log.i("-->SIN FOTO", "HOLI");
        }
        if(!usuario.getFoto().equals("")){
            if(usuario.getFoto().charAt(0) == 'c'){
                Uri uri = Uri.parse(usuario.getFoto());
                mSetImageView.setImageURI(uri);
            }else{
                Bitmap bitmap = BitmapFactory.decodeFile(usuario.getFoto());
                mSetImageView.setImageBitmap(bitmap);
            }
        }
         */

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

        botonCargaImagen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void showOptions(){
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroEvento.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which].equals("Tomar foto")){
                    openCamera();
                }else if(option[which].equals("Elegir de galeria")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();
        if(!isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }
        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis()/1000;
            String imageName = timestamp.toString() + ".jpg";
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;
            File newFile = new File(mPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    imgEvento = mPath;
                    Log.i("RUTAIMAGEN", "-> Uri = " + imgEvento);
                    imagenViewEvento.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imgEvento = path.toString();
                    Log.i("SELECT_PICTURE", "-> Uri = " + path);
                    imagenViewEvento.setImageURI(path);
                    break;

            }
        }
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
        private boolean flag = false;
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
            boolean registro = registrarEvento();
            if (registro){
                flag = true;
            }else{
                flag = false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (flag) {
                //registros
                Toast exito = Toast.makeText(getApplicationContext(), "Registro exitoso",Toast.LENGTH_SHORT);
                exito.show();
                finish();
            } else {
                Toast fracaso = Toast.makeText(getApplicationContext(), "Nombre de evento en uso",Toast.LENGTH_SHORT);
                fracaso.show();
                mNombreEventoView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private boolean registrarEvento() {
        UsuarioBD usbd = new UsuarioBD(this);
        AutoCompleteTextView nombre = (AutoCompleteTextView)findViewById(R.id.nombre_evento);
        AutoCompleteTextView descripcion = (AutoCompleteTextView)findViewById(R.id.descripcion_evento);
        AutoCompleteTextView hora = (AutoCompleteTextView)findViewById(R.id.hora_evento);
        AutoCompleteTextView fecha = (AutoCompleteTextView)findViewById(R.id.fecha_evento);
        AutoCompleteTextView precio = (AutoCompleteTextView)findViewById(R.id.precio_evento);
        AutoCompleteTextView direccionEvento = (AutoCompleteTextView)findViewById(R.id.direccion_evento);
        Usuario usuario = usbd.buscarUsuarios(sesion.loggedin());
        int creador = usuario.getId();
        db = new EventoBD(RegistroEvento.this);
        Evento evento = new Evento(nombre.getText().toString(),descripcion.getText().toString(),hora.getText().toString(),fecha.getText().toString(),precio.getText().toString(),direccionEvento.getText().toString(),creador,imgEvento);
        Log.i("---> Base de datos: ", evento.toString());
        if(validarEvento(nombre.getText().toString())){
            Log.i("---> Base de datos: ", "ingresando eventos");
            db.insertEvento(evento);
            return true;
        }else{
            return false;
        }
    }

    private boolean validarEvento(String nombre){
        boolean validacion = true;
        List list = db.loadEventos();
        for (int i = 0; i < list.size(); i++) {
            Evento evento = (Evento) list.get(i);
            if(evento.getNombre().toString().equals(nombre)){
                validacion = false;
            }
        }
        return validacion;
    }
}


