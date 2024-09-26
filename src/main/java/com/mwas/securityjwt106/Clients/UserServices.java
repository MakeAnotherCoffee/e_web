package com.mwas.securityjwt106.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UserServices {

    @Autowired
    private PasswordEncoder PasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Web_Users registerUser(Web_Users webUsers) {
        webUsers.setPassword(PasswordEncoder.encode(webUsers.getPassword()));
        webUsers.setRoles("USER");
        return userRepository.save(webUsers);
    }

    public File getDownloadFile(String filename) throws Exception{
        String dir="./AllFiles";
        if (filename==null){
            throw new NullPointerException("filename is null");
        }
        var fileToDownload=new File(dir+File.separator+filename);
        if(!Objects.equals(fileToDownload.getParent(),dir)){
            throw new SecurityException("Unsupported Filename");
        }
        if (!fileToDownload.exists()) {
            throw new FileNotFoundException("file not found Exception" + filename);
        }

                return fileToDownload;
            }
}



