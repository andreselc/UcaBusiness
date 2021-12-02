package logica.producto.filtros;

import logica.producto.Producto;

public class FiltroPrecioVisible extends Filtro {
    public FiltroPrecioVisible(int id){
        super(id);
    }

    @Override
    public boolean filtrar(Producto producto) {
        return producto.isPrecioVisible();
    }
}
