package com.mwas.securityjwt106.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private PasswordEncoder PasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    public Web_Users registerUser(Web_Users webUsers) {
        webUsers.setPassword(PasswordEncoder.encode(webUsers.getPassword()));
        return userRepository.save(webUsers);
    }
}
