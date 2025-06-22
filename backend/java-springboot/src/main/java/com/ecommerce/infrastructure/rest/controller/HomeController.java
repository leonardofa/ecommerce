package com.ecommerce.infrastructure.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class HomeController {

  @Value("${springdoc.swagger-ui.path}")
  private String swaggerUiPath;

  @GetMapping("/")
  public RedirectView redirect() {
    return new RedirectView(swaggerUiPath);
  }

}