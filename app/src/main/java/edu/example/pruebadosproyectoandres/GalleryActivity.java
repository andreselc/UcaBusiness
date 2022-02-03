package edu.example.pruebadosproyectoandres;

import static logica.ficheros.ListaUsuariosEmpresas.buscarUsuarioEmpresa;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import logica.producto.Producto;
import logica.producto.filtros.FiltroPrecio;
import logica.usuario.Empresa;

import org.w3c.dom.Text;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "Gallery Activity";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIncomingIntent();

        Button btnComprar = findViewById(R.id.btnComprar);
        Button btnReportar = findViewById(R.id.btnReportar);
        if(ListaUsuariosClientes.correoExisteEnClientesJSON(getIntent().getStringExtra("userID"))){
            btnComprar.setVisibility(View.VISIBLE);
            btnReportar.setVisibility(View.VISIBLE);
        }
      }

    @SuppressLint("LongLogTag")
    private void getIncomingIntent(){
        FloatingActionButton myButton = findViewById(R.id.deleteBtn);
        FloatingActionButton myButton2 = findViewById(R.id.modifyBtn);
        TextView disponibilidad = findViewById(R.id.cantidad_disponibles);


        if(getIntent().hasExtra("product_name")
                && getIntent().hasExtra("image_url")
                && getIntent().hasExtra("descripcion_producto")
                && getIntent().hasExtra("userID")
                && getIntent().hasExtra("precio_producto")
                && getIntent().hasExtra("disponibilidad")){

            String imageName = getIntent().getStringExtra("product_name");
            String imageUrl = getIntent().getStringExtra("image_url");
            String descripcionProducto = getIntent().getStringExtra("descripcion_producto");
            String disponibilidadActual = getIntent().getStringExtra("disponibilidad");
            String userID = getIntent().getStringExtra("userID");
            if (ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(userID)) {
                myButton.setVisibility(View.VISIBLE);
                myButton2.setVisibility(View.VISIBLE);
                disponibilidad.setVisibility(View.VISIBLE);
            }
            String precioProducto = getIntent().getStringExtra("precio_producto");
            setImage(imageUrl, imageName, descripcionProducto, precioProducto, disponibilidadActual);
        }
    }

    @SuppressLint({"LongLogTag", "SetTextI18n"})
    private void setImage(String imageUrl, String imageName, String descripcion, String precio, String disponibilidad){
        //private void setImage(String imageName){

        //TextViews que aparecen en los detalles del producto
        TextView nameTxt = findViewById(R.id.nombre_producto);
        TextView descripcionTxt = findViewById(R.id.descripcion_producto);
        TextView disponibilidadTxt = findViewById(R.id.cantidad_disponibles);

        //Pasarles el texto del producto seleccionado
        TextView precioTxt = findViewById(R.id.precio_producto);
        nameTxt.setText(imageName);
        String userID = getIntent().getStringExtra("userID");
        String textoPrecio;
        Producto producto = ListaProductos.buscarProductoGral(getIntent().getStringExtra("product_name"));

        if (ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(userID))
            textoPrecio = precio;
        else{
            if(producto.isPrecioVisible())
                textoPrecio = precio;
            else
                textoPrecio = "- - -";
        }
        precioTxt.setText(textoPrecio);
        disponibilidadTxt.setText(disponibilidad + " disponibles");
        descripcionTxt.setText(descripcion);
        ImageView image = findViewById(R.id.myImage);
        /*File imgFile = new File(producto.getUbicImg());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }*/

        Glide.with(this).
                asBitmap().load(imageUrl).
                placeholder(android.R.drawable.progress_indeterminate_horizontal).
                error(android.R.drawable.stat_notify_error).
                into(image);
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
            finish();
            ListaProductosSistema.getInstance().eliminarProducto(getIntent().getStringExtra("product_name"));
            Toast.makeText(this, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void onModifyClick(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modificación de disponibilidad");
        alert.setMessage("Introduzca la nueva disponibilidad: ");
        final EditText input = new EditText(this);
        input.setHint(" 0");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Producto producto = ListaProductos.buscarProductoGral(getIntent().getStringExtra("product_name"));
                Integer disponibilidad = Integer.parseInt(String.valueOf(input.getText()));
                if(disponibilidad <= 0 || disponibilidad > 1000) //validar cantidadd
                    Toast.makeText(GalleryActivity.this, "Disponibilidad no valida", Toast.LENGTH_SHORT).show();
                else{
                    assert producto != null;
                    producto.setDisponibilidad(disponibilidad);
                    Toast.makeText(GalleryActivity.this, "Disponibilidad modificada exitosamente", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
    public void goToWhatsapp(View view){
        String userID = getIntent().getStringExtra("productID");
        String linkWha = "";
        if (ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(userID)) {
            Empresa empresa = ListaUsuariosEmpresas.buscarUsuarioEmpresa(userID);
            linkWha = empresa.getLinkWhattsApp();
            Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(linkWha));
            startActivity(browse);
        }
    }

    public void goToReportar(View view){
        Intent intent = new Intent(this, InterfazReportarPublicacion.class);
        startActivity(intent);
    }
}