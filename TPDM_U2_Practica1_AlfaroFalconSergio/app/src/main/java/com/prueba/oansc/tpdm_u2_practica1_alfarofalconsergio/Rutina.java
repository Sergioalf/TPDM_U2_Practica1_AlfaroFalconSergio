package com.prueba.oansc.tpdm_u2_practica1_alfarofalconsergio;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class Rutina {

    int id;
    String dias;
    String descripcion;
    int calorias;
    BaseDeDatos baseDatos;

    public Rutina (Activity pantalla) {
        baseDatos = new BaseDeDatos(pantalla, "Gimnasio", null, 1);
    }

    public Rutina (int id, String dias, String descripcion, int calorias) {
        this.id = id;
        this.dias = dias;
        this.descripcion = descripcion;
        this.calorias = calorias;
    }

    public Rutina[] obtenerRutinas () {
        Rutina[] resultado = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String[] columnas = {"ID", "DIAS", "DESCRIPCION", "CALORIAS"};
            Cursor registros = leible.query("RUTINA", columnas, null, null, null, null, null, null);
            if (registros.moveToFirst()) {
                resultado = new Rutina [registros.getCount()];
                int i = 0;
                do {
                    resultado [i++] = new Rutina(registros.getInt(0), registros.getString(1), registros.getString(2), registros.getInt(3));
                } while (registros.moveToNext());
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return resultado;
    }

    public boolean insertar (Rutina rutina) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            ContentValues datosDeRutina = new ContentValues();
            datosDeRutina.put("DIAS", rutina.dias);
            datosDeRutina.put("DESCRIPCION", rutina.descripcion);
            datosDeRutina.put("CALORIAS", rutina.calorias);
            long funciono = escribible.insert("RUTINA", "ID", datosDeRutina);
            escribible.close();
            if (funciono < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public Rutina consultar (int id) {
        Rutina recuperada = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String[] columnas = {"ID", "DIAS", "DESCRIPCION", "CALORIAS"};
            String[] argumentos = {id+""};
            Cursor resultado = leible.query("RUTINA", columnas, "ID = ?", argumentos, null, null , null, null);
            if (resultado.moveToFirst()) {
                recuperada = new Rutina(resultado.getInt(0), resultado.getString(1), resultado.getString(2), resultado.getInt(3));
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return recuperada;
    }

    public Rutina consultar (String descripcion) {
        Rutina recuperada = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String[] columnas = {"ID", "DIAS", "DESCRIPCION", "CALORIAS"};
            String[] argumentos = {descripcion};
            Cursor resultado = leible.query("RUTINA", columnas, "DESCRIPCION = ?", argumentos, null, null , null, null);
            if (resultado.moveToFirst()) {
                recuperada = new Rutina(resultado.getInt(0), resultado.getString(1), resultado.getString(2), resultado.getInt(3));
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return recuperada;
    }

    public boolean actualizar (Rutina rutina) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            ContentValues datosDeRutina = new ContentValues();
            datosDeRutina.put("DIAS", rutina.dias);
            datosDeRutina.put("DESCRIPCION", rutina.descripcion);
            datosDeRutina.put("CALORIAS", rutina.calorias);
            String[] id = {rutina.id + ""};
            long resultado = escribible.update("RUTINA", datosDeRutina, "ID = ?", id);
            escribible.close();
            if (resultado < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public boolean eliminar (Rutina rutina) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            String[] id = {rutina.id + ""};
            long resultado = escribible.delete("RUTINA", "ID = ?", id);
            escribible.close();
            if (resultado < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

}
