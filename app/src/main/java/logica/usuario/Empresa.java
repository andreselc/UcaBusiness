package logica.usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import logica.producto.Producto;

public class Empresa extends Usuario{

    //por ahora, no se llena esta
    private ArrayList<Producto> listaPublicaciones;
    private String direccion;

    public Empresa(){usuarioJSON=new JSONObject();
    }
    //TODO: Aquí se cambió el orden de los atributos en función de lo que aparece en el constructor Usuario
    public Empresa(String password,String email, char tipoCuenta, String direccion) {
        super(password, email, tipoCuenta);
        listaPublicaciones = new ArrayList<Producto>();
        usuarioJSON=new JSONObject();
        this.direccion=direccion;
    }

    public ArrayList<Producto> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(ArrayList<Producto> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


      public void llenarObjetoEmpresaJson() {
        Encrypt encriptar=new Encrypt();
        setPassword(encriptar.getAES(getPassword()));
          try {
              if (usuarioJSON!=null){
              //TODO:Falta agregar aquí el ciclo que llena todas las publicaciones de una empresa en el ArrayList
              usuarioJSON.put("contraseña",getPassword());
              usuarioJSON.put("correo",getEmail());
              usuarioJSON.put("tipo",getTipoCuenta());
              usuarioJSON.put("direccion",getDireccion());}
              else
                  System.out.println("nulleado una empresa");
          } catch (JSONException e) {
              System.out.println("Error al insertar datos en JSON de Empresas, Clase Empresa.");
          }
    }


}
