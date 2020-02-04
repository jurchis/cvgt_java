package com.fasttrack.cvgt.steps;

import com.fasttrack.cvgt.domain.User;
import com.fasttrack.cvgt.service.UserService;
import com.fasttrack.cvgt.transfer.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;

@Component
public class UserSteps {

    @Autowired
    private UserService userService;

    public User createUser() {
        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName("Florin" + System.currentTimeMillis());
        request.setLastName("Jurchis" + System.currentTimeMillis());

        User createdUser = userService.createUser(request);

        assertThat(createdUser, notNullValue());
        assertThat(createdUser.getId(), greaterThan(0L));
        assertThat(createdUser.getFirstName(), is(request.getFirstName()));
        assertThat(createdUser.getLastName(), is(request.getLastName()));

        return createdUser;
    }
}
