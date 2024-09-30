package com.mwas.securityjwt106.Clients;

import com.mwas.securityjwt106.Admin.Allfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class UserControllers {
    @Autowired
    private UserServices userServices;

    @GetMapping("/admin/home")
    public String adminPage() {
        return "admin page";
    }

    @GetMapping("/user/home")
    public String userPage() {
        return "user page";
    }
    public String downloadPage() {
        return "/download_file";
    }
    @GetMapping("")
    public String homePage() {
        return "index";
    }
    @GetMapping("/signup")
    public String SignupPage(){
        return("signup");
    }



    @PostMapping("/send")
    public void register(@ModelAttribute Web_Users webUsers) {
         userServices.registerUser(webUsers);
    }



    @GetMapping("/signin")
    public String loginPage(){
        return "signin";
    }

    @GetMapping("/user/download/{fileName}")
    public ResponseEntity<Resource> downloadFromDir(@PathVariable String fileName) {
        try {
            var fileToDownload = userServices.getDownloadFile(fileName);
            if (!fileToDownload.exists()) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            return ResponseEntity.ok()
                    .contentLength(fileToDownload.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getName() + "\"")
                    .body(new InputStreamResource(Files.newInputStream(fileToDownload.toPath())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping("/getAllFiles")
//    public String gettAll(Model model){
//        List<String> allFiles=userServices.getresources();
//        model.addAttribute("allFiles",allFiles);
//        return "allfiles";
//    }
@GetMapping("/getAllFiles")
    public String trial(Model model){
        List<Allfiles> all=userServices.getFromdb();
        model.addAttribute("all", all);
        return  "allfiles";
    }
}
