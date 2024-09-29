package com.mwas.securityjwt106.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/admin/addfiles")
    public String addfileadmin(){
        return "filetemplate";
    }
    @PostMapping("/addFile")
    public void saveFile(MultipartFile file ,
                         @ModelAttribute Allfiles allfiles) throws Exception {
    adminService.saveToDir(file,allfiles);

    }
    @DeleteMapping("/deleteFile")
    public void deleteFile(@RequestParam("fileName") String fileName) throws Exception {
    adminService.deleteFiles(fileName);
    }
}
