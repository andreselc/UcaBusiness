package edu.example.pruebadosproyectoandres;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import logica.ficheros.*;
import logica.producto.ListaProductosSistema;
import logica.producto.Producto;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivityEmpresa extends AppCompatActivity implements ProductosRecViewAdapter.ViewHolder.OnNoteListener{
    private Producto p = new Producto();
    private GuardarDatosProducto j = new GuardarDatosProducto();
    public static ArrayList<Producto> productos = new ArrayList<>();
    private LeerDatos l=new LeerDatos();
    private String userID="n/a";
    //private RecyclerView productosRecView;
    //private  ProductosRecViewAdapter adapter = new ProductosRecViewAdapter(this);
    private ListaProductosSistema currLista = ListaProductosSistema.getInstance();

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

            currLista.updateListaProductos(userID);
            currLista.buildRecyclerView(this);
        }}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_crearproducto);

        //esto permite obtener los datos del primer activity
        Bundle b= getIntent().getExtras();
        userID=b.getString("userID");
        setTitle("Menú de Empresa");

        //productosRecView = findViewById(R.id.companiesRecView);
        currLista.setProductosRecView(findViewById(R.id.companiesRecView));

        currLista.updateListaProductos(userID);
        currLista.buildRecyclerView(this);
    }

    public void activityPerfil(View view) {
        Intent intent = new Intent(this, InterfazPerfil.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }

    /*public void updateListaProductos(){
        ArrayList<Producto> listaProductos = ListaProductos.getListaProductos();
        for(Producto producto: listaProductos){
            if(producto.getUserID().equals(userID))
                productos.add(producto);
        }
    }*/

    //crear el Recycler View que hace las veces de la lista
    /*private void buildRecyclerView(){
        productosRecView.setLayoutManager(new GridLayoutManager(this, 2));
        productosRecView.setHasFixedSize(true);
        adapter = new ProductosRecViewAdapter(this);
        productosRecView.setAdapter(adapter);
        adapter.setProductos(productos, this);
    }*/

    @Override
    public void onProductClick(int position) {
        Intent intent = new Intent(this, GalleryActivity.class);

        intent.putExtra("product_name", currLista.getAdapter().getProductos().get(position).getNombre());
        intent.putExtra("image_url", currLista.getAdapter().getProductos().get(position).getUbicImg());

        //si el producto tiene el precio visible
        if(currLista.getAdapter().getProductos().get(position).isPrecioVisible())
            intent.putExtra("precio_producto", "$" + currLista.getAdapter().getProductos().get(position).getPrecio() + "");
        intent.putExtra("descripcion_producto", currLista.getAdapter().getProductos().get(position).getDescripcion());
        intent.putExtra("userID", userID);
        startActivity(intent);
        //startActivityForResult(intent,1);
    }
}