package com.mwas.securityjwt106.Admin;
import com.mwas.securityjwt106.Clients.UserRepository;
import com.mwas.securityjwt106.Clients.Web_Users;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    public static final String DIR="./AllFiles";
    public String saveToDir(MultipartFile file, Allfiles allfiles) throws Exception{
        String username="";
        if(file==null){
            throw new Exception("file null Exception");
        }
            File directory=new File(DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
            var fileToSave=new File(DIR+File.separator+file.getOriginalFilename());
        if(!Objects.equals(fileToSave.getParent(),DIR)){
            throw new InvalidFileNameException("invalid naming",fileToSave.getName());
        }
        Files.copy(file.getInputStream(),fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //sep 29 2024 08:30
        //getting necessary file Data
        int index=file.getOriginalFilename().indexOf('.');
        String fileType=file.getOriginalFilename().substring(index+1);
        allfiles.setFilename(file.getOriginalFilename());
        allfiles.setType(fileType);
        allfiles.setUploadedBy(getPrincipal());
        System.out.println(username);
        adminRepository.save(allfiles);

        return username;
    }

public String getPrincipal(){
        String username="";
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            Object principal=authentication.getPrincipal();
            if( principal instanceof UserDetails){
                username=((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                username=(String) principal;
            }

        }
    return username;
}
    public void deleteFiles(String fileName) throws Exception{
        File file=new File(DIR+File.separator+fileName);
        if(!file.exists()){
            throw new FileNotFoundException("no file named"+file.getName());
        }else{
            file.delete();
        }
    }

    public int getAllUsers() {
        List<Web_Users> allUsers=userRepository.findAll();
        return  allUsers.size();
    }
}
