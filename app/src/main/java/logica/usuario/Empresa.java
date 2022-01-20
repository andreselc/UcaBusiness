package logica.usuario;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import logica.producto.Producto;

public class Empresa extends Usuario{

    //por ahora, no se llena esta
    private ArrayList<Producto> listaPublicaciones;
    private String direccion;

    public Empresa(){usuarioJSON=new JSONObject();
    }
    //TODO: Aquí se cambió el orden de los atributos en función de lo que aparece en el constructor Usuario
    public Empresa(String password,String email, String tipoCuenta, String direccion) {
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
          String passwordAux;
          passwordAux=encriptar.getAES(getPassword());
          if (usuarioJSON!=null){
          //TODO:Falta agregar aquí el ciclo que llena todas las publicaciones de una empresa en el ArrayList
              //TODO: Aquí se usa passwordAux para guardar la contraseña encriptada solo en los archivos
              //TODO: ANTES se encriptaba la contraseña con un set y se cambiaba en la lista estática
              //TODO: Si iniciabas con una cuenta ya registrada, funcionaba, pero si registrabas e inmediatamente iniciabas, no funcionaba
              //TODO:PROBLEMA RESUELTO
          usuarioJSON.put("contraseña",passwordAux);
          usuarioJSON.put("correo",getEmail());
          usuarioJSON.put("tipo",getTipoCuenta());
          usuarioJSON.put("direccion",getDireccion());}
          else
              System.out.println("nulleado una empresa");
      }


}
