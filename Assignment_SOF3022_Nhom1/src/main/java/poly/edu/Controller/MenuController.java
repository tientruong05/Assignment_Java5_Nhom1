package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import poly.edu.dao.SubCategoryDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import poly.edu.entity.SubCategoryEntity;

@ControllerAdvice // Thay đổi từ @Controller sang @ControllerAdvice
public class MenuController {
    
    @Autowired
    private SubCategoryDAO subCategoryDAO;

    @ModelAttribute("watchCategories")
    public Map<String, List<SubCategoryEntity>> getWatchCategories() {
        Map<String, List<SubCategoryEntity>> categories = new HashMap<>();
        
        try {
            // Lấy danh sách đồng hồ nam (id = 1)
            List<SubCategoryEntity> mensWatches = subCategoryDAO.findByCategory_Id(1);
            if (!mensWatches.isEmpty()) {
                categories.put("Đồng hồ Nam", mensWatches);
            }
            
            // Lấy danh sách đồng hồ nữ (id = 2)
            List<SubCategoryEntity> womensWatches = subCategoryDAO.findByCategory_Id(2);
            if (!womensWatches.isEmpty()) {
                categories.put("Đồng hồ Nữ", womensWatches);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return categories;
    }
}