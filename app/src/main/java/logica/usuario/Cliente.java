package logica.usuario;

import org.json.JSONException;
import org.json.JSONObject;


public class Cliente extends Usuario{

    public Cliente(){ }

    public Cliente (String password, String email,char tipoDeCuenta) {
        super(password, email,tipoDeCuenta);
        this.usuarioJSON=new JSONObject();
        usuarioJSON =new JSONObject();
    }

    public void llenarObjetoClienteJson(Cliente cliente) {
        Encrypt encriptar=new Encrypt();
        cliente.setPassword(encriptar.getAES(cliente.getPassword()));
        try {
            cliente.usuarioJSON.put("contraseña",cliente.getPassword());
            cliente.usuarioJSON.put("correo",cliente.getEmail());
            cliente.usuarioJSON.put("tipo",cliente.getTipoCuenta());
        } catch (JSONException e) {
            System.out.println("Error al insertar datos en JSON de Clientes. Clase Cliente");
        }
    }
}
