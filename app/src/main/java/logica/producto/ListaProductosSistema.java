package logica.producto;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.example.pruebadosproyectoandres.GalleryActivity;
import edu.example.pruebadosproyectoandres.ProductosRecViewAdapter;
import logica.ficheros.GuardarDatosProducto;
import logica.ficheros.ListaProductos;
import logica.ficheros.ListaProductosEmpresa;

public class ListaProductosSistema{
    //apply singleton
    private static ListaProductosSistema instancia;
    private ArrayList<Producto> lista;
    private ProductosRecViewAdapter adapter;
    private RecyclerView productosRecView;

    private ListaProductosSistema(){
        lista = new ArrayList<>();
    }

    public static ListaProductosSistema getInstance(){
        if (instancia  == null)
            instancia = new ListaProductosSistema();
        return instancia;
    }

    //crear el Recycler View que hace las veces de la lista
    public void buildRecyclerView(Context context){
        productosRecView.setLayoutManager(new GridLayoutManager(context, 2));
        //japroductosRecView.setHasFixedSize(true);
        adapter = new ProductosRecViewAdapter(context);
        productosRecView.setAdapter(adapter);
        adapter.setProductos(lista, (ProductosRecViewAdapter.ViewHolder.OnNoteListener) context);
    }

    public void setLista(ArrayList<Producto> productos){
        lista.clear();
        System.out.println(productos);
        lista.addAll(productos);
    }

    public void setProductosRecView(RecyclerView productosRecView) {
        this.productosRecView = productosRecView;
    }

    public void updateListaProductos(String userID){
        lista.clear();
        ArrayList<Producto> listaProductos = ListaProductos.getListaProductos();
        System.out.println(listaProductos);
        for(Producto producto: listaProductos){
            if(producto.getUserID().equals(userID))
                lista.add(producto);
        }
    }

    public void eliminarProducto(String name){
        Producto prod = ListaProductos.buscarProductoGral(name);
        System.out.println(prod.toString());
        GuardarDatosProducto.eliminarProducto(prod);
        ListaProductos.eliminarProductoALista(prod);
        lista.remove(prod);
    }

    public ProductosRecViewAdapter getAdapter(){
        return adapter;
    }

    public ArrayList<Producto> getListaProductos(){
        return lista;
    }
}
