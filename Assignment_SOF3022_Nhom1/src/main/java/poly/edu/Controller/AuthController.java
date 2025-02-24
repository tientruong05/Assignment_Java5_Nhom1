package poly.edu.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.edu.Dao.UserDAO;
import poly.edu.entity.UserEntity;
import poly.edu.service.MailService;
import poly.edu.service.SessionService;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/java5/asm")
public class AuthController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MailService emailService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private JavaMailSender mailSender;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @GetMapping({"/", "/home"})
    public String homepage() {
        return "homepage"; // Trang chủ sau khi đăng nhập
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-register"; // Trang đăng nhập
    }

    @GetMapping("/forgot-pass")
    public String forgotPasswordPage() {
        return "forgot_pass"; // Trang quên mật khẩu
    }

    @PostMapping("/login")
    public String loginSuccess(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<UserEntity> user = userDAO.findByEmail(username);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            model.addAttribute("message", "Đăng nhập thành công!");
            return "homepage";
        }

        model.addAttribute("message", "Sai email hoặc mật khẩu!");
        return "login-register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        if (userDAO.findByEmail(email).isPresent()) {
            model.addAttribute("message", "Email đã được sử dụng!");
            return "login-register";
        }
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            model.addAttribute("message", "Email không hợp lệ!");
            return "login-register";
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(0); // Người dùng bình thường
        newUser.setStatus(1); // Mặc định tài khoản được kích hoạt
        try {
            userDAO.save(newUser);
            emailService.sendWelcomeEmail(email, username);
            model.addAttribute("message", "Đăng ký thành công! Vui lòng kiểm tra email của bạn.");
            return "login-register";
        } catch (Exception e) {
            model.addAttribute("message", "Có lỗi xảy ra khi đăng ký!");
            return "login-register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/java5/asm/login"; // Đảm bảo đăng xuất và quay lại trang login
    }

    @PostMapping("/account/resetPassword")
    public String resetPassword(@RequestParam String username, @RequestParam String email, Model model) {
        Optional<UserEntity> userOptional = userDAO.findByUsernameAndEmail(username, email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String newPassword = String.format("%06d", new Random().nextInt(999999)); // Tạo mật khẩu mới
            user.setPassword(newPassword);
            userDAO.update(user);
            sendPasswordResetEmail(user.getEmail(), newPassword);
            model.addAttribute("message", "Mật khẩu mới đã được gửi đến email của bạn.");
            return "login-register";
        } else {
            model.addAttribute("message", "Tên tài khoản hoặc email không đúng!");
            return "login-register";
        }
    }

    @GetMapping("/account/changePassword")
    public String showChangePasswordForm(HttpSession session, Model model) {
        // Kiểm tra nếu người dùng đã đăng nhập hay chưa
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return "redirect:/java5/asm/login"; // Nếu chưa đăng nhập thì chuyển hướng về trang đăng nhập
        }
        return "change_pw"; // Hiển thị form thay đổi mật khẩu
    }

    // Xử lý đổi mật khẩu
    @PostMapping("/account/changePassword")
    public String changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Lấy thông tin người dùng đang đăng nhập
        UserEntity currentUser = (UserEntity) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/java5/asm/login"; // Nếu chưa đăng nhập thì chuyển hướng về trang đăng nhập
        }

        // Kiểm tra mật khẩu hiện tại
        Optional<UserEntity> userCheck = userDAO.findByEmailAndPassword(currentUser.getEmail(), oldPassword);

        if (userCheck.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "redirect:/java5/asm/account/changePassword";
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return "redirect:/java5/asm/account/changePassword";
        }

        // Kiểm tra độ dài mật khẩu mới
        if (newPassword.length() < 5 || newPassword.length() > 30) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới phải từ 5-30 ký tự!");
            return "redirect:/java5/asm/account/changePassword";
        }

        try {
            currentUser.setPassword(newPassword); // Cập nhật mật khẩu mới
            userDAO.update(currentUser);

            // Cập nhật lại session
            session.setAttribute("user", currentUser);

            redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");
            return "redirect:/java5/asm/account/changePassword";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đổi mật khẩu!");
            return "redirect:/java5/asm/account/changePassword";
        }
    }

    private void sendPasswordResetEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mật khẩu mới của bạn");
        message.setText("Mật khẩu mới của bạn là: " + newPassword + "\nVui lòng đăng nhập và đổi mật khẩu để bảo mật tài khoản.");
        mailSender.send(message);
    }
}
