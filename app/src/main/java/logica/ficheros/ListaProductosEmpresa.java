package logica.ficheros;

import logica.producto.Producto;
import org.json.JSONException;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class ListaProductosEmpresa extends ListaProductos{

    private static ArrayList<Producto> listaProductosEmpresa;
    private static org.json.simple.JSONArray listaProductosEmpresaJSON;

    public ListaProductosEmpresa() {
        ListaProductosEmpresa.listaProductosEmpresa=new ArrayList<>();
        listaProductosEmpresaJSON=new org.json.simple.JSONArray();
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
/**producto existe en json*/
    public static boolean productoExisteJSON(String correo)  {
        String palabra;
        for(int i=0;i<listaProductosEmpresaJSON.size();i++) {

            org.json.simple.JSONObject json= (org.json.simple.JSONObject) listaProductosEmpresaJSON.get(i);
            System.out.println("producto: "+json.get("nombre"));
            palabra=(String)json.get("nombre");
            if (palabra.compareTo(correo)==0)
                return true;

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
        org.json.simple.JSONObject json;
        for (int i=0;i<listaProductosEmpresaJSON.size();i++){
            Producto p=new Producto();
            //obtener datos de la lista json
            json=(org.json.simple.JSONObject) listaProductosEmpresaJSON.get(i);
            //agregando al producto
            //agregar los datos al producto
            p.setNombre((String)json.get("nombre"));
            p.setDescripcion((String)json.get("desc"));
            p.setPrecio(Float.parseFloat((String)json.get("precio")));
            p.setCantidad(Integer.parseInt((String)json.get("cantidad")));
            p.setPrecioVisible(Boolean.parseBoolean((String)json.get("visible")));
            p.setUbicImg((String)json.get("img"));
            //
            p.setUserID((String) json.get("userID"));//AQUI INSERTAR ID DEL USUARIO
            //agregar a la lista
            listaProductosEmpresa.add(p);

        }
    }

}
