package com.andrzej.bookDatabase.Controller;

import com.andrzej.bookDatabase.Model.User;
import com.andrzej.bookDatabase.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() != "anonymousUser") {
            return createHomeModelAndView(modelAndView, auth);
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            User userExists = userService.findUserByEmail(user.getEmail());
            if (userExists != null) {
                bindingResult
                        .rejectValue("email", "error.user",
                                "There is already a user registered with the email provided");
                modelAndView.setViewName("registration");
                return modelAndView;
            }
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getPrincipal() != "anonymousUser") {
                return createHomeModelAndView(modelAndView, auth);
            } else
                modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/home", "/admin/home", "/user/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (auth.getAuthorities().contains((new SimpleGrantedAuthority("ADMIN")))) {
            modelAndView = createHomeModelAndView(modelAndView, auth);
            modelAndView.setViewName("admin/home");
            return modelAndView;
        } else {
            modelAndView = createHomeModelAndView(modelAndView, auth);
            modelAndView.setViewName("user/home");
            return modelAndView;
        }
    }

/*
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return createHomeModelAndView(modelAndView, auth);
    }
*/

    private ModelAndView createHomeModelAndView(ModelAndView modelAndView, Authentication auth) {
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        //modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("successMessage", "User has been logged in successfully");
        //modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}
