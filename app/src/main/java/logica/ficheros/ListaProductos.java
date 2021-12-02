package logica.ficheros;

import logica.producto.Producto;
import logica.usuario.Cliente;
import logica.usuario.Encrypt;
import org.json.JSONException;
import org.json.simple.JSONObject;
import com.google.*;

import java.util.ArrayList;

public class ListaProductos {

    private static ArrayList<Producto> listaProductos;
    private static org.json.simple.JSONArray listaProductosJSON;

    /**constructor
     */
    public ListaProductos() {
        ListaProductos.listaProductos=new ArrayList<>();
        listaProductosJSON=new org.json.simple.JSONArray();
    }

    //getsetters

    public static ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public static void setListaProductos(ArrayList<Producto> listaProductos) {
        ListaProductos.listaProductos = listaProductos;
    }

    public static org.json.simple.JSONArray getListaProductosJSON() {
        return listaProductosJSON;
    }

    public static void setListaProductosJSON(org.json.simple.JSONArray listaProductosJSON) {
        ListaProductos.listaProductosJSON = listaProductosJSON;
    }

    //cosas que debe hacer la lista :)
    /**
    * almacenar todos los productos
    * mostrar todos los productos
    * buscar productos en la lista
    * */

    //agregar a lista JSON
    public static void agregarProductoAListaJSON(org.json.simple.JSONObject producto, org.json.simple.JSONArray lista){
        lista.add(producto);
    }

    //agregar a la lista normal

    public static void agregarProductoALista (Producto p){
        listaProductos.add(p);
    }

    //buscar en lista


    //buscar producto por nombre
    public static boolean productoExisteGral(String nombre){
        //false si no existe
        ArrayList<Producto> temp = listaProductos;
        for(int i=0;i<temp.size();i++){
            if (temp.get(i).getNombre()==nombre){
                return true;
            }
        }
        return false;
    }

    public static boolean productoExisteJSONGral(String nombre){
        String palabra;
        for(int i=0;i<listaProductos.size();i++) {
            org.json.simple.JSONObject json= (org.json.simple.JSONObject)listaProductosJSON.get(i);
            palabra=(String)json.get("name");
            if (palabra.compareTo(nombre)==0)
                return true;
        }
        return false;
    }

    public static Producto buscarProductoGral(String nombre){
        for(Producto p: listaProductos){
            if (p.getNombre().compareTo(nombre)==0){
                return p;
            }
        }
        return null;
    }


    public static void llenarListaEstaticaProductos() {
        LeerDatos leer=new LeerDatos();
        leer.leerListaProductos();
        org.json.simple.JSONObject json;
        for(int i=0;i<listaProductosJSON.size();i++){
            Producto p=new Producto();
            try {
                json = (org.json.simple.JSONObject) listaProductosJSON.get(i);
                p.setNombre(json.get("name").toString());
                p.setDescripcion(json.get("desc").toString());
                p.setUserID(json.get("userID").toString());
                p.setPrecio(Float.parseFloat(json.get("price").toString()));
                p.setCantidad(Integer.parseInt(json.get("quantity").toString()));
                p.setPrecioVisible(Boolean.parseBoolean(json.get("visible").toString()));
                p.setUbicImg(json.get("imgroute").toString());
                listaProductos.add(p);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }
}
