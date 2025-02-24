package poly.edu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.Dao.UserDAO;
import poly.edu.entity.UserEntity;

import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session) {
        // Lấy thông tin user từ session
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        return "account/profile"; // Trả về trang profile.html
    }

    @PostMapping("/profilecard")
    public String updateProfile(@RequestParam("username") String username,
                              @RequestParam("fullname") String fullname,
                              @RequestParam("phonenumber") String phoneNumber,
                              @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            // Lấy thông tin user từ session
            UserEntity user = (UserEntity) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            // Cập nhật thông tin cơ bản
            user.setFullName(fullname);
            user.setPhone(phoneNumber);

            // Xử lý upload ảnh đại diện nếu có
            if (avatar != null && !avatar.isEmpty()) {
                try {
                    String uploadDir = "src/main/resources/static/photos/";
                    String fileName = avatar.getOriginalFilename();
                    File uploadPath = new File(uploadDir);
                    
                    if (!uploadPath.exists()) {
                        uploadPath.mkdirs();
                    }
                    
                    File destination = new File(uploadPath.getAbsolutePath() + File.separator + fileName);
                    avatar.transferTo(destination);
                    
                    // Lưu đường dẫn ảnh vào database
                    user.setPhone(fileName);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", "Không thể upload ảnh. Vui lòng thử lại.");
                    return "redirect:/account/profile";
                }
            }
         // Cập nhật vào database
            userDAO.update(user);
            
            // Cập nhật lại session
            session.setAttribute("user", user);

            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại.");
        }

        return "redirect:/account/profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "account/change_password"; // Trả về trang đổi mật khẩu
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("confirmPassword") String confirmPassword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            UserEntity user = (UserEntity) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            // Kiểm tra mật khẩu hiện tại
            if (!user.getPassword().equals(currentPassword)) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
                return "redirect:/account/change-password";
            }

            // Kiểm tra mật khẩu mới và xác nhận mật khẩu
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
                return "redirect:/account/change-password";
            }

            // Cập nhật mật khẩu mới
            user.setPassword(newPassword); // Nên mã hóa mật khẩu trước khi lưu
            userDAO.update(user);

            // Cập nhật session
            session.setAttribute("user", user);

            redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công!");
            return "redirect:/account/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại.");
            return "redirect:/account/change-password";
        }
    }
}