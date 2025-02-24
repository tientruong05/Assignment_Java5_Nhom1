package poly.edu.service;

import poly.edu.entity.CategoryEntity;
import poly.edu.entity.ProductEntity;
import java.util.List;

public interface CategoryService {
    List<ProductEntity> getProductsBySubCategory(int subCategoryId);
    List<ProductEntity> getProductsByCategory(int categoryId);
    List<ProductEntity> getAllProducts();
    String getCategoryName(int categoryId);
    String getSubCategoryName(int subCategoryId);
    List<CategoryEntity> getCategoriesByStatus(int status);
    List<CategoryEntity> searchCategoriesByName(String name);
    CategoryEntity getCategoryById(int id);
    List<CategoryEntity> getAllCategories();
    void createCategory(CategoryEntity category);
    void updateCategory(CategoryEntity category);
    void deleteCategory(int id);
}
