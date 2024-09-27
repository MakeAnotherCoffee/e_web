package com.mwas.securityjwt106.Security;

import com.mwas.securityjwt106.Clients.UserRepository;
import com.mwas.securityjwt106.Clients.Web_Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class UserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Web_Users> webUsers = userRepository.findByUsername(username);
        if (webUsers.isPresent()) {
            var userObj = webUsers.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword()) // Ensure this is the encoded password
                    .authorities(getRoles(userObj)) // Use authorities instead of roles
                    .build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
    private String[] getRoles(Web_Users userObj) {
        if (userObj == null) {
            return new String[]{"ROLE_USER"}; // Ensure roles have ROLE_ prefix
        } else {
            return userObj.getRoles().split(",").stream()
                    .map(role -> "ROLE_" + role) // Prefix roles
                    .toArray(String[]::new);
        }
    }

}
