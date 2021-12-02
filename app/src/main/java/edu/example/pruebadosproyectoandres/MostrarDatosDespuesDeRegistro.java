package edu.example.pruebadosproyectoandres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.*;

public class MostrarDatosDespuesDeRegistro extends AppCompatActivity {

    public void BuscarDatos(){
        Intent intent = getIntent();
        String correo=intent.getStringExtra("correo");
        Cliente currCliente = ListaUsuariosClientes.buscarUsuarioClientes(correo);
        Empresa currEmpresa = ListaUsuariosEmpresas.buscarUsuarioEmpresa(correo);

        if(currEmpresa!=null){
            TextView UserName = findViewById(R.id.nombreUsuario);
            UserName.setText(currEmpresa.getUserName());
            TextView tipoEmpresa = findViewById(R.id.tipoCuenta);
            tipoEmpresa.setText("Empresa");

        }else if(currCliente!=null){
            TextView UserName = findViewById(R.id.nombreUsuario);
            UserName.setText(currCliente.getUserName());
            TextView tipoEmpresa = findViewById(R.id.tipoCuenta);
            tipoEmpresa.setText("Cliente");
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos_despues_de_registro);
    }
}