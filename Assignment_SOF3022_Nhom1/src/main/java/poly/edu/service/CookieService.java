package poly.edu.service;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public CookieService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Đọc cookie từ request
     * 
     * @param name tên cookie cần đọc
     * @return đối tượng cookie đọc được hoặc null nếu không tồn tại
     */
    public Cookie get(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * Đọc giá trị của cookie từ request
     * 
     * @param name tên cookie cần đọc
     * @return chuỗi giá trị đọc được hoặc rỗng nếu không tồn tại
     */
    public String getValue(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return ""; // Không tìm thấy cookie
    }

    /**
     * Tạo và gửi cookie về client
     * 
     * @param name  tên cookie
     * @param value giá trị cookie
     * @param hours thời hạn (giờ)
     * @return đối tượng cookie đã tạo
     */
    public Cookie add(String name, String value, int hours) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(hours * 60 * 60); // Thời gian sống của cookie (giờ -> giây)
        cookie.setPath("/"); // Áp dụng cho toàn bộ ứng dụng
        cookie.setHttpOnly(true);  // Bảo mật: chỉ cho phép truy cập cookie từ server
        cookie.setSecure(true);    // Bảo mật: chỉ truyền cookie qua HTTPS (nếu đang dùng HTTPS)
        response.addCookie(cookie); 
        return cookie;
    }

    /**
     * Xóa cookie khỏi client
     * 
     * @param name tên cookie cần xóa
     */
    public void remove(String name) {
        Cookie cookie = get(name);
        if (cookie != null) {
            cookie.setMaxAge(0); // Đặt thời gian sống cookie là 0 để xóa nó
            cookie.setPath("/"); // Áp dụng cho toàn bộ ứng dụng
            cookie.setHttpOnly(true);  // Bảo mật khi xóa cookie
            cookie.setSecure(true);    // Đảm bảo cookie bị xóa qua HTTPS (nếu đang dùng HTTPS)
            response.addCookie(cookie);
        }
    }
}

