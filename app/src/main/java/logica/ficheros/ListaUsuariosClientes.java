package logica.ficheros;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.util.*;
import logica.usuario.Cliente;
import logica.usuario.Encrypt;


import java.lang.*;

public class ListaUsuariosClientes extends ListaUsuarios {

    private static ArrayList<Cliente> listaUsuariosClientes;
    private static org.json.simple.JSONArray listaUsuariosClientesJSON;

    /**
     * Constructor donde se declaran los atributos
     */
    public ListaUsuariosClientes() {
        ListaUsuariosClientes.listaUsuariosClientes=new ArrayList<>();
        listaUsuariosClientesJSON= new org.json.simple.JSONArray();
    }
    /**
     * Getter
     * @return devuelve el atributo listaUsuarios
     */
    public static ArrayList<Cliente> getListaUsuariosClientes() {
        return listaUsuariosClientes;
    }
    /**
     * Setter, modifica la información dentro del atributo listaUsuarios
     * @param listaUsuariosClientes parametro a modificar
     */
    public static void setListaUsuariosClientes(ArrayList<Cliente> listaUsuariosClientes) {
        ListaUsuariosClientes.listaUsuariosClientes = listaUsuariosClientes;
    }
    /**
     * Getter
     * @return devuelve el atributo listaUsuariosJSON
     */
    public static JSONArray getListaUsuariosClientesJSON() {
        return listaUsuariosClientesJSON;
    }
    /**
     * Setter, modifica la información dentro del atributo listaUsuariosJSON
     * @param listaUsuariosClientesJSON parametro a modificar
     */
    public static void setListaUsuariosClientesJSON(org.json.simple.JSONArray listaUsuariosClientesJSON) {
        ListaUsuariosClientes.listaUsuariosClientesJSON = listaUsuariosClientesJSON;
    }
    /**
     * Metodo encargado de llenar los datos dentro de listaUsuarios
     */

    public static Cliente buscarUsuarioClientes(String email) {
        for (Cliente cliente: listaUsuariosClientes) {
            if (cliente.getEmail().compareTo(email)==0)
                return cliente;}
        return null;}

    public static int buscarIndexUsuarioClientes(String email) {
        for (Cliente cliente: listaUsuariosClientes) {
            if (cliente.getEmail().compareTo(email)==0)
                return listaUsuariosClientes.indexOf(cliente);}
        return 0;}

    public static boolean correoExisteEnClientesJSON(String correo)  {
        String palabra;
        for(int i=0;i<listaUsuariosClientesJSON.size();i++) {
                org.json.simple.JSONObject json= (org.json.simple.JSONObject) listaUsuariosClientesJSON.get(i);
                System.out.println("correo cliente: "+json.get("correo"));
                palabra=(String)json.get("correo");
                if (palabra.compareTo(correo)==0)
                    return true;
        }
        return false;
    }

    public static int buscarIndexUsuarioClientesJSON(String email) throws JSONException {
        for (int i=0; i<=listaUsuariosClientesJSON.size();i++) {
            org.json.simple.JSONObject cliente= (org.json.simple.JSONObject) listaUsuariosClientesJSON.get(i);
            if (cliente.get("correo").toString().equals(email))
                return listaUsuariosClientesJSON.indexOf(cliente);}
        return 0;}

    //TODO: Detalles irrelevantes se cambiaron aquí tambipen
    public static void llenarListaEstaticaClientes() {
        Encrypt desencriptar=new Encrypt();
        LeerDatos leer=new LeerDatos();
        leer.leerListaClientes();
        org.json.simple.JSONObject json;
        if (listaUsuariosClientesJSON.isEmpty()) return;
        for(int i=0;i<listaUsuariosClientesJSON.size();i++){
            Cliente cliente=new Cliente();
            try {
                json = (org.json.simple.JSONObject) listaUsuariosClientesJSON.get(i);
                if (json!=null) {
                    cliente.setAddress((String) json.get("correo"));
                    cliente.setPassword(desencriptar.getAESDecrypt((String) json.get("contraseña")));
                    cliente.setTipoCuenta((String) json.get("tipo"));
                    System.out.println(json.get("edad"));
                    cliente.setEdad((long) json.get("edad"));
                    listaUsuariosClientes.add(cliente);}
                } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
     }



}