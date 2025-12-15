package com.akif.assetguardian.service;

import com.akif.assetguardian.model.MyUserDetails;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repo.findByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("404");
        }
        return new MyUserDetails(user);
    }

}
