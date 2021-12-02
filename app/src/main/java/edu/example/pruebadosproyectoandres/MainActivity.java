package edu.example.pruebadosproyectoandres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;


public class MainActivity extends AppCompatActivity {

    public void buttonPress(View view){
        Intent newIntent= new Intent(this, InterfazRegistro.class);
        startActivity(newIntent);
    }

    public void btnIniciarSesion(View v){

        //este carga el menú del cliente (buscar producto)
        //Intent newIntent = new Intent(this, MainActivityCliente.class);
        getSupportActionBar().setTitle("Busqueda de Productos");
        //o, alternativamente, se carga el menú de la empresa (crear producto)
        Intent newIntent=new Intent(this,MainActivityEmpresa.class);
        //getSupportActionBar().setTitle("Publicar Producto");
        startActivity(newIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListaUsuariosClientes listaC= new ListaUsuariosClientes();
        ListaUsuariosEmpresas listaE= new ListaUsuariosEmpresas();
        ListaUsuariosClientes.llenarListaEstaticaClientes();
        ListaUsuariosEmpresas.llenarListaEstaticaEmpresas();

        getSupportActionBar().hide();
    }
}
