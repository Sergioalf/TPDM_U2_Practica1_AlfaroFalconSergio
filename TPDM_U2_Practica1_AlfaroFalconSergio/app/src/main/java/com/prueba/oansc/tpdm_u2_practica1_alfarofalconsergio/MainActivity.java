package com.prueba.oansc.tpdm_u2_practica1_alfarofalconsergio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    String[] elementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar();
            }
        });
        lista = findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    consultar(elementos[position]);
                } catch (Exception e) {
                    miniMensaje(e.getMessage());
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Rutina rutina = new Rutina(this);
        Rutina[] rutinas = rutina.obtenerRutinas();
        ArrayAdapter<String> contenidoLista;
        if (rutinas == null) {
            String[] vacio = {};
            contenidoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vacio);
            miniMensaje("No hay Rutinas...");
        } else {
            elementos = new String[rutinas.length];
            for (int i = 0; i < elementos.length; i++) {
                elementos[i] = rutinas[i].descripcion;
            }
            contenidoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos);
        }
        lista.setAdapter(contenidoLista);
    }

    private void agregar () {
        Intent agregar = new Intent(MainActivity.this, Main2Activity.class);
        agregar.putExtra("registro", true);
        agregar.putExtra("descripcion", "");
        startActivity(agregar);
    }

    private void consultar (String descripcion) {
        Intent consultar = new Intent(MainActivity.this, Main2Activity.class);
        consultar.putExtra("registro", false);
        consultar.putExtra("descripcion", descripcion);
        startActivity(consultar);
    }

    private void miniMensaje (String mensaje) {
        Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }

}
