package com.expenseTracker.expenseTracker.security;

import com.expenseTracker.expenseTracker.entity.User;
import com.expenseTracker.expenseTracker.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomeUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("This user email does not exist : " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),
                                                                      new ArrayList<>());
    }
}
