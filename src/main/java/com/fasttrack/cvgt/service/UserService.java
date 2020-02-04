package com.fasttrack.cvgt.service;

import com.fasttrack.cvgt.domain.User;
import com.fasttrack.cvgt.exception.ResourceNotFoundException;
import com.fasttrack.cvgt.persistance.UserRepository;
import com.fasttrack.cvgt.transfer.SaveUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(SaveUserRequest request) {

        LOGGER.info("Creating user {} ", request);
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
    }

    public User getUser(long id) {

        LOGGER.info("Retrieving User {}", id);
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User " + id + " does not exist."));
    }

    public User updateUser(long id, SaveUserRequest request) {
        LOGGER.info("Updating user {}: {}", id, request);
        User retrievedUser = getUser(id);
        BeanUtils.copyProperties(request, retrievedUser);
        return userRepository.save(retrievedUser);
    }

    public void deleteUser(long id) {
        LOGGER.info("Deleting user {}:", id);
        userRepository.deleteById(id);
        LOGGER.info("User id: {} has been deleted", id);
    }
}


