package com.ecommerce.infrastructure.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

  @Value("${springdoc.swagger-ui.path}")
  private String swaggerUiPath;

  @GetMapping("/")
  public RedirectView redirect() {
    return new RedirectView(swaggerUiPath);
  }

  /**
   * Simple health check endpoint that returns status UP when the application is running.
   * @return A JSON response with status information
   */
  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> healthCheck() {
    Map<String, String> response = new HashMap<>();
    response.put("status", "UP");
    return ResponseEntity.ok(response);
  }

}
