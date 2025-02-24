//package poly.edu.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Controller
//@RequestMapping("/java5/asm")
//public class IndexController {
//	
//    @GetMapping({"/", "/home"})
//    public String home() {
//        return "/homepage";
//    }
//    
//    @GetMapping("/login")
//    public String login() {
//    	return "/login-register.html";
//    }
//    
//    @PostMapping("login")
//    public String loginSuccess(HttpServletRequest request, Model model) {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        if ("nhom1".equals(username) && "12345".equals(password)) {
//            model.addAttribute("message", "Đăng nhập thành công");
//            return "homepage"; // Không cần dấu '/'
//        }
//
//        model.addAttribute("message", "Đăng nhập thất bại");
//        return "login-register"; // Không cần .html nếu dùng Thymeleaf
//    }
//
//
//    @RequestMapping("/list-product")
//    public String listProduct() {
//    	return "/list-product.html";
//    }
//
//    @RequestMapping("/crud/categories")
//    public String crudCategories() {
//    	return "/crud_categories.html";
//    }
//
//    @RequestMapping("/crud/orders")
//    public String crudOrders() {
//    	return "/crud_orders.html";
//    }
//
//    @RequestMapping("/crud/products")
//    public String crudProducts() {
//    	return "/crud_products.html";
//    }
//
//    @RequestMapping("/crud/users")
//    public String crudUsers() {
//    	return "/crud_users.html";
//    }
//
//    @RequestMapping("/cart")
//    public String cart() {
//    	return "/cart.html";
//    }
//
//    @RequestMapping("/payment")
//    public String payment() {
//    	return "/payment.html";
//    }
//
//    @RequestMapping("/users/profile")
//    public String profile() {
//    	return "/profile.html";
//    }
//
//    @RequestMapping("/users/change-pw")
//    public String changePass() {
//    	return "/change-pw.html";
//    }
//    
//    @RequestMapping("/users/forgot-pw")
//    public String forgotPass() {
//    	return "/forgot_pass.html";
//    }
//    
//    @RequestMapping("/users/shopping-history")
//    public String shoppHistr() {
//    	return "/shopping_history.html";
//    }
//    
//    @RequestMapping("/statistics/business")
//    public String statisBusiness() {
//    	return "/business-statistics.html";
//    }
//    
//    @RequestMapping("/statistics/customers")
//    public String statisCuss() {
//    	return "/VIP.html";
//    }
//    
//    @RequestMapping("/product-detail")
//    public String productDetail() {
//    	return "/product_detail.html";
//    }
//    
//
//}
