package logica.ficheros;

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
    public static org.json.simple.JSONArray getListaUsuariosClientesJSON() {
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

    public static boolean correoExisteEnClientesJSON(String correo)  {
        String palabra;
        for(int i=0;i<listaUsuariosClientesJSON.size();i++) {
            try {
                org.json.simple.JSONObject json= (org.json.simple.JSONObject) listaUsuariosClientesJSON.get(i);
                palabra=(String)json.get("correo");
                if (palabra.compareTo(correo)==0)
                    return true;
            } catch (Exception e) {
                System.out.println("Error en verificar si el correo existe o no");
            }
        }
        return false;
    }


    public static void llenarListaEstaticaClientes() {
        Encrypt desencriptar=new Encrypt();
        LeerDatos leer=new LeerDatos();
        leer.leerListaClientes();
        org.json.simple.JSONObject json;
        for(int i=0;i<listaUsuariosClientesJSON.size();i++){
            Cliente cliente=new Cliente();
            try {
                json = (org.json.simple.JSONObject) listaUsuariosClientesJSON.get(i);
                cliente.setAddressFromJSON((String) json.get("correo"));
                cliente.setPassword(desencriptar.getAESDecrypt((String) json.get("contraseña")));
                cliente.setTipoCuenta((char) Integer.parseInt(json.get("tipo").toString()));
                listaUsuariosClientes.add(cliente);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
     }

}