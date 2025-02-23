package poly.edu.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.dao.CategoryDAO;
import poly.edu.entity.CategoryEntity;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

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
}
