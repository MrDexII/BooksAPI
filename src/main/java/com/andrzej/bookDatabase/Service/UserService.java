package com.andrzej.bookDatabase.Service;

import com.andrzej.bookDatabase.Model.Role;
import com.andrzej.bookDatabase.Model.User;
import com.andrzej.bookDatabase.Repository.RoleRepository;
import com.andrzej.bookDatabase.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ModelAndView saveUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            User userExists = userRepository.findByEmail(user.getEmail());
            if (userExists != null) {
                bindingResult
                        .rejectValue("email", "error.user",
                                "There is already a user registered with the email provided");
                modelAndView.setViewName("registration");
                return modelAndView;
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            List<Role> userRole = new ArrayList<>();
            userRole.add(roleRepository.findByRole("USER"));
            user.setRoles(userRole);

            userRepository.save(user);
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

    // VIEWS
    public ModelAndView getLoginView() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() != "anonymousUser") {
            return createHomeModelAndView(modelAndView, auth);
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    public ModelAndView getRegistrationView() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    public ModelAndView getHomeView() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (auth.getAuthorities().contains((new SimpleGrantedAuthority("ADMIN")))) {
            createHomeModelAndView(modelAndView, auth);
            modelAndView.setViewName("admin/home");
            return modelAndView;
        } else {
            createHomeModelAndView(modelAndView, auth);
            modelAndView.setViewName("user/home");
            return modelAndView;
        }
    }

    private ModelAndView createHomeModelAndView(ModelAndView modelAndView, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        //modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("successMessage", "User has been logged in successfully");
        //modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}
