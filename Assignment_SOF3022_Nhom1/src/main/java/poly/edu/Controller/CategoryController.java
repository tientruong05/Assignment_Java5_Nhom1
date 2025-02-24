package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.service.CategoryService;
import java.util.List;
import poly.edu.entity.ProductEntity;

@Controller
@RequestMapping("/java5/asm")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list-product")
    public String showCategory(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer subCategoryId,
            Model model) {
        
        List<ProductEntity> products;
        String categoryName;
        
        try {
            if (subCategoryId != null) {
                products = categoryService.getProductsBySubCategory(subCategoryId);
                categoryName = categoryService.getSubCategoryName(subCategoryId);
            } else if (categoryId != null) {
                products = categoryService.getProductsByCategory(categoryId);
                categoryName = categoryService.getCategoryName(categoryId);
            } else {
                // Nếu không có categoryId và subCategoryId, lấy tất cả sản phẩm
                products = categoryService.getAllProducts();
                categoryName = "Tất cả sản phẩm";
            }
            
            model.addAttribute("products", products);
            model.addAttribute("categoryName", categoryName);
            
            return "category";
              } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/java5/asm/";
        }
    }

    @GetMapping("/category")
    public String showCategoryProducts(
            @RequestParam(required = false) Integer subCategoryId,
            Model model) {
        
        if (subCategoryId != null) {
            List<ProductEntity> products = categoryService.getProductsBySubCategory(subCategoryId);
            String categoryName = categoryService.getSubCategoryName(subCategoryId);
            
            model.addAttribute("products", products);
            model.addAttribute("categoryName", categoryName);
            return "category";
        }
        
        return "redirect:/java5/asm/";
    }
}