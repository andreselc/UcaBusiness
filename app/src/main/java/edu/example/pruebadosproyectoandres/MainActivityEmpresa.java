package edu.example.pruebadosproyectoandres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import logica.ficheros.*;
import logica.producto.Producto;

import java.io.IOException;


public class MainActivityEmpresa extends AppCompatActivity {
    private Producto p = new Producto();
    private GuardarDatosProducto j = new GuardarDatosProducto();
    private LeerDatos l=new LeerDatos();
    private String userID="n/a";

    public void siguienteActivity(View v) {
        startActivityForResult(new Intent(MainActivityEmpresa.this, InterfazNuevaPublicacion.class),1);
    }

    //para recibir datos de menuPublicacion
    public void onActivityResult(int requestCode, int resultCode, Intent datos){
        if (datos!=null){
        Bundle bundle=datos.getExtras();
        super.onActivityResult(requestCode, resultCode, datos);
        if ((requestCode==1)&&(resultCode==RESULT_OK)){
            //agregar los datos al producto
            p=new Producto(bundle.getString("desc"),bundle.getString("nombre"),
                    Boolean.parseBoolean(bundle.getString("visible")),
                    Float.parseFloat(bundle.getString("precio")),Integer.parseInt(bundle.getString("cantidad")),
                    bundle.getString("img"),userID);

            //aqui se agregaria a la lista de empresa (lista de empresa actualiza automaticamente lista gral)
            //por ahora, solo lista de empresa gen
            l.leerListaProductos();
            ListaProductos.agregarProductoALista(p);
            GuardarDatosProducto.guardarProducto(MainActivityEmpresa.this);
        }}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menú de Empresa");
        //esto permite obtener los datos del primer activity
        Bundle b= getIntent().getExtras();
        userID=b.getString("userID");
        System.out.println("User ID en On Create: "+userID);
        setContentView(R.layout.activity_main_crearproducto);

    }
}