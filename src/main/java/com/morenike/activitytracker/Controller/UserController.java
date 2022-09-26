package com.morenike.activitytracker.Controller;

import com.morenike.activitytracker.Entity.User;
import com.morenike.activitytracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * user login page
     */
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("register", new User());
        return "register";
    }

    /*
     * user login
     */
    @PostMapping("/register")
    public String register(@ModelAttribute("register") User user, Model model) {
        if (userService.registerUser(user)) {
            model.addAttribute("success", "Registered Successfully");
            return "login";
        }
        model.addAttribute("error", "Password or Email already exit");
        return "register";
    }


    /*
     * user login page
     */
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("login", new User());
        return "index";
    }

    /*
     * user login
     */
    @PostMapping("/login")
    public String login(@ModelAttribute("login") User user, Model model, HttpSession session) {
        if (userService.loginAuth(user)) {
            session.setAttribute("Auth", user.getEmail());
            return "redirect:/";
        }
        model.addAttribute("error", "Wrong email or password");
        return "index";
    }

    /*
     * user logout
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/account/login";
    }
}
