package com.mwas.securityjwt106.Security;

import com.mwas.securityjwt106.Clients.UserRepository;
import com.mwas.securityjwt106.Clients.Web_Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <Web_Users> webUsers=userRepository.findByUsername(username);
        if(webUsers.isPresent()){
            var userObj=webUsers.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }
        return null;
    }

    private String[] getRoles(Web_Users userObj) {
        if (userObj==null){
            return new String[]{"USER"};
        }else{
            return  userObj.getRoles().split(",");
        }
    }
}
