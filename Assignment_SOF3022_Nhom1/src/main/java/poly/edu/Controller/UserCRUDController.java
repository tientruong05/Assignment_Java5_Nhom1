package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.entity.UserEntity;
import poly.edu.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserCRUDController {
    
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "crud_users";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "crud_users";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute UserEntity user) {
        userService.createUser(user);
        return "redirect:/admin/users";  // Sửa lại đường dẫn
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        UserEntity user = userService.getUserById(id)  // Thay đổi thành getUserById
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/crud_users";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UserEntity user) {
        userService.updateUser(user);
        return "redirect:/admin/users";  // Sửa lại đường dẫn
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";  // Sửa lại đường dẫn
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("users", userService.searchUsersByName(keyword));
        return "admin/crud_users";
    }

    @GetMapping("/role/{role}")
    public String filterByRole(@PathVariable("role") int role, Model model) {
        model.addAttribute("users", userService.getUsersByRole(role));
        return "crud_users";
    }

    // Thêm endpoint để lấy user theo id cho chức năng edit
    @GetMapping("/get/{id}")
    @ResponseBody
    public UserEntity getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id)  // Thay đổi thành getUserById
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }
}
