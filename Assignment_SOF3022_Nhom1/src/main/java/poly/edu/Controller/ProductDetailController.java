package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.entity.ProductEntity;
import poly.edu.service.ProductService;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{id}")
    public String getProductDetail(@PathVariable("id") int id, Model model) {
        // Lấy thông tin chi tiết sản phẩm
        Optional<ProductEntity> productOptional = productService.getProductById(id);
        
        if (productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            model.addAttribute("product", product);
            
            // Lấy danh sách sản phẩm liên quan (ví dụ: cùng danh mục con)
            List<ProductEntity> relatedProducts = productService.getAllProducts()
                .stream()
                .filter(p -> p.getSubCategory().getId() == product.getSubCategory().getId())
                .filter(p -> p.getId() != product.getId())
                .limit(6)
                .toList();
            
            model.addAttribute("relatedProducts", relatedProducts);
            
            return "detail_product";
        } else {
            return "redirect:/products"; // Chuyển hướng về trang danh sách sản phẩm nếu không tìm thấy
        }
    }
}