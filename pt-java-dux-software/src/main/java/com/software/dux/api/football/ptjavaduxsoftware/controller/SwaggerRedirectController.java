package com.software.dux.api.football.ptjavaduxsoftware.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/swagger-ui")
public class SwaggerRedirectController {
    @GetMapping
    public RedirectView redirectSwagger() {
        return new RedirectView("/swagger-ui/index.html");
    }
}