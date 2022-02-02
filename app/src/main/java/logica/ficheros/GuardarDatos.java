package logica.ficheros;


import java.io.*;

import android.app.Activity;
import logica.usuario.*;
import org.json.JSONException;
import org.json.simple.*;

public class GuardarDatos {

    //TODO: Se quitó el Try catch innecesario y el método para agregar los clientes al JSON está en lo último de este método
    public static void procesoGuardadoClientes(Activity activity) {
        System.out.println("entra a guardado");
        Cliente cliente;
        GuardarDatosProducto a = new GuardarDatosProducto();
        GuardarDatos guardar = new GuardarDatos();

        ///busca que el cliente no exista en el json
        for (Cliente usuarioCliente : ListaUsuariosClientes.getListaUsuariosClientes()) {
            if (!ListaUsuariosClientes.correoExisteEnClientesJSON(usuarioCliente.getEmail())) {
                cliente = ListaUsuariosClientes.buscarUsuarioClientes(usuarioCliente.getEmail());
                if (cliente != null) {//si existe el objeto*
                    System.out.println(cliente.getEmail());
                    cliente.llenarObjetoClienteJson(); //crea objeto json
                    ListaUsuariosClientes.getListaUsuariosClientesJSON().add(cliente.getUsuarioJSON());//agrega a la lista json
                    /*try {
                        a.escribirArchivo(ListaUsuariosClientes.getListaUsuariosClientesJSON(),activity,"usuariosClientes.json");
                        //intenta escribir al json
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    //ListaUsuariosClientes.agregarUsuarioAListaJSON(cliente.getUsuarioJSON(), ListaUsuariosClientes.getListaUsuariosClientesJSON());
                }
                else
                    System.out.println ("Un objeto cliente es nulo");
            }
        }
        //vaciar el archivo
        a.borrarArchivo("clientes.json");
        guardar.agregarAJsonClientes(ListaUsuariosClientes.getListaUsuariosClientesJSON());
    }

    //TODO: quité el Try Catch que no se usa por ahora, y coloqué la condición para verificar si empresa no es nulo
    public static void procesoGuardadoEmpresas(Activity activity) {
        Empresa empresa;
        GuardarDatosProducto a = new GuardarDatosProducto();
        GuardarDatos guardar = new GuardarDatos();
        for (Empresa usuarioEmpresa : ListaUsuariosEmpresas.getListaUsuariosEmpresas()) {
            if (!ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(usuarioEmpresa.getEmail())) {
                empresa = ListaUsuariosEmpresas.buscarUsuarioEmpresa(usuarioEmpresa.getEmail());
                if (empresa != null) {
                    System.out.println(empresa.getEmail());
                    empresa.llenarObjetoEmpresaJson();
                    ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON().add(empresa.getUsuarioJSON());
                    /*try {
                        a.escribirArchivo(ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON(),activity,"usuariosEmpresas.json");
                    } catch (IOException e) {
                        e.printStackTrace();}*/
                    //ListaUsuariosEmpresas.agregarUsuarioAListaJSON(empresa.getUsuarioJSON(), ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON());

                }
            }
        }
        a.borrarArchivo("empresas.json");
         guardar.agregarAJsonEmpresas(ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON());
    }

    public static void procesoModEmpresas(Activity activity, Empresa emp) {

        GuardarDatosProducto a = new GuardarDatosProducto();
        GuardarDatos guardar = new GuardarDatos();

        if (emp != null) {
                    System.out.println("modifica la empresa");
            try {
                ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON().remove(ListaUsuariosEmpresas.buscarIndexUsuarioEmpresasJSON(emp.getEmail()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(emp.getEmail())){
                        System.out.println("el usuario se borró de la lista");
                    emp.setUsuarioJSON(new JSONObject());
                    emp.llenarObjetoEmpresaJson();
                    ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON().add(emp.getUsuarioJSON());}

            }

        a.borrarArchivo("empresas.json");
        guardar.agregarAJsonEmpresas(ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON());
    }

    public static void procesoModClientes(Activity activity, Cliente cl) throws JSONException {
        GuardarDatosProducto a = new GuardarDatosProducto();
        GuardarDatos guardar = new GuardarDatos();

                if (cl != null) {
                    System.out.println("modifica la empresa");
                    ListaUsuariosClientes.getListaUsuariosClientesJSON().remove(ListaUsuariosClientes.buscarIndexUsuarioClientesJSON(cl.getEmail()));
                    if (!ListaUsuariosClientes.correoExisteEnClientesJSON(cl.getEmail())){
                        System.out.println("el usuario se borró de la lista");
                        cl.setUsuarioJSON(new JSONObject());
                        cl.llenarObjetoClienteJson();
                        ListaUsuariosClientes.getListaUsuariosClientesJSON().add(cl.getUsuarioJSON());}


        }
        a.borrarArchivo("clientes.json");
        guardar.agregarAJsonClientes(ListaUsuariosClientes.getListaUsuariosClientesJSON());
    }

    //TODO: se modificaron los nombres de los archivos para que se guardara la info en la memoria del tlf
    public void agregarAJsonEmpresas(JSONArray ListaJson) {
        try (FileWriter archivo = new FileWriter("data/data/edu.example.pruebadosproyectoandres/files/empresas.json")) {
            BufferedWriter buffer = new BufferedWriter(archivo);
            buffer.write(ListaJson.toString());
            buffer.close();
        } catch (IOException e) {
            System.out.println("Problemas al ingresar datos al archivo empresas. Clase Guardar Datos");
        }
    }

    public void agregarAJsonClientes(JSONArray listaJson) {
        try (FileWriter archivo = new FileWriter("data/data/edu.example.pruebadosproyectoandres/files/clientes.json")) {
            BufferedWriter buffer = new BufferedWriter(archivo);
            buffer.write(listaJson.toString());
            buffer.close();
            System.out.println("Escribe en JSON");
        } catch (IOException e) {
            System.out.println("Problemas al ingresar datos  al archivo clientes. Clase Guardar Datos");
        }


    }
    public static void eliminarEmpresa(String email){
        Empresa temp = ListaUsuariosEmpresas.buscarUsuarioEmpresa(email);
        if (temp!=null){
            //se elimina
            ListaUsuariosEmpresas.getListaUsuariosEmpresas().remove(ListaUsuariosEmpresas.buscarIndexUsuarioEmpresa(email));
        }
    }

    public static void eliminarCliente(String email){
        Cliente temp = ListaUsuariosClientes.buscarUsuarioClientes(email);
        if (temp!=null){
            //se elimina
            ListaUsuariosClientes.getListaUsuariosClientes().remove(ListaUsuariosClientes.buscarIndexUsuarioClientes(email));
        }
    }
}
