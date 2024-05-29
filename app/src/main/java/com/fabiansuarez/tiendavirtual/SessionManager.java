package com.fabiansuarez.tiendavirtual;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {


    private static final String PREF_NAME = "MyAppSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static SessionManager instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Constructor privado para evitar la creación de instancias directas
    private SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Método estático para obtener la instancia Singleton de SessionManager
    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    // Setear el estado de inicio de sesión
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    // Verificar si el usuario está conectado
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Borrar datos de sesión
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }


  /*  // Obtener la instancia Singleton de SessionManager
    SessionManager sessionManager = SessionManager.getInstance(getApplicationContext());

// Para iniciar sesión
sessionManager.setLogin(true);

// Para verificar si el usuario está conectado
if (sessionManager.isLoggedIn()) {
        // El usuario está conectado
    } else {
        // El usuario no está conectado
    }

// Para cerrar sesión
sessionManager.logoutUser();*/



}
