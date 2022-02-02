package logica.usuario;

import java.util.Date;

public class Bloqueo {

    public boolean bloqueo=false;
    @SuppressWarnings("deprecation")
    private static Date fechaBloqueo = new Date();

    public boolean getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(boolean bloqueo) {
        this.bloqueo = bloqueo;
    }

    public void Bloquear(){
        bloqueo=true;
    }

    public boolean Desbloqueo(){
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
