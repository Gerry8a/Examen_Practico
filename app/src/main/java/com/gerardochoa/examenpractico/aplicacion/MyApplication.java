package com.gerardochoa.examenpractico.aplicacion;

import android.app.Application;

import com.gerardochoa.examenpractico.modelos.Persona;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance(); //configurar base de datos
        realm.close();


    }

    /**
     * Configurar la base de datos. Se puede encontrar en la documentación de Realm
     */
    private void setUpRealmConfig(){
        Realm.init(getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
    }

    /**
     * Trabajar con clases genéricas. Consulta la base de datos para determinar el ID
     */
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll(); //consultar la BD
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }

}
