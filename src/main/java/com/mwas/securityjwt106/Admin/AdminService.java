package com.mwas.securityjwt106.Admin;
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
import java.util.Objects;

@Service
public class AdminService {
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                username = (String) principal;
            } else {
                System.out.println("Unknown principal type.");
            }
        } else {
            System.out.println("User is not authenticated");
        }

        int index=file.getOriginalFilename().indexOf('.');
        String fileType=file.getOriginalFilename().substring(index+1);
        allfiles.setFilename(file.getOriginalFilename());
        allfiles.setType(fileType);
        allfiles.setUploadedBy(username);
        System.out.println(username);
        adminRepository.save(allfiles);

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

//    public void storeFiledata(Allfiles allfiles, MultipartFile file) {
//   Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
//     String username="";
//     if (authentication != null){
//         Object principal =authentication.getPrincipal();
//         if(principal instanceof Web_Users){
//             username=((Web_Users)principal).getUsername();
//         }else{
//             System.out.println("error");
//         }
//     }
//       String name=file.getOriginalFilename();
//       int index=name.indexOf('.');
//       String type=name.substring(index+1);
//       allfiles.setFilename(name);
//       allfiles.setType(type);
//       allfiles.setUploadedBy(username);
//       allfiles.setId(1);
//       adminRepository.save(allfiles);


}
