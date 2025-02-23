package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.entity.*;
import poly.edu.service.CartService;
import poly.edu.service.CartDetailService;
import poly.edu.service.ProductService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/java5/asm")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartDetailService cartDetailService;
    
    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return "redirect:/java5/asm/login";
        }

        // Tìm giỏ hàng của user
        Optional<CartEntity> cart = cartService.getCartByUserId(user.getId());
        if (cart.isPresent()) {
            List<CartDetailEntity> cartItems = cartDetailService.getCartDetailsByCartId(cart.get().getId());
            double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQty())
                .sum();

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("total", total);
            model.addAttribute("shippingFee", 30000.0);
        }

        return "cart";
    }

    @PostMapping("/cart/add/{productId}")
    @ResponseBody
    public String addToCart(@PathVariable("productId") int productId,
                           @RequestParam("quantity") int quantity,
                           HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return "redirect:/java5/asm/login";
        }

        // Tìm hoặc tạo giỏ hàng mới
        CartEntity cart = cartService.getCartByUserId(user.getId())
            .orElseGet(() -> {
                CartEntity newCart = new CartEntity();
                newCart.setUser(user);
                cartService.addCart(newCart);
                return newCart;
            });

        // Thêm sản phẩm vào giỏ hàng
        ProductEntity product = productService.getProductById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        CartDetailEntity cartDetail = cartDetailService.findByCartAndProduct(cart.getId(), productId)
            .orElseGet(() -> {
                CartDetailEntity newDetail = new CartDetailEntity();
                newDetail.setCart(cart);
                newDetail.setProduct(product);
                newDetail.setQty(0);
                newDetail.setPrice(product.getPrice());
                return newDetail;
            });

        cartDetail.setQty(cartDetail.getQty() + quantity);
        cartDetailService.saveOrUpdate(cartDetail);

        return "success";
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCart(@RequestParam("itemId") int itemId,
                           @RequestParam("quantity") int quantity) {
        CartDetailEntity cartDetail = cartDetailService.getCartDetailById(itemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            cartDetailService.removeCartDetail(itemId);
        } else {
            cartDetail.setQty(quantity);
            cartDetailService.updateCartDetail(cartDetail);
        }
        
        return "success";
    }

    @PostMapping("/cart/remove/{itemId}")
    @ResponseBody
    public String removeFromCart(@PathVariable("itemId") int itemId) {
        cartDetailService.removeCartDetail(itemId);
        return "success";
    }
}