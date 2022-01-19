package logica.ficheros;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * Clase encargada de leer los datos dentro del archivo JSON
 */
public class LeerDatos {

    org.json.simple.JSONArray temp=new org.json.simple.JSONArray();
    /**
     * Metodo encargado de realizar la correspondiente lectuta de la información
     * almacenada dentro del archivo JSON
     */
    public void leerListaClientes() {
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try {FileReader reader = new FileReader("data/data/edu.example.pruebadosproyectoandres/files/clientes.json");
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaUsuariosClientes.setListaUsuariosClientesJSON(jsonLista);
        }catch (FileNotFoundException e) {
            System.out.println("archivo no encontrado de clientes");
        }
        catch (IOException e) {
        }
        catch (ParseException e) {
        }
    }
       //TODO: Se modificaron los nombres de los archivos para que se leyeran desde el tlf
       public void leerListaEmpresas() {
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try {FileReader reader = new FileReader("data/data/edu.example.pruebadosproyectoandres/files/empresas.json");
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaUsuariosEmpresas.setListaUsuariosEmpresasJSON(jsonLista);
        }catch (FileNotFoundException e) {
            System.out.println("archivo no encontrado de empresas");
        }
        catch (IOException e) {System.out.println("io");
        }
        catch (ParseException e) { System.out.println("parse");
        }
    }

    //TODO: No se está usando por ahora
    public void leerListaProductos() {
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try (FileReader reader = new FileReader("/data/data/edu.example.pruebadosproyectoandres/files/listaEmpresaGral.json")) {
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            //hasta aqui bien
            ListaProductos.setListaProductosJSON(jsonLista);
        }catch (FileNotFoundException e) {
            System.out.println("archivo no encontrado de productos");
        }
        catch (IOException e) {
            System.out.println("io no encontrado");
        }
        catch (ParseException e) {
            System.out.println("parse no encontrado");
        }

    }
}
