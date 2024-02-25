package com.leandrosps.authclient.controler;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandrosps.authclient.config.CanWriteUsersServer;

@RestController
public class HelloController {

  // @CanReadUsers
  @GetMapping("/server/auth-code")
  @PreAuthorize("hasRole('ADMIN')")
  public String clientAuth(
      @AuthenticationPrincipal Jwt principal) {
    return "You are athenticated!: " + principal.getClaims().toString();
  }

  @GetMapping("/server/client-credentials")
  @CanWriteUsersServer
  public String serverAuth(
      @AuthenticationPrincipal Jwt principal) {
    return "You are athenticated!: " + principal.getClaims().toString();
  }

}
