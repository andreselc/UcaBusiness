package logica.ficheros;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
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
        try (FileReader reader = new FileReader("/data/user/0/edu.example.pruebadosproyectoandres/files/files/usuariosClientes.json")) {
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaUsuariosClientes.setListaUsuariosClientesJSON(jsonLista);
        }catch (FileNotFoundException e) {
        }
        catch (IOException e) {
        }
        catch (ParseException e) {
        }
    }

       public void leerListaEmpresas() {
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try (FileReader reader = new FileReader("/data/user/0/edu.example.pruebadosproyectoandres/files/files/usuariosEmpresas.json")) {
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaUsuariosEmpresas.setListaUsuariosEmpresasJSON(jsonLista);
        }catch (FileNotFoundException e) {
        }
        catch (IOException e) {
        }
        catch (ParseException e) {
        }

    }
    public void leerListaProductos() {
        org.json.simple.JSONArray jsonLista;
        JSONParser lectura = new JSONParser();
        //La ruta del archivo no debe ser específico.
        try (FileReader reader = new FileReader("/data/data/edu.example.pruebadosproyectoandres/files/listaEmpresaGral.json")) {
            Object objeto = lectura.parse(reader);
            jsonLista = (org.json.simple.JSONArray) objeto;
            ListaProductos.setListaProductosJSON(jsonLista);
        }catch (FileNotFoundException e) {
            System.out.println("archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("io no encontrado");
        }
        catch (ParseException e) {
            System.out.println("parse no encontrado");
        }

    }
}
