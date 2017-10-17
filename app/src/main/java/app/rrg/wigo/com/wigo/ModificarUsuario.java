package app.rrg.wigo.com.wigo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.Log;
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

import app.rrg.wigo.com.wigo.Entities.Usuario;
import app.rrg.wigo.com.wigo.Utilidades.Sesion;
import app.rrg.wigo.com.wigo.Utilidades.UsuarioBD;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ModificarUsuario extends AppCompatActivity implements LoaderCallbacks<Cursor> {

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
    private UsuarioBD db;
    private Sesion sesion;
    private AutoCompleteTextView mNombreView;
    private AutoCompleteTextView mDireccionView;
    private AutoCompleteTextView mTelefonoView;
    private AutoCompleteTextView mCelularView;
    private AutoCompleteTextView mEmpresaView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        sesion = new Sesion(this);
        db = new UsuarioBD(this);
        Usuario usuario = db.buscarUsuarios(sesion.loggedin());
        // Set up the login form.
        mNombreView = (AutoCompleteTextView) findViewById(R.id.nombrePersonaM);
        mDireccionView = (AutoCompleteTextView) findViewById(R.id.direccionM);
        mTelefonoView = (AutoCompleteTextView) findViewById(R.id.telefonoM);
        mCelularView = (AutoCompleteTextView) findViewById(R.id.celularM);
        mEmpresaView = (AutoCompleteTextView) findViewById(R.id.empresaM);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.correoM);

        mNombreView.setText(usuario.getNombre());
        mDireccionView.setText(usuario.getDireccion());
        mTelefonoView.setText(usuario.getTelefono());
        mCelularView.setText(usuario.getCelular());
        mEmpresaView.setText(usuario.getNombreEmpresa());
        mEmailView.setText(usuario.getCorreo());
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.contrasenaM);
        mPasswordView.setText(usuario.getContrasena());
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_buttonM);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
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
        mNombreView.setError(null);
        mDireccionView.setError(null);
        mTelefonoView.setError(null);
        mCelularView.setError(null);
        mEmpresaView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String nombre = mNombreView.getText().toString();
        String direccion = mDireccionView.getText().toString();
        String telefono = mTelefonoView.getText().toString();
        String celular = mCelularView.getText().toString();
        String empresa = mEmpresaView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(nombre)) {//Check for a valid name
            mNombreView.setError(getString(R.string.error_field_required));
            focusView = mNombreView;
            cancel = true;
        }else if (TextUtils.isEmpty(direccion)) { //Check for a valid address
            mDireccionView.setError(getString(R.string.error_field_required));
            focusView = mDireccionView;
            cancel = true;
        }else if (TextUtils.isEmpty(telefono)) {//Check for a valid phone
            mTelefonoView.setError(getString(R.string.error_field_required));
            focusView = mTelefonoView;
            cancel = true;
        }else if (TextUtils.isEmpty(empresa)) {//Check for a valid company
            mEmpresaView.setError(getString(R.string.error_field_required));
            focusView = mEmpresaView;
            cancel = true;
        }else if (TextUtils.isEmpty(email)) {//Check for a valid email
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }else if(!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
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
            mAuthTask = new UserLoginTask(nombre, direccion, telefono, celular, empresa, email, password);
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(ModificarUsuario.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private boolean flag = false;
        private final String mNombre;
        private final String mDireccion;
        private final String mTelefono;
        private final String mCelular;
        private final String mEmpresa;
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String nombre, String direccion, String telefono, String celular, String empresa, String email,
                      String password) {
            mNombre = nombre;
            mDireccion = direccion;
            mTelefono = telefono;
            mCelular = celular;
            mEmpresa = empresa;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            boolean registro = modificarUsuario();
            if(registro){
                Intent iniciar = new Intent(ModificarUsuario.this,VistaAdminEvento.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iniciar);
                return true;
            }
            else {
                return false;
            }
        }

        private boolean modificarUsuario() {

            AutoCompleteTextView nombre = (AutoCompleteTextView)findViewById(R.id.nombrePersonaM);
            EditText contrasena = (EditText)findViewById(R.id.contrasenaM);
            AutoCompleteTextView correo = (AutoCompleteTextView)findViewById(R.id.correoM);
            AutoCompleteTextView direccion = (AutoCompleteTextView)findViewById(R.id.direccionM);
            AutoCompleteTextView telefono = (AutoCompleteTextView)findViewById(R.id.telefonoM);
            AutoCompleteTextView celular = (AutoCompleteTextView)findViewById(R.id.celularM);
            AutoCompleteTextView empresa = (AutoCompleteTextView)findViewById(R.id.empresaM);
            Usuario user = db.buscarUsuarios(sesion.loggedin());
            Usuario usuario = new Usuario(nombre.getText().toString(),
                    correo.getText().toString(), direccion.getText().toString(), telefono.getText().toString(),
                    celular.getText().toString(), empresa.getText().toString(), contrasena.getText().toString());
            usuario.setId(user.getId());
            if(validarUsuariosLog(correo.getText().toString())){
                Log.i("---> Base de datos: ", usuario.toString());
                Log.i("---> Base de datos: ", "Modificando Usuarios....");
                db.updateUsuario(usuario);
                flag = true;
                return true;
            }else{
                flag = false;
                return false;
            }
        }

        private boolean validarUsuariosLog(String correo) {
            boolean validacion = true;
            List list = db.loadUsuarios();
            for (int i = 0; i < list.size(); i++) {
                Usuario usuario = (Usuario) list.get(i);
                if(usuario.getCorreo().toString().equals(correo)){
                    validacion = false;
                    if(correo.equals(sesion.loggedin())){
                        Log.i("---> VALIDACION: ", "Debería entrar aquí");
                        validacion = true;
                    }
                }
                Log.i("---> Base de datos: ", usuario.getCorreo());
                Log.i("---> LoggedIn: ", sesion.loggedin());
            }
            return validacion;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (flag) {
                sesion.setLoggedin(mEmailView.getText().toString());
                Toast exito = Toast.makeText(getApplicationContext(), "Perfil guardado",Toast.LENGTH_SHORT);
                exito.show();
                finish();
            } else {
                Toast fracaso = Toast.makeText(getApplicationContext(), "Correo en uso",Toast.LENGTH_SHORT);
                fracaso.show();
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

