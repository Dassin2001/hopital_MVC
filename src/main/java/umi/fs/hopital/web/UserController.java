package umi.fs.hopital.web;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import umi.fs.hopital.entities.User;
import umi.fs.hopital.repository.UserRepository;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/add")
    @ResponseBody
    public String addUserForm() {
        return "<form method='post'>Username: <input name='username'/><br>Password: <input name='password' type='password'/><br>Role: <input name='role'/><br><button type='submit'>Add User</button></form>";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addUser(@ModelAttribute User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(role));
        userRepository.save(user);
        return "User added!";
    }
}