package logica.usuario;

import java.util.Date;

public class Bloqueo {

    public Boolean bloqueo=false;
    @SuppressWarnings("deprecation")
    private static Date fechaBloqueo = new Date();

    public Boolean getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(Boolean bloqueo) {
        this.bloqueo = bloqueo;
    }

    public void Bloquear(){
        bloqueo=true;
    }

    public Boolean Desbloqueo(){
        @SuppressWarnings("deprecation")
        Date fechaDesbloqueo = new Date();
        System.out.println("Fecha2: "+fechaDesbloqueo);
        int validar = fechaBloqueo.compareTo(fechaDesbloqueo);
        if((validar<0)||(validar==0)){
            bloqueo=false;
        }
        return bloqueo;
    }

}
