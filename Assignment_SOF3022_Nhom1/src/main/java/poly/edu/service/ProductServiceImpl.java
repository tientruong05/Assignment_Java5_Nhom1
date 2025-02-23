package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.dao.ProductDAO;
import poly.edu.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Optional<ProductEntity> getProductById(int id) {
        return productDAO.findById(id);
    }

    @Override
    public List<ProductEntity> getProductsByName(String name) {
        return productDAO.findByNameContaining(name);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public void addProduct(ProductEntity product) {
        productDAO.save(product);
    }

    @Override
    public void updateProduct(ProductEntity product) {
        productDAO.update(product);
    }

    @Override
    public void removeProduct(int id) {
        productDAO.delete(id);
    }
}

