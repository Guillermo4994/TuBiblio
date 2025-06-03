package com.example.tubiblio;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectionBD {
    private String ip = "192.168.1.35";
    private String usuario = "root";
    private String contraseña = "";
    private String bd = "tubiblio";


public Connection connect (){
    Connection connection = null;
    String connectionURL = null;
    try {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        connectionURL = "jdbc:jtds:sqlserver://"+this.ip+"/"+this.bd+";user"+this.usuario+";password"+this.contraseña+";";
        connection= DriverManager.getConnection(connectionURL);

    }catch (Exception e){
        e.printStackTrace();
        Log.e("Error de conexion SQL: ", e.getMessage());
    }
    return connection;}
}