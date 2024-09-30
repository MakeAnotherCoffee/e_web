package com.mwas.securityjwt106.Clients;

import com.mwas.securityjwt106.Admin.AdminRepository;
import com.mwas.securityjwt106.Admin.Allfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServices {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder PasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Web_Users registerUser(Web_Users webUsers) {
        webUsers.setPassword(PasswordEncoder.encode(webUsers.getPassword()));
        webUsers.setRoles("USER");
        return userRepository.save(webUsers);
    }
    public static final String DIR="./AllFiles";
    public File getDownloadFile(String filename) throws Exception{

        if (filename==null){
            throw new NullPointerException("filename is null");
        }
        var fileToDownload=new File(DIR+File.separator+filename);
        if(!Objects.equals(fileToDownload.getParent(),DIR)){
            throw new SecurityException("Unsupported Filename");
        }
        if (!fileToDownload.exists()) {
            throw new FileNotFoundException("file not found Exception" + filename);
        }
                return fileToDownload;
            }

    public List<String> getresources() {
        List<String > all=new ArrayList<>();
        File directory=new File(DIR);
        if(directory.exists() && directory.isDirectory()){
            File[] file= directory.listFiles();
            if(file != null){
                for (File file1 :file){
                    if(file1.isFile()){
                        all.add(file1.getName());
                    }
                }
            }
        }
        return all;
    }

    public List<Allfiles> getFromdb() {
        return adminRepository.findAll();
    }
}



