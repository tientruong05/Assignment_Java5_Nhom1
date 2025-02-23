package poly.edu.service;

import poly.edu.entity.CategoryEntity;
import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getCategoriesByStatus(int status);
    List<CategoryEntity> searchCategoriesByName(String name);
    CategoryEntity getCategoryById(int id);
    List<CategoryEntity> getAllCategories();
    void createCategory(CategoryEntity category);
    void updateCategory(CategoryEntity category);
    void deleteCategory(int id);
}
