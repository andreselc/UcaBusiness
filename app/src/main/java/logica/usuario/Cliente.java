package logica.usuario;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class Cliente extends Usuario{

    public Cliente(){ }

    public Cliente (String password, String email,String tipoDeCuenta) {
        super(password, email,tipoDeCuenta);
        this.usuarioJSON=new JSONObject();
        usuarioJSON =new JSONObject();
    }

    //TODO: Cambié en el get de EMAIL para que colocara bien los correos
    public void llenarObjetoClienteJson() {
        Encrypt encriptar=new Encrypt();
        setPassword(encriptar.getAES(getPassword()));
        if (usuarioJSON==null){
            System.out.println("nulleado un cliente");
        }
        else{
        usuarioJSON.put("contraseña",getPassword());
        usuarioJSON.put("correo",getEmail());
        usuarioJSON.put("tipo",getTipoCuenta());}
    }


}
