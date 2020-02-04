package com.fasttrack.cvgt;

import com.fasttrack.cvgt.domain.User;
import com.fasttrack.cvgt.exception.ResourceNotFoundException;
import com.fasttrack.cvgt.service.UserService;
import com.fasttrack.cvgt.steps.UserSteps;
import com.fasttrack.cvgt.transfer.SaveUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSteps userSteps;

    @Test
    public void testCreateUser_whenValidRequest_thenUserIsSaved() {
        userSteps.createUser();
    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateUser_whenInvalidRequest_thenThrowException() {
        SaveUserRequest request = new SaveUserRequest();
        userService.createUser(request);
    }

    @Test
    public void testGetUser_whenExistingUser_thenReturnUser() {
        User createdUser = userSteps.createUser();
        User retrievedUser = userService.getUser(createdUser.getId());

        assertThat(retrievedUser, notNullValue());
        assertThat(retrievedUser.getId(), is(createdUser.getId()));
        assertThat(retrievedUser.getFirstName(), is(createdUser.getFirstName()));
        assertThat(retrievedUser.getLastName(), is(createdUser.getLastName()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUser_whenNotExistingUser_thenThrowResourceNotFound() {
        userService.getUser(0);
    }

    @Test
    public void testUpdateUser_whenValidRequest_thenReturnUpdatedUser() {
        User createdUser = userSteps.createUser();
        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName(createdUser.getFirstName() + " updated");
        request.setLastName(createdUser.getLastName() + " updated");

        User updatedUser = userService.updateUser(createdUser.getId(), request);

        assertThat(updatedUser, notNullValue());
        assertThat(updatedUser.getId(), is(createdUser.getId()));
        assertThat(updatedUser.getFirstName(), is(request.getFirstName()));
        assertThat(updatedUser.getLastName(), is(request.getLastName()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUser_whenExistingUser_thenUserIsDeleted() {
        User user = userSteps.createUser();
        userService.deleteUser(user.getId());
        userService.getUser(user.getId());
    }
}
