package com.controller;

import com.model.User;
import com.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/find-all")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("data_from_backend", users);


        /**
         * Здесь пишу этот код, чтобы итерироваться
         * по каждому элементу списка(list)
         * и это же пишу в HTML страничке(название: users)
         */
        for (User user : users) {
            System.out.println(user.getId());
            System.out.println(user.getName());
            System.out.println(user.getAge());
        }
        return "users";
    }
}
