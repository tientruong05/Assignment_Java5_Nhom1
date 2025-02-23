package poly.edu.service;

import poly.edu.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductEntity> getProductById(int id);
    List<ProductEntity> getProductsByName(String name);
    List<ProductEntity> getAllProducts();
    void addProduct(ProductEntity product);
    void updateProduct(ProductEntity product);
    void removeProduct(int id);
}

