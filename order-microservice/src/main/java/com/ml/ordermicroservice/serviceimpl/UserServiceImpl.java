package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.model.Role;
import com.ml.ordermicroservice.model.User;
import com.ml.ordermicroservice.repository.RoleRepository;
import com.ml.ordermicroservice.repository.UserRepository;
import com.ml.ordermicroservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public User saveUser(User user) {
        log.info("SAVING USER ==> {}", user);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("SAVING ROLE ==> {}", role);
        return roleRepository.save(role);
    }

    @Override
    public void addUserToRole(String username, String roleName) {
        var user = userRepository.findByUsername(username);
        var role = roleRepository.findByRoleName(roleName);

        if (user.isPresent() && role.isPresent()) {
            log.info("ADDING ROLE{} TO USER{}", user, role);
            user.get().getRoleList().add(role.get());
        }
    }

    @Override
    public User getUser(String username) {
        log.info("FETCH USER ==> {}", username);
        return userRepository.findByUsername(username).orElseGet(User::new);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("FETCH USERS");
        return userRepository.findAll();
    }

}
