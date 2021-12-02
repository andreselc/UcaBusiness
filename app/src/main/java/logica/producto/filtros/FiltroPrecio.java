package logica.producto.filtros;

import logica.producto.Producto;

public class FiltroPrecio extends Filtro {
    private int precio;

    public FiltroPrecio(int id){
        super(id);
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public boolean filtrar(Producto producto) {
        return producto.getPrecio() <= precio;
    }
}
