package poly.edu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.edu.Dao.UserDAO;
import poly.edu.entity.UserEntity;
import poly.edu.service.MailService;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/account")
public class ForgotPassController {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private MailService mailService; // Service để gửi email

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot_pass";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username, 
                              @RequestParam String email,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<UserEntity> userOpt = userDAO.findByUsernameAndEmail(username, email);
            
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                
                // Tạo mật khẩu mới ngẫu nhiên
                String newPassword = generateRandomPassword();
                
                // Cập nhật mật khẩu mới trong database
                user.setPassword(newPassword); // Nên mã hóa mật khẩu trước khi lưu
                userDAO.update(user);
                
                // Gửi email chứa mật khẩu mới
                String emailContent = "Mật khẩu mới của bạn là: " + newPassword;
                mailService.mailSender(user.getEmail(), "Khôi phục mật khẩu", emailContent);
                
                redirectAttributes.addFlashAttribute("message", 
                    "Mật khẩu mới đã được gửi đến email của bạn.");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Không tìm thấy tài khoản với thông tin này.");
                return "redirect:/forgot-password";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Đã xảy ra lỗi trong quá trình khôi phục mật khẩu.");
            return "redirect:/forgot-password";
        }
    }
    
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
}