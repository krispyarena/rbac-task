package org.demo.amniltask.controller;

import org.demo.amniltask.model.Admin;
import org.demo.amniltask.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin")
    public String getAdminPage() {
        return "AdminPage";
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("admin") Admin admin, Model model) {

        Admin existingAdmin = adminService.findAdminByEmail(admin.getEmail());

        if (existingAdmin == null) {
            model.addAttribute("error", "Invalid email or password");
        }

        if (!passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())) {
            model.addAttribute("loginError", "Invalid email or password.");
            return "Login";
        }

        return "ViewStudentPage";


    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("admin", new Admin());

        return "Signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("admin") Admin admin, Model model) {
        boolean hasErrors = false;

        if(admin.getFname()==null || admin.getFname().isEmpty()) {
            model.addAttribute("firstNameError", "Please enter a valid first name");
            hasErrors = true;
        }

        if(admin.getLname()==null || admin.getLname().isEmpty()) {
            model.addAttribute("lastNameError", "Please enter a valid last name");
            hasErrors = true;
        }

        if(admin.getEmail()==null || admin.getEmail().isEmpty()) {
            model.addAttribute("emailError", "Please enter a valid email");
            hasErrors = true;
        }
        if(admin.getPassword()==null || admin.getPassword().isEmpty()) {
            model.addAttribute("passwordError", "Please enter a valid password");
            hasErrors = true;
        } else if (admin.getPassword().length() <6) {
            model.addAttribute("passwordError", "Password must be at least 6 characters");
            hasErrors = true;
        }

        if(admin.getConfirmPassword()==null || admin.getConfirmPassword().isEmpty()) {
            model.addAttribute("confirmPasswordError", "Please enter a valid confirm password");
            hasErrors = true;
        } else if (admin.getConfirmPassword() == (admin.getPassword())) {
            model.addAttribute("confirmPasswordError", "Passwords do not match");
        }

        if(admin.getPhone()==null || admin.getPhone().isEmpty()) {
            model.addAttribute("phoneError", "Please enter a valid phone number");
            hasErrors = true;
        }

        Admin checkExistingAdmin = adminService.findAdminByEmail(admin.getEmail());
        if(checkExistingAdmin!=null) {
            model.addAttribute("emailError", "This email already exists");
            hasErrors = true;
        }

        Admin checkExistingAdmin2 = adminService.findAdminByPhone(admin.getPhone());
        if(checkExistingAdmin2!=null) {
            model.addAttribute("phoneError", "This phone already exists");
            hasErrors = true;
        }

        if(hasErrors) {
            return "Signup";
        }

        admin.setRole("admin");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.saveAdmin(admin);
        return "redirect:/login";

    }
}
