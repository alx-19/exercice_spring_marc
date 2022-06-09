package com.alex.buildindingAndco.service.impl;

import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.User;
import com.alex.buildindingAndco.repository.UserRepository;
import com.alex.buildindingAndco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll(Sort.by("username").ascending());
    }

    @Override
    public User getById(Integer id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("Unknown user."));
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UnknownResourceException("No user found for this username."));
    }

    @Override
    public User createUser(User user) {
        String passwordEncoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(passwordEncoded);
        return this.userRepository.save(user);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        User user = this.userRepository.findByUsername(username).get();
        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return user;
        }
        throw new UnknownResourceException("No user found for the given user/password");
    }

    @Override
    public void deleteUser(Integer id) {
        User userToDelete = this.getById(id);
        this.userRepository.delete(userToDelete);
    }

    @Override
    public User update(User user) {
        User existingUser = this.getById(user.getId());
        existingUser.setUsername(user.getUsername());
        existingUser.setMail(user.getMail());
        existingUser.setGrants(user.getGrants());

        // Chiffrer le MDP reçu
        String passwordEncoded = new BCryptPasswordEncoder().encode(user.getPassword());
        existingUser.setPassword(passwordEncoded);

        // TODO: compléter le code de mise à jour
        return userRepository.save(existingUser);
    }
}
