package com.wirecard.userapp.usertype;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import com.wirecard.userapp.enumerator.ResponseEnum;
import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.usertype.entity.UserType;
import com.wirecard.userapp.usertype.repository.UserTypeRepository;
import com.wirecard.userapp.usertype.service.impl.UserTypeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserTypeServiceImplTest {

    @Mock
    UserTypeRepository userTypeRepository;

    @InjectMocks
    UserTypeServiceImpl userTypeServiceImpl;

    @Mock
    Pageable pageable;

    @Mock
    Sort sort;

    /*
     * Testing : Get all user type data Expectation : Return two records and
     * userTypeRepository.findUserTypeByPredicate was called 1 time
     */
    @Test
    public void getAllUserTypesDataTest() {

        final UserType userType = new UserType();
        final UserType userType2 = new UserType();

        prepareMockingBeforeCallingImplementation();
        when(userTypeRepository.findUserTypeByPredicate(pageable, null, null))
                .thenReturn(new ArrayList<>(Arrays.asList(userType, userType2)));

        assertEquals(2, userTypeServiceImpl.getUserTypeList(pageable, null, null).getBody().getUserTypes().size());
        verify(userTypeRepository, times(1)).findUserTypeByPredicate(pageable, null, null);

    }

    /*
     * Testing : Insert new user type Expectation : Return Success and Status Code =
     * 201 CREATED and userTypeRepository.save was called 1 time
     */
    @Test
    public void insertNewUserTypeTest() {

        final UserType userType = new UserType();

        when(userTypeRepository.save(userType)).thenReturn(userType);
        ResponseEntity<DefaultResponse> responseInsert = userTypeServiceImpl.insertNewUserType(userType);

        assertEquals(ResponseEnum.SUCCESS_STATUS.getCode(), responseInsert.getBody().getStatus());
        assertEquals("201 CREATED", responseInsert.getStatusCode().toString());
        verify(userTypeRepository, times(1)).save(userType);

    }

    /*
     * Testing : Get user type data by name Clerk Local Expectation : Return user
     * type with name Clerk Local and userTypeRepository.findUserTypeByPredicate was
     * called 1 time
     */
    @Test
    public void getUserTypeDataByNameTest() {

        final UserType userType = prepareUserTypeForTest();
        final String userTypeNameToFind = "Admin Local";

        prepareMockingBeforeCallingImplementation();
        when(userTypeRepository.findUserTypeByPredicate(pageable, null, userTypeNameToFind))
                .thenReturn(new ArrayList<>(Arrays.asList(userType)));

        assertEquals(userTypeNameToFind, userTypeServiceImpl.getUserTypeList(pageable, null, userTypeNameToFind)
                .getBody().getUserTypes().get(0).getTypeName());
        verify(userTypeRepository, times(1)).findUserTypeByPredicate(pageable, null, userTypeNameToFind);

    }

    /*
     * Testing : Get user type data by ID 10L Expectation : Return user type with ID
     * 10L and userTypeRepository.findUserTypeByPredicate was called 1 time
     */
    @Test
    public void getUserTypeDataByIdTest() {

        final UserType userType = prepareUserTypeForTest();
        final Long userTypeIdToFind = 10L;

        prepareMockingBeforeCallingImplementation();
        when(userTypeRepository.findUserTypeByPredicate(pageable, userTypeIdToFind, null))
                .thenReturn(new ArrayList<>(Arrays.asList(userType)));

        assertEquals(userTypeIdToFind, userTypeServiceImpl.getUserTypeList(pageable, userTypeIdToFind, null).getBody()
                .getUserTypes().get(0).getId());
        verify(userTypeRepository, times(1)).findUserTypeByPredicate(pageable, userTypeIdToFind, null);

    }

    /*
     * Testing : Get user type data by invalid ID Expectation : Return zero user
     * type and userTypeRepository.findUserTypeByPredicate was called 1 time
     */
    @Test
    public void getUserTypeByInvalidIdTest() {

        final Long userTypeIdToFind = 99L;

        prepareMockingBeforeCallingImplementation();

        assertEquals(0,
                userTypeServiceImpl.getUserTypeList(pageable, userTypeIdToFind, null).getBody().getUserTypes().size());
        verify(userTypeRepository, times(1)).findUserTypeByPredicate(pageable, userTypeIdToFind, null);

    }

    private UserType prepareUserTypeForTest() {

        UserType userType = new UserType();
        userType.setId(10L);
        userType.setTypeName("Admin Local");
        return userType;

    }

    private void prepareMockingBeforeCallingImplementation() {

        when(pageable.getPageSize()).thenReturn(10);
        when(pageable.getSort()).thenReturn(sort);

    }

}
