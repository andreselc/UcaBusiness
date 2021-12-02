package edu.example.pruebadosproyectoandres;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import logica.producto.validaciones.Validaciones;

import java.io.File;
import java.io.IOException;

public class InterfazNuevaPublicacion extends AppCompatActivity {
    //variable para almacenar la direccion de la imagen en el telefono
    static private String URIFoto="files/img/product-category-icon-5.jpg";
    //constante para comparar la salida de la imagen
    int SELECT_PICTURE = 200;
    private final Validaciones a = new Validaciones();
    private boolean flagNombre=false,flagDesc=false,flagCant=false,flagPrecio=false;
    private Bitmap bitmap;

    public Boolean validar(){
        String temp;
        flagNombre=a.validar(((EditText)findViewById(R.id.editTextTextPersonName)).getText().toString(),1);
        flagDesc=a.validar((((EditText)findViewById(R.id.editTextTextPersonName2)).getText().toString()),2);
        flagCant=a.validar((((EditText)findViewById(R.id.editTextNumber)).getText().toString()),4);
        flagPrecio=a.validar((((EditText)findViewById(R.id.editTextNumberDecimal)).getText().toString()),3);

        if ((flagCant)&&(flagPrecio)&&(flagNombre)&&(flagDesc))return true;

      if (!flagCant){//numero invalido
          temp=((EditText)findViewById(R.id.editTextNumber)).getText().toString();
          temp=a.deleteFromString(temp, false);
          ((EditText)findViewById(R.id.editTextNumber)).setText(temp);
          System.out.println("cant:"+((EditText)findViewById(R.id.editTextNumber)).getText());
      }

      if (!flagPrecio){//precio invalido
          temp=((EditText)findViewById(R.id.editTextNumberDecimal)).getText().toString();
          temp=a.deleteFromString(temp, true);
          ((EditText)findViewById(R.id.editTextNumberDecimal)).setText(temp);
        System.out.println("precio:"+((EditText)findViewById(R.id.editTextNumberDecimal)).getText());}

    return false;}


    public void siguienteActivity(View v) {
        if (validar()) alerta("Producto creado exitosamente.");
        else {
            toasts();
            if (((EditText)findViewById(R.id.editTextNumber)).getText().toString().isEmpty()){
            }
            if (((EditText)findViewById(R.id.editTextNumberDecimal)).getText().toString().isEmpty()){
            }}

    }

    private void toasts(){
        //mostrar los errores respectivos:
        //cantidad
        if (!flagCant) Toast.makeText(InterfazNuevaPublicacion.this,"Cantidad debe ser entre 1 y 999 y sólo debe contener dígitos",Toast.LENGTH_SHORT).show();
        //precio
        if (!flagPrecio) Toast.makeText(InterfazNuevaPublicacion.this,"Precio debe ser entre 1 y 999 y sólo debe contener dígitos y punto",Toast.LENGTH_SHORT).show();
        //descripcion
        if (!flagDesc) Toast.makeText(InterfazNuevaPublicacion.this,"Descripción debe tener entre 20 y 280 caracteres",Toast.LENGTH_LONG).show();
        //nombre
        if (!flagNombre) Toast.makeText(InterfazNuevaPublicacion.this,"Nombre debe tener entre 5 y 50 caracteres",Toast.LENGTH_LONG).show();

    }

    //intent para pasar el producto :)
    public void nuevoProducto() {
        //se crea el intent
        Intent i = new Intent();
        //se obtiene la string:
        //nombre
        EditText tempT = findViewById(R.id.editTextTextPersonName);
        String temp = tempT.getText().toString();
        i.putExtra("nombre", temp);
        //descripcion
        tempT = findViewById(R.id.editTextTextPersonName2);
        temp = tempT.getText().toString();
        i.putExtra("desc", temp);
        //precio
        tempT = findViewById(R.id.editTextNumberDecimal);
        temp = tempT.getText().toString();
        i.putExtra("precio", temp);
        //cantidad
        tempT = findViewById(R.id.editTextNumber);
        temp = tempT.getText().toString();
        i.putExtra("cantidad", temp);
        //visible
        Boolean tempS = ((Switch) findViewById(R.id.switch1)).isChecked();
        temp = tempS.toString();
        i.putExtra("visible", temp);
        //dir. imagen
        temp= URIFoto;
        i.putExtra("img", temp);

        setResult(RESULT_OK, i);
    }

    //para seleccionar la imagen uwu
    //se activa al darle click al boton uwu
    public void solicitarImagen (View v){
        //crear Intent
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        //pasar constante para comparar el intent
        startActivityForResult(Intent.createChooser(i,"Seleccione imagen"),SELECT_PICTURE);}

    //esta ocurre al seleccionarse la img
    public void onActivityResult(int requestCode, int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            //compara el codigo de resultado con la constante
            if(requestCode==SELECT_PICTURE){
                //obtiene la direccion de imagen
                Uri uriDeImagen=data.getData();
                if (null!=uriDeImagen){
                    //carga la imagen en el layout
                    ImageView a= findViewById(R.id.imageView2);
                    //se resizea la imagen
                    resize(uriDeImagen,a);

                    URIFoto = uriDeImagen.toString();
                }
            }
        }


    }

    private void resize(Uri uriDeImagen,ImageView a){
        try {
            bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uriDeImagen);
            a.setImageBitmap(bitmap);
            resizeImage(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //para resizear la imagen
    public void resizeImage(ImageView image){
        Bitmap resized = Bitmap.createScaledBitmap(bitmap,450,450,true);
        image.setImageBitmap(resized);
    }

    //para construir la alerta al usuario
    public void alerta(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(InterfazNuevaPublicacion.this);

        //mensaje de la alerta
        builder.setMessage(mensaje);
        //título de la alerta
        builder.setTitle("Alerta");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialog, which) -> {//datos aceptados
                    nuevoProducto();
                    dialog.cancel();
                    finish();
            });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_publicacion);
        setTitle("Crear Producto");
       EditText cant = findViewById(R.id.editTextNumber);
       EditText precio = findViewById(R.id.editTextNumberDecimal);
       //resize(Uri.parse(URIFoto),findViewById(R.id.imageView2));
        copyPasteDisable(cant);
        copyPasteDisable(precio);
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                esconderTeclado(InterfazNuevaPublicacion.this);
                return false;
            }
        });
        }

        //para esconder el teclado al hacer click en otro lado
    public static void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    //para evitar que hagan copy/paste/seleccion en textviews
    private void copyPasteDisable(TextView textview){
        textview.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });
        textview.setLongClickable(false);
        textview.setTextIsSelectable(false);
    }
}