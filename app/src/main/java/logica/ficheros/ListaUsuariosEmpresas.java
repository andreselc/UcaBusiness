package logica.ficheros;


import logica.usuario.Empresa;
import java.util.ArrayList;
import logica.usuario.*;
import org.json.simple.JSONArray;

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

    public static JSONArray getListaUsuariosEmpresasJSON() {
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

    public static int buscarIndexUsuarioEmpresa(String email) {
        for (Empresa empresa: listaUsuariosEmpresas) {
            if (empresa.getEmail().compareTo(email)==0)
                return listaUsuariosEmpresas.indexOf(empresa);}
        return 0;}


    public static boolean correoExisteEnEmpresasJSON(String correo)  {
        String palabra;
        System.out.println("listaJSON:"+listaUsuariosEmpresasJSON.toString());
        for(int i=0;i<listaUsuariosEmpresasJSON.size();i++) {
            org.json.simple.JSONObject json= (org.json.simple.JSONObject) listaUsuariosEmpresasJSON.get(i);
            System.out.println("correo empresa: "+json.get("correo"));
                palabra=(String)json.get("correo");
                if(palabra != null) {
                    if (palabra.compareTo(correo) == 0)
                        return true;
                }
        }
        return false;
    }

    //TODO:Falta agregar el ciclo que me llene las publicaciones en la lista.
    //TODO: Detalles irrelevantes se cambiaron aquí
    public static void llenarListaEstaticaEmpresas() {
        Encrypt desencriptar = new Encrypt();
        LeerDatos leer = new LeerDatos();
        leer.leerListaEmpresas();
        org.json.simple.JSONObject json;
        if (listaUsuariosEmpresasJSON.isEmpty()) return;
        for (int i = 0; i < listaUsuariosEmpresasJSON.size(); i++) {
            Empresa empresa = new Empresa();
            try {
                json = (org.json.simple.JSONObject) listaUsuariosEmpresasJSON.get(i);
                empresa.setAddress((String) json.get("correo"));
                empresa.setPassword(desencriptar.getAESDecrypt((String) json.get("contraseña")));
                empresa.setTipoCuenta((String) json.get("tipo"));
                empresa.setDireccion((String) json.get("direccion"));
                empresa.setDatosContacto((String) json.get("datosContacto"));
                empresa.setLinkWhattsApp((String) json.get("linkWhattsApp"));
                empresa.setPrivacidad((String) json.get("privacidad"));
                System.out.println(empresa.getTipoCuenta());
                System.out.println(empresa.getDireccion());
                listaUsuariosEmpresas.add(empresa);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
    }
}
