package edu.example.pruebadosproyectoandres;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SearchView;

import edu.example.pruebadosproyectoandres.R;
import logica.ficheros.ListaProductos;
import logica.producto.ListaProductosSistema;
import logica.producto.Producto;
import logica.producto.filtros.Filtro;
import logica.producto.filtros.FiltroImagenVisible;
import logica.producto.filtros.FiltroPrecio;
import logica.producto.filtros.FiltroPrecioVisible;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivityCliente extends AppCompatActivity implements ProductosRecViewAdapter.ViewHolder.OnNoteListener {

    private RecyclerView productosRecView;
    public static ArrayList<Producto> productos = new ArrayList<>();
    private  ProductosRecViewAdapter adapter = new ProductosRecViewAdapter(this);
    private FloatingActionButton myButton;
    private ArrayList<Filtro> filtros;
    private PopupMenu popupMenu;
    private SearchView searchView;
    private ListaProductosSistema currLista = ListaProductosSistema.getInstance();
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        Bundle b= getIntent().getExtras();
        userID=b.getString("userID");

        //no love
        myButton = findViewById(R.id.floating_filter);
        //productosRecView = findViewById(R.id.contactsRecView);
        currLista.setProductosRecView(findViewById(R.id.contactsRecView));
        currLista.buildRecyclerView(this);

        crearFiltros();
        onFilterFabClick();
    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.nav_search);

        // getting search view of our item.
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search products");

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myButton.setVisibility(View.VISIBLE);
            }

        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //productosRecView.setVisibility(View.INVISIBLE);
                return false;
            }

        });
        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view
                //productosRecView.setVisibility(View.VISIBLE);
                filter(newText);
                return false;
            }
        });
        return true;
    }

    //Crear filtros disponibles - añadirlos a la lista de filtros
    public void crearFiltros(){
        filtros = new ArrayList<>();
        filtros.add(new FiltroPrecioVisible(R.id.precio_visible));
        filtros.add(new FiltroImagenVisible(R.id.tiene_imagen));
        filtros.add(new FiltroPrecio(R.id.limite_precio));
    }

    //aplicar los filtros que estén checked a un producto especifico
    public boolean aplicarFiltros(Producto producto){
        for(Filtro filtro: filtros){
            if(popupMenu.getMenu().findItem(filtro.getId()).isChecked()){
                if(!filtro.filtrar(producto))
                    return false;
            }
        }
        return true;
    }

    //filtrar la lista de productos en base a un texto y a los filtros seleccionados
    private void filter(String text){
        ArrayList<Producto> listaFiltrada = new ArrayList<>();

        for(Producto producto: currLista.getListaProductos()){
            if(aplicarFiltros(producto) && producto.getNombre().contains(text))
                listaFiltrada.add(producto);
        }
        currLista.getAdapter().setProductos(listaFiltrada, this);
    }

    //crear el Recycler View que hace las veces de la lista
    private void buildRecyclerView(){
        productosRecView.setLayoutManager(new GridLayoutManager(this, 2));
        productosRecView.setHasFixedSize(true);
        adapter = new ProductosRecViewAdapter(this);
        productosRecView.setAdapter(adapter);

        productos = ListaProductos.getListaProductos();
        adapter.setProductos(productos, this);
        //productos.add(new Producto("perro", 10, 28.0, "grande", "https://estaticos.muyinteresante.es/media/cache/1140x_thumb/uploads/images/gallery/59bbb29c5bafe878503c9872/husky-siberiano-bosque.jpg", false, "jorge_l8"));

        // unir productos = lista de productos del sistema

    }

    //mandar el intent a Gallery Activity una vez que se selecciono un producto


    //Crear el floating action button
    public void onFilterFabClick(){
        popupMenu = new PopupMenu(this, myButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked((!item.isChecked()));
                popupMenu.show();

                switch (item.getItemId()) {
                    case R.id.limite_precio:
                        if (popupMenu.getMenu().getItem(2).isChecked())
                            showAlertDialog(popupMenu.getMenu().getItem(2));
                        else
                            filter("" + searchView.getQuery());
                        break;
                    default:
                        filter("" + searchView.getQuery());
                        break;
                }

                return false;
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
    }

    //mostrar el alert dialog que le pide al usuario el limite de precio del producto
    public void showAlertDialog(MenuItem popupMenu){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Precio Límite");
        alert.setMessage("Introduzca el precio límite");
        final EditText input = new EditText(this);
        input.setHint("0");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FiltroPrecio newFiltro = new FiltroPrecio(R.id.limite_precio);
                Integer precio;
                if(String.valueOf(input.getText()).length() != 0)
                    precio = Integer.parseInt(String.valueOf(input.getText()));
                else
                    precio = 0;
                newFiltro.setPrecio(precio);
                filtros.remove(2);
                filtros.add(newFiltro);
                filter("" + searchView.getQuery());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                popupMenu.setChecked(!popupMenu.isChecked());
            }
        });
        alert.show();
    }

    @Override
    public void onProductClick(int position) {
        Intent intent = new Intent(this, GalleryActivity.class);
        Producto currProd = currLista.getAdapter().getProductos().get(position);

        intent.putExtra("product_name", currProd.getNombre());
        intent.putExtra("image_url", currProd.getUbicImg());
        intent.putExtra("precio_producto", "$" + currProd.getPrecio() + "");
        intent.putExtra("descripcion_producto", currProd.getDescripcion());
        intent.putExtra("userID", userID);
        intent.putExtra("disponibilidad", currProd.getDisponibilidad() + "");
        startActivity(intent);
    }

    public void activityPerfil(View view){
        Intent intent = new Intent(this, InterfazPerfil.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }

}