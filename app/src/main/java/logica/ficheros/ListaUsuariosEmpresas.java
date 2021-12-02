package logica.ficheros;

import org.json.JSONException;
import org.json.JSONObject;
import logica.usuario.Empresa;
import java.util.ArrayList;
import logica.usuario.*;

public class ListaUsuariosEmpresas extends ListaUsuarios{

    private static ArrayList<Empresa> listaUsuariosEmpresas;
    private static org.json.simple.JSONArray listaUsuariosEmpresasJSON;

    public ListaUsuariosEmpresas() {
        ListaUsuariosEmpresas.listaUsuariosEmpresas = new ArrayList<Empresa>();
        listaUsuariosEmpresasJSON = new org.json.simple.JSONArray();
    }
    /**
     * Getter
     * @return devuelve el atributo listaUsuarios
     */
    public static ArrayList<Empresa> getListaUsuariosEmpresas() {
        return listaUsuariosEmpresas;
    }
    /**
     * Setter, modifica la información dentro del atributo listaUsuarios
     * @param listaUsuariosEmpresas parametro a modificar
     */
    public static void setListaUsuarioEmpresas(ArrayList<Empresa> listaUsuariosEmpresas) {
        ListaUsuariosEmpresas.listaUsuariosEmpresas = listaUsuariosEmpresas;
    }

    public static org.json.simple.JSONArray getListaUsuariosEmpresasJSON() {
        return listaUsuariosEmpresasJSON;
    }
    /**
     * Setter, modifica la información dentro del atributo listaUsuariosJSON
     * @param listaUsuariosEmpresasJSON parametro a modificar
     */
    public static void setListaUsuariosEmpresasJSON(org.json.simple.JSONArray listaUsuariosEmpresasJSON) {
        ListaUsuariosEmpresas.listaUsuariosEmpresasJSON = listaUsuariosEmpresasJSON;
    }
    /**
     * Metodo encargado de llenar los datos dentro de listaUsuarios
     */

    public static Empresa buscarUsuarioEmpresa(String email) {
        for (Empresa empresa: listaUsuariosEmpresas) {
            if (empresa.getEmail().compareTo(email)==0)
                return empresa;}
        return null;}

    public static boolean correoExisteEnEmpresasJSON(String correo)  {
        String palabra;
        for(int i=0;i<listaUsuariosEmpresasJSON.size();i++) {
            try {
                org.json.simple.JSONObject json= (org.json.simple.JSONObject) listaUsuariosEmpresasJSON.get(i);
                palabra=(String)json.get("correo");
                if (palabra.compareTo(correo)==0)
                    return true;
            } catch (Exception e) {
                System.out.println("Error en verificar si el correo existe o no");
            }
        }
        return false;
    }

    //TODO:Falta agregar el ciclo que me llene las publicaciones en la lista.
    public static void llenarListaEstaticaEmpresas() {
        Encrypt desencriptar = new Encrypt();
        LeerDatos leer = new LeerDatos();
        leer.leerListaEmpresas();
        org.json.simple.JSONObject json;
        for (int i = 0; i < listaUsuariosEmpresasJSON.size(); i++) {
            Empresa empresa = new Empresa();
            try {
                json = (org.json.simple.JSONObject) listaUsuariosEmpresasJSON.get(i);
                empresa.setAddressFromJSON((String) json.get("correo"));
                empresa.setPassword(desencriptar.getAESDecrypt((String) json.get("contraseña")));
                empresa.setTipoCuenta((char) json.get("tipo"));
                empresa.setDireccion((String) json.get("direccion"));
                listaUsuariosEmpresas.add(empresa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
