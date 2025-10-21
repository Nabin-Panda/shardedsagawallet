package com.shardedsagawallet.shardedsagawallet.service;

import com.shardedsagawallet.shardedsagawallet.entities.User;
import com.shardedsagawallet.shardedsagawallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user){
        log.info("creating a new user {}",user.getEmail());

        User newUser =  userRepository.save(user);
        log.info("user successfully created with id {} in the database shardwallet{}", newUser.getId(),(newUser.getId()%2+1));
        return  newUser;
    }
    public User getUserById(Long id){
        log.info("getting the user by id {}",id);
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user exists"));
    }
}
