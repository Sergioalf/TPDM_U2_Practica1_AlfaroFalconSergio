package com.prueba.oansc.tpdm_u2_practica1_alfarofalconsergio;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText dias, descripcion, calorias;
    Button guardar, actualizar, eliminar;
    boolean actualizando;
    Rutina baseDeDatos;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dias = findViewById(R.id.dias);
        descripcion = findViewById(R.id.descripcion);
        calorias = findViewById(R.id.calorias);
        guardar = findViewById(R.id.guardar);
        actualizar = findViewById(R.id.actualizar);
        eliminar = findViewById(R.id.eliminar);
        baseDeDatos = new Rutina(this);
        actualizando = false;

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposValidos()){
                    guardar();
                }
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        obtenerID();
        cambiarContenido(getIntent().getBooleanExtra("registro", true));
    }

    //------------------------Métodos Principales---------------------------------------

    private void guardar () {
        final Rutina nuevaRutina = new Rutina(id, dias.getText().toString(), descripcion.getText().toString(), Integer.parseInt(calorias.getText().toString()));
        if (actualizando) {
            AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
            cuadro.setTitle("Atención").setMessage("¿Seguro que deseas actualizar la rutina?"). setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (baseDeDatos.actualizar(nuevaRutina)) {
                        miniMensaje("Se actualizó correctamente");
                        actualizando = false;
                        cambiarContenido(false);
                    } else {
                        miniMensaje("Error al actualizar");
                    }
                    dialog.dismiss();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        } else {
            if (baseDeDatos.insertar(nuevaRutina)) {
                miniMensaje("Se insertó correctamente");
                limpiarCampos();
            } else {
                miniMensaje("Error al insetar");
            }
        }
    }

    private void actualizar () {
        cambiarContenido(true);
        actualizando = true;
    }

    private void eliminar () {
        AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
        cuadro.setTitle("Atención").setMessage("¿Seguro que deseas eliminar la rutina?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (baseDeDatos.eliminar(new Rutina(id, dias.getText().toString(), descripcion.getText().toString(), Integer.parseInt(calorias.getText().toString())))){
                    miniMensaje("Se eliminó correctamente");
                    finish();
                } else {
                    miniMensaje("Error al eliminar");
                }
                dialog.dismiss();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    //---------------------------Métodos Auxiliares----------------------------------------

    private boolean camposValidos () {
        if (dias.getText().toString().equals("")) {
            miniMensaje("Escribe cuantos días");
            return false;
        }
        if (descripcion.getText().toString().equals("")) {
            miniMensaje("Escribe una descripción");
            return false;
        }
        if (calorias.getText().toString().equals("")) {
            miniMensaje("Escribe cantidad de calorías");
            return false;
        }
        try {
            int i = Integer.parseInt(calorias.getText().toString());
        } catch (NumberFormatException e) {
            miniMensaje("Escribe una cantidad entera de calorías");
            return false;
        }
        return true;
    }

    private void obtenerID () {
        String descripcion = getIntent().getStringExtra("descripcion");
        if (descripcion.equals("")) {
            id = 1;
        } else {
            Rutina actual = baseDeDatos.consultar(descripcion);
            id = actual.id;
        }
    }

    private void cambiarContenido (boolean cambio) {
        if (cambio) {
            guardar.setVisibility(View.VISIBLE);
            actualizar.setVisibility(View.INVISIBLE);
            eliminar.setVisibility(View.INVISIBLE);
            dias.setEnabled(true);
            descripcion.setEnabled(true);
            calorias.setEnabled(true);
        } else {
            guardar.setVisibility(View.INVISIBLE);
            actualizar.setVisibility(View.VISIBLE);
            eliminar.setVisibility(View.VISIBLE);
            dias.setEnabled(false);
            descripcion.setEnabled(false);
            calorias.setEnabled(false);
            mostrarRutina();
        }
    }

    private void limpiarCampos () {
        dias.setText("");
        descripcion.setText("");
        calorias.setText("");
    }

    private void mostrarRutina () {
        Rutina rutina = baseDeDatos.consultar(id);
        dias.setText(rutina.dias);
        descripcion.setText(rutina.descripcion);
        calorias.setText(rutina.calorias+"");
    }

    private void miniMensaje (String mensaje) {
        Toast.makeText(Main2Activity.this, mensaje, Toast.LENGTH_LONG).show();
    }

}
