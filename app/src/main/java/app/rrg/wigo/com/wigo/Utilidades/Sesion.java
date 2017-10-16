package app.rrg.wigo.com.wigo.Utilidades;

import android.content.Context;
import android.content.SharedPreferences;

public class Sesion {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public Sesion(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("myapp", context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(String user){
        editor.putString("LoggedIn" , user);
        editor.commit();
    }

    public String loggedin(){
        return prefs.getString("LoggedIn", "");
    }
}
