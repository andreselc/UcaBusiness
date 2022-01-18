package logica.ficheros;

import android.app.Activity;
import edu.example.pruebadosproyectoandres.MainActivityEmpresa;
import logica.producto.Producto;
import logica.usuario.Empresa;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class GuardarDatosProducto {

   /* public static void guardarProducto(Activity activityActual, Empresa e){
        Producto producto;
        GuardarDatosProducto guardar = new GuardarDatosProducto();
        //guardar en listaempresa
        for (Producto p: ListaProductosEmpresa.getListaProductosEmpresa()){
            if (!ListaProductosEmpresa.productoExisteJSON(p.getNombre())){
                //si no esta repetido el producto
                producto=(Producto) ListaProductosEmpresa.buscarProductoEnEmpresa(p.getNombre());
                producto.llenarObjetoProductoJSON(producto);
                ListaProductosEmpresa.agregarProductoAListaJSON(producto.getProductoJSON(), ListaProductosEmpresa.getListaProductosEmpresaJSON());
            }
        }
        try {
            guardar.escribirArchivo(ListaProductosEmpresa.getListaProductosEmpresaJSON(), activityActual,"listaEmpresa"+e.getEmailWithoutAt()+".json");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/

    //para empresa gral
    public static void guardarProducto(Activity activityActual){
        Producto producto;
        GuardarDatosProducto guardar = new GuardarDatosProducto();
        //guardar en listaempresa
        for (Producto p: ListaProductos.getListaProductos()){
            if (!ListaProductos.productoExisteJSONGral(p.getNombre())){
                //si no esta repetido el producto
                producto=(Producto) ListaProductosEmpresa.buscarProductoGral(p.getNombre());
                producto.llenarObjetoProductoJSON(producto);
                ListaProductos.agregarProductoAListaJSON(producto.getProductoJSON(), ListaProductos.getListaProductosJSON());
            }
        }
        try {
            guardar.escribirArchivo(ListaProductosEmpresa.getListaProductosJSON(), activityActual,"listaEmpresaGral.json");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //debe agregarse al json de lista de productos de empresa y al json de lista de productos en general
    public void agregarAJSONProductos(JSONArray ListaJson) {
        try(FileWriter archivo= new FileWriter (" C:\\Users\\Andres\\AndroidStudioProjects\\pruebaDosProyectoAndres\\app\\files\\usuariosEmpresas.json")){
            BufferedWriter buffer= new BufferedWriter (archivo);
            buffer.write(ListaJson.toString());
            buffer.close();
        }

        catch (IOException e){
            System.out.println("Problemas al ingresar un dato al archivo");
        }
    }



    public void escribirArchivo(org.json.simple.JSONArray listaProducto, Activity a,String nombreArchivo) throws IOException {
        //nombre archivo debe incluir .json

        try{File file = new File("/data/data/edu.example.pruebadosproyectoandres/files/files/"+nombreArchivo);

        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println("direccion:"+file.getPath());

        FileWriter fileW=new FileWriter(file);
        fileW.append(listaProducto.toString());
        fileW.flush();
        fileW.close();}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void borrarArchivo(String nombreArchivo){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("/data/data/edu.example.pruebadosproyectoandres/files/files/"+nombreArchivo);
            pw.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();
    }

    //leer listas de productos del json

   /* public void leerProductos(Empresa e){//para una empresa especifica
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try (FileReader reader = new FileReader("files/listaEmpresa"+e.getEmailNoAt()+".json")) {
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaProductosEmpresa.setListaProductosEmpresaJSON(jsonLista);
        }catch (FileNotFoundException er) {
        }
        catch (IOException er) {
        }
        catch (ParseException er) {
        }

    }
    public void leerProductos(){//para todas empresas
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try (FileReader reader = new FileReader("files/listaEmpresaGral.json")) {
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaProductosEmpresa.setListaProductosEmpresaJSON(jsonLista);
        }catch (FileNotFoundException er) {
        }
        catch (IOException er) {
        }
        catch (ParseException er) {
        }

    }


*/
    }



