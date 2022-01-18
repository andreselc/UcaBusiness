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

    public void llenarObjetoClienteJson() {
        Encrypt encriptar=new Encrypt();
        setPassword(encriptar.getAES(getPassword()));
        try {
            System.out.println(getPassword());
            if (usuarioJSON==null){
                System.out.println("nulleado");
                return;
            }
            usuarioJSON.put("contraseña",getPassword());
            usuarioJSON.put("correo",getEmailNoAt());
            usuarioJSON.put("tipo",getTipoCuenta());
        } catch (JSONException e) {
            System.out.println("Error al insertar datos en JSON de Clientes. Clase Cliente");
        }
    }


}
