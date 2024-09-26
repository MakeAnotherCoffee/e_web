package com.mwas.securityjwt106.Admin;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
@Service
public class AdminService {
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
}
