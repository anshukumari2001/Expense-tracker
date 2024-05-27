package com.expenseTracker.expenseTracker.controller;

import com.expenseTracker.expenseTracker.model.AuthModel;
import com.expenseTracker.expenseTracker.response.JwtResponse;
import com.expenseTracker.expenseTracker.security.CustomeUserDetailService;
import com.expenseTracker.expenseTracker.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomeUserDetailService customeUserDetailService;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomeUserDetailService customeUserDetailService,
                          JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.customeUserDetailService = customeUserDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createUser(@Valid @RequestBody AuthModel authModel)
        throws Exception {

        getAuthenticate(authModel);
        UserDetails userDetails = customeUserDetailService.loadUserByUsername(authModel.getEmail());
        String token= jwtTokenUtil.generateToken(userDetails);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);

    }

    private void getAuthenticate(AuthModel authModel) throws Exception {
       try {
           authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));
       }
       catch (BadCredentialsException e){
           throw new Exception("Bad credential");
       }
       catch (DisabledException e){
           throw new Exception("Disable exception");
       }

    }
}
