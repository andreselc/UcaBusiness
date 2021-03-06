package logica.usuario;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class Cliente extends Usuario{
    private long edad;

    public Cliente(){ }

    public long getEdad() {
        return edad;
    }

    public void setEdad(long edad) {
        this.edad = edad;
    }

    public Cliente (String password, String email, String tipoDeCuenta, int edad) {
        super(password, email,tipoDeCuenta);
        this.edad=edad;
        this.usuarioJSON=new JSONObject();
        usuarioJSON =new JSONObject();
    }

    //TODO: Cambié en el get de EMAIL para que colocara bien los correos
    public void llenarObjetoClienteJson() {
        //TODO: Aquí se usa passwordAux para guardar la contraseña encriptada solo en los archivos
        //TODO: ANTES se encriptaba la contraseña con un set y se cambiaba en la lista estática
        //TODO: Si iniciabas con una cuenta ya registrada, funcionaba, pero si registrabas e inmediatamente iniciabas, no funcionaba
        //TODO:PROBLEMA RESUELTO
        Encrypt encriptar=new Encrypt();
        String passwordAux;
        passwordAux=encriptar.getAES(getPassword());
        if (usuarioJSON==null){
            System.out.println("nulleado un cliente");
        }
        else{
        usuarioJSON.put("contraseña",passwordAux);
        usuarioJSON.put("correo",getEmail());
        usuarioJSON.put("tipo",getTipoCuenta());
        usuarioJSON.put("edad",edad);}
    }


}
