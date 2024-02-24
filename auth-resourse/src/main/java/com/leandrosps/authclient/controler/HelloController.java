package com.leandrosps.authclient.controler;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leandrosps.authclient.config.CanReadUsers;

@RestController
@CanReadUsers
public class HelloController {

  @GetMapping("/isAuthenticated")
  public String isAuth(
      @AuthenticationPrincipal Jwt principal) {
    String name = principal.getClaims().get("authorities").toString();
    return "You are athenticated!: " + name;
  }

}
