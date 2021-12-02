package logica.ficheros;


import java.io.*;
import logica.usuario.*;
import org.json.*;

public class GuardarDatos {

    public static void procesoGuardadoClientes() {
        Cliente cliente;
        GuardarDatos guardar = new GuardarDatos();
        for (Cliente usuarioCliente : ListaUsuariosClientes.getListaUsuariosClientes()) {
            if (!ListaUsuariosClientes.correoExisteEnClientesJSON(usuarioCliente.getEmail())) {
                cliente = ListaUsuariosClientes.buscarUsuarioClientes(usuarioCliente.getEmail());
                if (cliente != null) {
                    cliente.llenarObjetoClienteJson(cliente);
                    ListaUsuariosClientes.getListaUsuariosClientesJSON().add(cliente.getUsuarioJSON());
                    //ListaUsuariosClientes.agregarUsuarioAListaJSON(cliente.getUsuarioJSON(), ListaUsuariosClientes.getListaUsuariosClientesJSON());
                }
            }
        }
        //guardar.agregarAJsonClientes(ListaUsuariosClientes.getListaUsuariosClientesJSON());
    }

    public static void procesoGuardadoEmpresas() {
        Empresa empresa;
        GuardarDatos guardar = new GuardarDatos();
        for (Empresa usuarioEmpresa : ListaUsuariosEmpresas.getListaUsuariosEmpresas()) {
            if (!ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(usuarioEmpresa.getEmail())) {
                empresa = ListaUsuariosEmpresas.buscarUsuarioEmpresa(usuarioEmpresa.getEmail());
                if (empresa != null) {
                    empresa.llenarObjetoEmpresaJson(empresa);
                    ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON().add(empresa.getUsuarioJSON());
                    //ListaUsuariosEmpresas.agregarUsuarioAListaJSON(empresa.getUsuarioJSON(), ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON());

                }
            }
        }
        //guardar.agregarAJsonEmpresas(ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON());
    }

    public void agregarAJsonEmpresas(JSONArray ListaJson) {
        try (FileWriter archivo = new FileWriter("/app/files/usuariosEmpresas.json")) {
            BufferedWriter buffer = new BufferedWriter(archivo);
            buffer.write(ListaJson.toString());
            buffer.close();
        } catch (IOException e) {
            System.out.println("Problemas al ingresar un dato al archivo empresas. Clase Guardar Datos");
        }
    }

    public void agregarAJsonClientes(JSONArray listaJson) {
        try (FileWriter archivo = new FileWriter("/app/files/usuariosClientes.json")) {
            BufferedWriter buffer = new BufferedWriter(archivo);
            buffer.write(listaJson.toString());
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
