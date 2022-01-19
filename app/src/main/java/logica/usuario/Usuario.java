package logica.usuario;
import org.json.simple.JSONObject;
/**
 * Clase encargada de manejar la información por usuario
 * @author Grupo4
 *
 */
public class Usuario{
    /**
     * Atributos para el correspondiente funcionamiento de la clase
     */
    private Password password;
    private Correo email;
    private String tipoCuenta;
    public JSONObject usuarioJSON;
    public Boolean bloqueo=false;
    public String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String nombreUsuario) {
        this.UserName = nombreUsuario;
    }

    public Boolean getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(Boolean bloqueo) {
        this.bloqueo = bloqueo;
    }


    /**
     * Constructor donde se inicializan los atributos por defecto
     */
    public Usuario() {
        password = new Password();
        email = new Correo();
    }

    public Usuario(String password,String email,String tipoCuenta){
        this.password= new Password(password);
        this.email=new Correo(email);
        this.tipoCuenta=tipoCuenta;
    }

    /**
     * Constructor donde se inicializan los atributos de password y email, el resto por defecto
     * @param password valor a pasarle al atributo password
     * @param email valor a pasarle al atributo email
     */

    public Usuario(String password, String email) {
        this.password.setPassword(password);
        this.email.setAddress(email);
    }

    /**
     * getter
     * @return devuelve el atributo email
     */
    public String getEmail() {
        return email.getAddress();
    }

    public String getEmailNoAt(){return email.getAddressNoAt();}

    /**
     * getter
     * @return devuelve el atributo password
     */
    public String getPassword() {
        return password.getPassword();
    }

    /**
     * getter
     * @return devuelve el atriburo tipoCuenta
     */
    public String getTipoCuenta() { return tipoCuenta; }

    /**
     * setter modifica la información del parametro tipoCuenta
     * @param tipoCuenta valor a pasarle al atributo tipoCuenta
     */
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    /**
     * setter, modifica la información del parametro address
     * @param address valor a pasarle al atributo address
     */

    public void setAddress(String address) {
        email.setAddress(address);
    }

    /**
     * Setter, modifica la información del parametro password
     * @param password contraseña
     */
    public void setPassword(String password) {
        this.password.setPassword(password);
    }

    public JSONObject getUsuarioJSON() {
        return usuarioJSON;
    }

    public void setUsuarioJSON(JSONObject usuarioJSON) {
        this.usuarioJSON = usuarioJSON;
    }


    /**
     * Verifica si el correo existe
     * @param correo correo a validar
     * @return true o false segun corresponda
     */
    public boolean verificarCorreo (String correo) {
        if (email.read(correo)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Verifica si la contraseña existe
     * @param clave contraseña a validar
     * @return true o false segun corresponda
     */
    public boolean verificarPassword (String clave) {
        if (password.ValidarPassword(clave)) {
            return true;
        } else {
            return false;
        }
    }
}
