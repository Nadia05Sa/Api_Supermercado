package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.Producto;
import mx.ed.utez.api_supermercado.model.dao.IProductoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements IProductoService{

    @Autowired
    private IProductoDao productoDao;

    @Override
    public Producto guardar(Producto producto){
        return productoDao.save(producto);
    }
}
