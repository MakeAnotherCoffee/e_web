package com.mwas.securityjwt106.Admin;

import com.mwas.securityjwt106.Clients.Web_Users;
import com.mwas.securityjwt106.Security.UserDetails;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Objects;
import lombok.Builder;
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public static final String DIR="./AllFiles";
    public void saveToDir(MultipartFile file) throws Exception{
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
    }

    public void deleteFiles(String fileName) throws Exception{
        File file=new File(DIR+File.separator+fileName);
        if(!file.exists()){
            throw new FileNotFoundException("no file named"+file.getName());
        }else{
            file.delete();
        }
    }

    public void storeFiledata(Allfiles allfiles, MultipartFile file) {
   Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
     String username="";
     if (authentication != null){
         Object principal =authentication.getPrincipal();
         if(principal instanceof Web_Users){
             username=((Web_Users)principal).getUsername();
         }else{
             System.out.println("error");
         }
     }
       String name=file.getOriginalFilename();
       int index=name.indexOf('.');
       String type=name.substring(index+1);
       allfiles.setFilename(name);
       allfiles.setType(type);
       allfiles.setUploadedBy(username);
       adminRepository.save(allfiles);



    }
}
