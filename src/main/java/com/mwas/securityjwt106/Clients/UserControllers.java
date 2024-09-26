package com.mwas.securityjwt106.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;

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

    @PostMapping("/signup")
    public Web_Users register(@RequestBody Web_Users webUsers) {
        return userServices.registerUser(webUsers);
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

}
