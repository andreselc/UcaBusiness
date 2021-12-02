package logica.ficheros;

import logica.producto.Producto;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class ListaProductosEmpresa extends ListaProductos{

    private static ArrayList<Producto> listaProductosEmpresa;
    private static JSONArray listaProductosEmpresaJSON;

    public ListaProductosEmpresa() {
        ListaProductosEmpresa.listaProductosEmpresa=new ArrayList<>();
        listaProductosEmpresaJSON=new JSONArray();
    }

    //getter and setter

    public static ArrayList<Producto> getListaProductosEmpresa() {
        return listaProductosEmpresa;
    }

    public static void setListaProductosEmpresa(ArrayList<Producto> listaProductosEmpresa) {
        ListaProductosEmpresa.listaProductosEmpresa = listaProductosEmpresa;
    }

    //public static org.json.JSONArray getListaProductosEmpresaJSON() {
    //    return listaProductosEmpresaJSON;
   // }

    public static void setListaProductosEmpresaJSON(JSONArray listaProductosEmpresaJSON) {
        ListaProductosEmpresa.listaProductosEmpresaJSON = listaProductosEmpresaJSON;
    }

    //buscar producto por nombre
    public static boolean productoExiste(String nombre){
        //false si no existe
        ArrayList<Producto> temp = listaProductosEmpresa;
        for(int i=0;i<temp.size();i++){
            if (temp.get(i).getNombre()==nombre){
                return true;
            }
        }
        return false;
    }

  /**  public static boolean productoExisteJSON(String nombre){
        String palabra;
        for(int i=0;i<listaProductosEmpresaJSON.size();i++) {
            try {
                JSONObject json= (JSONObject) listaProductosEmpresaJSON.get(i);
                palabra=(String)json.get("nombre");
                if (palabra.compareTo(nombre)==0)
                    return true;
            } catch (JSONException e) {
                System.out.println("Error en verificar si el producto existe o no");
            }
        }
        return false;
    }

    public static Producto buscarProductoEnEmpresa(String nombre){
        for(Producto p: listaProductosEmpresa){
            if (p.getNombre().compareTo(nombre)==0){
                return p;
            }
        }
        return null;
    }

    //llenar la lista de productos :)

    public static void llenarListaEstaticaProductos(){
        JSONObject json;
        for (int i=0;i<listaProductosEmpresaJSON.size();i++){
            Producto p=new Producto();
            try{
                //obtener datos de la lista json
                json=(JSONObject) listaProductosEmpresaJSON.get(i);
                //agregando al producto
                //agregar los datos al producto
                p.setNombre(json.getString("nombre"));
                p.setDescripcion(json.getString("desc"));
                p.setPrecio(Float.parseFloat(json.getString("precio")));
                p.setCantidad(Integer.parseInt(json.getString("cantidad")));
                p.setPrecioVisible(Boolean.parseBoolean(json.getString("visible")));
                p.setUbicImg(json.getString("img"));
                //
                p.setUserID("USERID_A");//AQUI INSERTAR ID DEL USUARIO
                //agregar a la lista
                listaProductosEmpresa.add(p);
            }
            catch (JSONException e){
                System.out.println("Error al guardar datos del json en lista de clientes");
            }

        }
    }*/

}
