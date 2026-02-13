package com.zerowastehub.backend.publics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HealthCheck {
    @GetMapping("/health-check")
     public  String healthCheck(){
         return  "I am working ZWH backend";
     }
}
