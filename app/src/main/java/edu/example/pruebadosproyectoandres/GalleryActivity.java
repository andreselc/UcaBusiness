package edu.example.pruebadosproyectoandres;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.example.pruebadosproyectoandres.R;
import logica.ficheros.ListaProductos;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.producto.ListaProductosSistema;

import org.w3c.dom.Text;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "Gallery Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getIncomingIntent();
    }

    @SuppressLint("LongLogTag")
    private void getIncomingIntent(){
        FloatingActionButton myButton = findViewById(R.id.deleteBtn);

        if(getIntent().hasExtra("product_name")
                && getIntent().hasExtra("image_url")
                && getIntent().hasExtra("descripcion_producto")){
            //if(getIntent().hasExtra("product_name")){

            String imageName = getIntent().getStringExtra("product_name");
            String imageUrl = getIntent().getStringExtra("image_url");
            String descripcionProducto = getIntent().getStringExtra("descripcion_producto");
            if(getIntent().hasExtra("userID")) {
                String userID = getIntent().getStringExtra("userID");
                if (ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(userID))
                    myButton.setVisibility(View.VISIBLE);
            }
            if(getIntent().hasExtra("precio_producto")) {
                String precioProducto = getIntent().getStringExtra("precio_producto");
                setImage(imageUrl, imageName, descripcionProducto, precioProducto);
            }
            else
                setImage(imageUrl, imageName, descripcionProducto, "");
        }
    }

    @SuppressLint({"LongLogTag", "SetTextI18n"})
    private void setImage(String imageUrl, String imageName, String descripcion, String precio){
        //private void setImage(String imageName){
        Log.d(TAG, "setImage: setting the image and name to widgets ");

        //TextViews que aparecen en los detalles del producto
        TextView nameTxt = findViewById(R.id.nombre_producto);
        TextView descripcionTxt = findViewById(R.id.descripcion_producto);

        //Pasarles el texto del producto seleccionado
        nameTxt.setText(imageName);
        if(precio.length()>0){
            TextView precioTxt = findViewById(R.id.precio_producto);
            precioTxt.setText(precio);
        }

        //ListaUsuariosClientes.correoExisteEnClientesJSON(correo));

        descripcionTxt.setText(descripcion);
        ImageView image = findViewById(R.id.myImage);
        Glide.with(this).asBitmap().load(imageUrl).into(image);
        Log.d(TAG, "setImage: Upload the image");
    }

    public void onDeleteClick(View view){
        //para construir la alerta al usuario
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //mensaje de la alerta
        builder.setMessage("¿Está seguro que desea eliminar el producto?");

        //título de la alerta
        builder.setTitle("Confirmación");

        //builder.setCancelable(false);
        builder.setPositiveButton("Seguro", (dialog, which) -> {//datos aceptados
            dialog.cancel();
            finish();
            ListaProductosSistema.getInstance().eliminarProducto(getIntent().getStringExtra("product_name"));
            ListaProductosSistema.getInstance().buildRecyclerView(getParent());
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.cancel();
        }
        );
        AlertDialog alerta = builder.create();
        alerta.show();
    }
}