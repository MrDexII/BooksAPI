package com.andrzej.bookDatabase.Controller;

import com.andrzej.bookDatabase.Model.User;
import com.andrzej.bookDatabase.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/home", "/admin/home", "/user/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        return userService.getHomeView();
    }

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        return userService.getLoginView();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        return userService.getRegistrationView();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        return userService.saveUser(user, bindingResult);
    }
}
