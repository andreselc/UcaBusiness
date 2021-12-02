package logica.producto.filtros;

import logica.producto.Producto;

public class FiltroImagenVisible extends Filtro {

    public FiltroImagenVisible(int id){
        super(id);
    }

    @Override
    public boolean filtrar(Producto producto) {
        return producto.getUbicImg().contains(".jpg");
    }
}
