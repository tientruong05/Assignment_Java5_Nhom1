package poly.edu.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.dao.ProductDAO;
import poly.edu.dao.CategoryDAO;
import poly.edu.dao.SubCategoryDAO;
import poly.edu.entity.CategoryEntity;
import poly.edu.entity.ProductEntity;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private SubCategoryDAO subCategoryDAO;

    @Override
    public List<CategoryEntity> getCategoriesByStatus(int status) {
        return categoryDAO.findByStatus(status);
    }

    @Override
    public List<CategoryEntity> searchCategoriesByName(String name) {
        return categoryDAO.findByNameContaining(name);
    }

    @Override
    public CategoryEntity getCategoryById(int id) {
        return categoryDAO.findById(id);
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public void createCategory(CategoryEntity category) {
        categoryDAO.save(category);
    }

    @Override
    public void updateCategory(CategoryEntity category) {
        categoryDAO.update(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryDAO.delete(id);
    }

    @Override
    public List<ProductEntity> getProductsBySubCategory(int subCategoryId) {
        return productDAO.findBySubCategoryId(subCategoryId);
    }

    @Override
    public List<ProductEntity> getProductsByCategory(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    @Override
    public String getCategoryName(int categoryId) {
        return categoryDAO.findById(categoryId).getName();
    }

    @Override
    public String getSubCategoryName(int subCategoryId) {
        return subCategoryDAO.findById(subCategoryId).getSubCategoriesName();
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productDAO.findAll();
    }
}
