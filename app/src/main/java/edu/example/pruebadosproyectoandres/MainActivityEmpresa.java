package edu.example.pruebadosproyectoandres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import logica.ficheros.GuardarDatosProducto;
import logica.producto.Producto;

import java.io.IOException;


public class MainActivityEmpresa extends AppCompatActivity {
    private Producto p = new Producto();
    private GuardarDatosProducto j = new GuardarDatosProducto();

    public void siguienteActivity(View v) {
        startActivityForResult(new Intent(MainActivityEmpresa.this, InterfazNuevaPublicacion.class),1);

    }

    //para recibir datos de menuPublicacion
    public void onActivityResult(int requestCode, int resultCode, Intent datos){
        Bundle bundle=datos.getExtras();
        super.onActivityResult(requestCode, resultCode, datos);
        if ((requestCode==1)&&(resultCode==RESULT_OK)){
            //agregar los datos al producto
            p.setNombre(bundle.getString("nombre"));
            p.setDescripcion(bundle.getString("desc"));
            p.setPrecio(Float.parseFloat(bundle.getString("precio")));
            p.setCantidad(Integer.parseInt(bundle.getString("cantidad")));
            p.setPrecioVisible(Boolean.parseBoolean(bundle.getString("visible")));
            p.setUbicImg(bundle.getString("img"));

            p.setUserID("USERID_A");//AQUI INSERTAR ID DEL USUARIO

            //aqui se agregaria a la lista de empresa (lista de empresa actualiza automaticamente lista gral)

            //comprueba que se agregaron los datos
            TextView temp1= findViewById(R.id.textView);
            temp1.setText(p.getNombre());

            //escribir al json: debe escribirse lista de empresa y lista gral tambien
            try {
                j.escribirArchivo(j.crearJsonProducto(p), MainActivityEmpresa.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(j.crearJsonProducto(p));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menú de Empresa");
        setContentView(R.layout.activity_main_crearproducto);

    }
}