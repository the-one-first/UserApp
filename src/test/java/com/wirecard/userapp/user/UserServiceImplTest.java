package com.wirecard.userapp.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import com.wirecard.userapp.enumerator.ResponseEnum;
import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.user.repository.UserRepository;
import com.wirecard.userapp.user.service.impl.UserServiceImpl;
import com.wirecard.userapp.usertype.entity.UserType;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    Pageable pageable;

    @Mock
    Sort sort;
    
    /*
     * Testing : Get all user data 
     * Expectation : Return two records and userRepository.findUserByPredicate was called 1 time
     */
    @Test
    public void getAllUserDataTest() {

        final User user = new User();
        final User user2 = new User();

        prepareMockingBeforeCallingImplementation();
        when(userRepository.findUserByPredicate(pageable, null, null, null, null))
                .thenReturn(new ArrayList<>(Arrays.asList(user, user2)));

        assertEquals(2, userServiceImpl.getUserList(pageable, null, null, null, null).getBody().getUsers().size());
        verify(userRepository, times(1)).findUserByPredicate(pageable, null, null, null, null);

    }
    
    /*
     * Testing : Insert new user 
     * Expectation : Return Success and Status Code = 201 CREATED and userRepository.save was called 1 time
     */
    @Test
    public void insertNewUserTest() {

        final User user = prepareUserForTest();

        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<DefaultResponse> responseInsert = userServiceImpl.insertNewUser(user);

        assertEquals(ResponseEnum.SUCCESS_STATUS.getCode(), responseInsert.getBody().getStatus());
        assertEquals("201 CREATED", responseInsert.getStatusCode().toString());
        verify(userRepository, times(1)).save(user);

    }
    
    /*
     * Testing : Insert new user name that existed in DB 
     * Expectation : Return Error and Status Code = 409 CONFLICT and userRepository.save was never called
     */
    @Test
    public void insertNewUserWithNameIsAlreadyExistedInDBTest() {

        final User user = prepareUserForTest();

        when(userRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(user)));
        ResponseEntity<DefaultResponse> responseInsert = userServiceImpl.insertNewUser(user);

        assertEquals(ResponseEnum.ERROR_STATUS.getCode(), responseInsert.getBody().getStatus());
        assertEquals("409 CONFLICT", responseInsert.getStatusCode().toString());
        verify(userRepository, never()).save(user);

    }
    
    /*
     * Testing : Get user data by name Hadi Gunawan 
     * Expectation : Return user with name Hadi Gunawan and userRepository.findUserByPredicate was called 1 time
     */
    @Test
    public void getUserDataByNameTest() {

        final User user = prepareUserForTest();
        final String userNameToFind = "Hadi Gunawan";

        prepareMockingBeforeCallingImplementation();
        when(userRepository.findUserByPredicate(pageable, null, userNameToFind, null, null))
                .thenReturn(new ArrayList<>(Arrays.asList(user)));

        assertEquals(userNameToFind, userServiceImpl.getUserList(pageable, null, userNameToFind, null, null)
                .getBody().getUsers().get(0).getUserName());
        verify(userRepository, times(1)).findUserByPredicate(pageable, null, userNameToFind, null, null);

    }
    
    /*
     * Testing : Get user data by ID 5L 
     * Expectation : Return user with ID 5L and userRepository.findUserByPredicate was called 1 time
     */
    @Test
    public void getUserDataByIdTest() {

        final User user = prepareUserForTest();
        final Long userIdToFind = 5L;

        prepareMockingBeforeCallingImplementation();
        when(userRepository.findUserByPredicate(pageable, userIdToFind, null, null, null))
                .thenReturn(new ArrayList<>(Arrays.asList(user)));

        assertEquals(userIdToFind, userServiceImpl.getUserList(pageable, userIdToFind, null, null, null).getBody()
                .getUsers().get(0).getId());
        verify(userRepository, times(1)).findUserByPredicate(pageable, userIdToFind, null, null, null);

    }
    
    /*
     * Testing : Get user data by invalid ID 
     * Expectation : Return zero user and userRepository.findUserByPredicate was called 1 time
     */
    @Test
    public void getUserByInvalidIdTest() {

        final Long userIdToFind = 89L;

        prepareMockingBeforeCallingImplementation();

        assertEquals(0,
                userServiceImpl.getUserList(pageable, userIdToFind, null, null, null).getBody().getUsers().size());
        verify(userRepository, times(1)).findUserByPredicate(pageable, userIdToFind, null, null, null);

    }
    
    /*
     * Testing : Update existing user by ID and user object 
     * Expectation : Return Success and Status Code = 200 OK and userRepository.save was called 1 time
     */
    @Test
    public void updateExistingUserByIdAndUserTypeObjectTest() {

        final User user = prepareUserForTest();
        User updatedUser = user;
        updatedUser.setUserName("Michael Owen");
        final Long userIdToFind = 7L;

        when(userRepository.findById(userIdToFind)).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        ResponseEntity<DefaultResponse> responseUpdate = userServiceImpl
                .updateExistingUserById(userIdToFind, updatedUser);

        assertEquals(ResponseEnum.SUCCESS_STATUS.getCode(), responseUpdate.getBody().getStatus());
        assertEquals("200 OK", responseUpdate.getStatusCode().toString());
        verify(userRepository, times(1)).save(updatedUser);

    }
    
    /*
     * Testing : Update existing user using invalid ID 
     * Expectation : Return Error and Status Code = 404 NOT_FOUND and never called userRepository.save
     */
    @Test
    public void updateExistingUserUsingInvalidIdTest() {

        User updatedUser = prepareUserForTest();
        updatedUser.setUserName("Michael Owen");
        final Long userIdToFind = 75L;

        when(userRepository.findById(userIdToFind)).thenThrow(EmptyResultDataAccessException.class);
        ResponseEntity<DefaultResponse> responseUpdate = userServiceImpl
                .updateExistingUserById(userIdToFind, updatedUser);

        assertEquals(ResponseEnum.ERROR_STATUS.getCode(), responseUpdate.getBody().getStatus());
        assertEquals("404 NOT_FOUND", responseUpdate.getStatusCode().toString());
        verify(userRepository, never()).save(updatedUser);

    }
    
    /*
     * Testing : Delete existing user by ID 
     * Expectation : Return Success and Status Code = 202 ACCEPTED and userRepository.deleteById was called 1 time
     */
    @Test
    public void deleteExistingUserByIdTest() {

        final User user = prepareUserForTest();
        final Long userIdToDelete = 10L;

        when(userRepository.save(user)).thenReturn(user);
        userServiceImpl.insertNewUser(user);
        doAnswer(invocation -> {
            Long parameterUserId = invocation.getArgument(0);

            assertEquals(userIdToDelete, parameterUserId);
            return null;
        }).when(userRepository).deleteById(userIdToDelete);
        ResponseEntity<DefaultResponse> responseDelete = userServiceImpl
                .deleteExistingUserById(userIdToDelete);

        assertEquals(ResponseEnum.SUCCESS_STATUS.getCode(), responseDelete.getBody().getStatus());
        assertEquals("202 ACCEPTED", responseDelete.getStatusCode().toString());
        verify(userRepository, times(1)).deleteById(userIdToDelete);

    }
    
    /*
     * Testing : Delete existing user by invalid ID 
     * Expectation : Return Error and Status Code = 404 NOT_FOUND and userRepository.deleteById was called 1 time
     */
    @Test
    public void deleteExistingUserByInvalidIdTest() {

        final Long userIdToDelete = 70L;

        doThrow(EmptyResultDataAccessException.class).when(userRepository).deleteById(userIdToDelete);

        ResponseEntity<DefaultResponse> responseDelete = userServiceImpl
                .deleteExistingUserById(userIdToDelete);

        assertEquals(ResponseEnum.ERROR_STATUS.getCode(), responseDelete.getBody().getStatus());
        assertEquals("404 NOT_FOUND", responseDelete.getStatusCode().toString());
        verify(userRepository, times(1)).deleteById(userIdToDelete);

    }

    
    private User prepareUserForTest() {

        User user = new User();
        user.setId(5L);
        user.setUserName("Hadi Gunawan");
        user.setUserDate(new Date());
        user.setUserType(prepareUserTypeForTest());
        return user;

    }
    
    private UserType prepareUserTypeForTest() {

        UserType userType = new UserType();
        userType.setId(14L);
        userType.setTypeName("Human Resource Local");
        return userType;

    }

    private void prepareMockingBeforeCallingImplementation() {

        when(pageable.getPageSize()).thenReturn(10);
        when(pageable.getSort()).thenReturn(sort);

    }

}
