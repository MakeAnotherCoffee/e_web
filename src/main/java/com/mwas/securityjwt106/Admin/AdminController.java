package com.mwas.securityjwt106.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/admin/addfiles")
    public String addfileadmin(){
        return "fileUpload";
    }
    @PostMapping("/addFile")
    public String saveFile(MultipartFile file ,
                         @ModelAttribute Allfiles allfiles) throws Exception {
    adminService.saveToDir(file,allfiles);
    return "redirect:/getAllFiles";
    }

    //work on the redirect

    @DeleteMapping("/deleteFile")
    public void deleteFile(@RequestParam("fileName") String fileName) throws Exception {
    adminService.deleteFiles(fileName);
    }
    @GetMapping("/allUsers")
    public ResponseEntity<Integer> allUserss(Model model) {
        int all=adminService.getAllUsers();
        model.addAttribute("all",all);
        return ResponseEntity.ok(all);
    }
    @GetMapping("/test")
public String tests(){
        return("UserNumber");
    }
}
