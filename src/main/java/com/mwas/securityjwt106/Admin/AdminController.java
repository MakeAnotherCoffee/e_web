package com.mwas.securityjwt106.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/addFile")
    public void saveFile(MultipartFile file ,
                         @RequestBody Allfiles allfiles) throws Exception {
    adminService.saveToDir(file);
    adminService.storeFiledata(allfiles,
                               file);
    }
    @DeleteMapping("/deleteFile")
    public void deleteFile(@RequestParam("fileName") String fileName) throws Exception {
    adminService.deleteFiles(fileName);
    }
}