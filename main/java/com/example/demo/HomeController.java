package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    XOrderRepository xOrderRepository;

    @GetMapping("/test")
    public String showTest(Model model){
        model.addAttribute("o", xOrderRepository);
        XOrder x = new XOrder();
        x.calculatePrice("a, b,c");
        return "test";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/";
    }


    @RequestMapping("/")
    public String listOrders(Principal principal, Model model) {
        if(userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }

        model.addAttribute("orders", xOrderRepository.findAll());
        return "index";
    }


    @GetMapping("/add")
    public String orderForm(Model model) {
        model.addAttribute("order", new XOrder());
        return "orderform";
    }

    @PostMapping("/process")
    public String processForm(@ModelAttribute XOrder order, Model model) {

        order.setUser(userService.getUser());
        xOrderRepository.save(order);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/myorders")
    public String myOrders(Model model) {
        User user = userService.getUser();
        /*ArrayList<XOrder> orders = (ArrayList<XOrder>) xOrderRepository.findAll(user);*/
       /* model.addAttribute("orders", orders);*/
        return "myorders";
    }

    @RequestMapping("/allorders")
    public String allOrders(Model model) {
        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }

        model.addAttribute("orders", xOrderRepository.findAll());
        return "allorders";
    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("order", xOrderRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("order", xOrderRepository.findById(id).get());
        return "orderform";
    }

    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id, Authentication auth) {
        xOrderRepository.deleteById(id);

        if (auth.getAuthorities().toString().equals("[ADMIN]")) {
            return "redirect:/allorders";
        }

        else {
            return "redirect:/myorders";
        }
    }
}
