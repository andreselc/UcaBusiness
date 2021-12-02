package logica.ficheros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import logica.usuario.Usuario;

public class ListaUsuarios {

    private static ArrayList<Usuario> listaUsuarios;
    private static JSONArray listaUsuariosJSON;

    /**
     * Constructor donde se declaran los atributos
     */
    public ListaUsuarios() {
        ListaUsuarios.listaUsuarios=new ArrayList<Usuario>();
        listaUsuariosJSON= new JSONArray();
    }

    //TODO:Con este método se puede validar si un usuario se encuentra en una lista. Esto es para cuando te registras
    public static boolean correoExiste(String correo) {
        for (Usuario usuario: listaUsuarios) {
            if (usuario.getEmail().compareTo(correo)==0)
                return true;}
        return false;}

    public static boolean correoExisteEnJSON(String correo)  {
        String palabra;
        for(int i=0;i<listaUsuariosJSON.length();i++) {
            try {
                JSONObject json= (JSONObject) listaUsuariosJSON.get(i);
                palabra=(String)json.get("correo");
                if (palabra.compareTo(correo)==0)
                    return true;
            } catch (JSONException e) {
                System.out.println("Error en verificar si el correo existe o no");
            }
        }
        return false;
    }

    public static void agregarUsuarioAListaJSON(JSONObject usuario, JSONArray lista) {
        lista.put(usuario);
    }
}
