package logica.usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import logica.producto.Producto;

public class Empresa extends Usuario{

    private ArrayList<Producto> listaPublicaciones;

    public Empresa (){
    }

    public Empresa(String password, String email, char tipoCuenta) {
        super(password, email, tipoCuenta);
         listaPublicaciones = new ArrayList<Producto>();
         usuarioJSON=new JSONObject();
    }

    public Empresa(String password, String email, ArrayList<Producto> listaPublicaciones) {
        super(password, email);
        this.listaPublicaciones = listaPublicaciones;
    }
      public void llenarObjetoEmpresaJson(Empresa empresa) {
        Encrypt encriptar=new Encrypt();
        empresa.setPassword(encriptar.getAES(empresa.getPassword()));
          try {
              empresa.usuarioJSON.put("contraseña",empresa.getPassword());
              empresa.usuarioJSON.put("correo",empresa.getEmail());
              empresa.usuarioJSON.put("tipo",empresa.getTipoCuenta());
              } catch (JSONException e) {
              System.out.println("Error al insertar datos en JSON de Empresas, Clase Empresa.");
          }
    }

}
