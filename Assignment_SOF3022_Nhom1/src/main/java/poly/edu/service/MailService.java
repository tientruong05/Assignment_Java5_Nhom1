package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    
    @Autowired
    private JavaMailSender mailSender;

    // Gửi email chào mừng
    public void sendWelcomeEmail(String to, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Chào mừng bạn đến với TimeLux Watch!");
            message.setText("Xin chào " + username + ",\n\n"
                    + "Chúc mừng bạn đã đăng ký thành công tài khoản tại Luxe Watch!\n"
                    + "Chúng tôi rất vui mừng được chào đón bạn tham gia cộng đồng của chúng tôi.\n\n"
                    + "Trân trọng,\n"
                    + "Đội ngũ Luxe Watch");

            mailSender.send(message);
            System.out.println("📧 Email đã gửi thành công tới: " + to);
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi gửi email: " + e.getMessage());
        }
    }

    // Gửi email với nội dung tùy chỉnh
    public void sendEmail(String to, String subject, String emailContent) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(emailContent);

            mailSender.send(message);
            System.out.println("📧 Email đã gửi thành công tới: " + to);
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi gửi email: " + e.getMessage());
        }
    }
}
