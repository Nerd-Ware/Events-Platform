package com.example.eventplatform.config;
import com.example.eventplatform.model.AplicationUser;
import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.repository.DbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Dependency Injection & IoC
    @Autowired
    DbUserRepository dbUserRepository;

    // Polymorphism
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUser applicationUser1 = dbUserRepository.findByUsername(username);
        AplicationUser applicationUser = new AplicationUser(applicationUser1);
        // Error handling ... the user is equal to null (doesn't exist in the database)
        if(applicationUser1 == null){
            throw  new UsernameNotFoundException("The user "+ username + " does not exist");
        }
        return applicationUser;
    }
}