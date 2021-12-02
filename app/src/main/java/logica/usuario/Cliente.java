package logica.usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class Cliente extends Usuario{

    public Cliente(){ usuarioJSON=new JSONObject();}

    public Cliente (String password, String email,char tipoDeCuenta) {
        super(password, email,tipoDeCuenta);
        usuarioJSON=new JSONObject();
    }

    public void llenarObjetoClienteJson(Cliente cliente) {
        Encrypt encriptar=new Encrypt();
        cliente.setPassword(encriptar.getAES(cliente.getPassword()));
        System.out.println(cliente.getEmailNoAt());
        System.out.println(cliente.getPassword());
        System.out.println(cliente.getTipoCuenta());
        try {
            cliente.usuarioJSON.put("contraseña",cliente.getPassword());
            cliente.usuarioJSON.put("correo",cliente.getEmailNoAt());
            cliente.usuarioJSON.put("tipo",cliente.getTipoCuenta());
        } catch (JSONException e) {
            System.out.println("Error al insertar datos en JSON de Clientes. Clase Cliente");
        }
    }


}
