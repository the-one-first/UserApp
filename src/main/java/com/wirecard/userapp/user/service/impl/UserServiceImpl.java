package com.wirecard.userapp.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.wirecard.userapp.enumerator.ErrorEnum;
import com.wirecard.userapp.enumerator.ResponseEnum;
import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.error.CodeDescError;
import com.wirecard.userapp.response.error.ResponseError;
import com.wirecard.userapp.response.paging.Paging;
import com.wirecard.userapp.response.sorting.Sorting;
import com.wirecard.userapp.response.user.ResponseUserDelete;
import com.wirecard.userapp.response.user.ResponseUserInsertUpdate;
import com.wirecard.userapp.response.user.ResponseUserView;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.user.repository.UserRepository;
import com.wirecard.userapp.user.service.UserService;
import com.wirecard.userapp.utils.PagingUtils;
import com.wirecard.userapp.utils.SortingUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 10)
    public ResponseEntity<ResponseUserView> getUserList(Pageable pageable, Long id, String name, Date date,
            Long userType) {

        List<User> users = userRepository.findUserByPredicate(pageable, id, name, date, userType);
        List<User> listOfUser = PagingUtils.getSubsetList(users, pageable);

        Page<User> userData = new PageImpl<>(listOfUser, pageable, users.size());
        Paging paging = PagingUtils.getPaging(userData);
        List<Sorting> listSorting = SortingUtils.getListSorting(userData);

        return new ResponseEntity<>(
                new ResponseUserView(ResponseEnum.SUCCESS_STATUS.getCode(), listOfUser, paging, listSorting),
                HttpStatus.OK);

    }

    public ResponseEntity<DefaultResponse> insertNewUser(User user) {

        List<CodeDescError> details = new ArrayList<>();

        try {

            boolean isValidUserName = isValidToInsertUserName(user);

            if (!isValidUserName) {
                CodeDescError errorConstraintUserNameUnique = new CodeDescError(ErrorEnum.ERR_USER_NM_UNIQUE.getCode(),
                        ErrorEnum.ERR_USER_NM_UNIQUE.getDesc());
                details.add(errorConstraintUserNameUnique);
            }

            if (details.isEmpty()) {

                if(user.getUserDate() == null) {
                    user.setUserDate(new Date());
                }
                userRepository.save(user);

                return new ResponseEntity<>(
                        new ResponseUserInsertUpdate(ResponseEnum.SUCCESS_STATUS.getCode(), user.getUserName()),
                        HttpStatus.CREATED);

            }

            return new ResponseEntity<>(new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), details),
                    HttpStatus.CONFLICT);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    new ResponseError(ResponseEnum.ERROR_STATUS.getCode(),
                            Collections.singletonList(new CodeDescError(ResponseEnum.FAILED_INSERT.getCode(),
                                    ResponseEnum.FAILED_INSERT.getDesc() + " because " + e.getMessage()))),
                    HttpStatus.NOT_FOUND);

        }

    }

    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 10)
    public ResponseEntity<DefaultResponse> updateExistingUserById(Long id, User user) {

        try {

            Optional<User> userObject = userRepository.findById(id);

            if (userObject.isPresent()) {

                userObject.get().setUserName(user.getUserName());
                userObject.get().setUserDate(user.getUserDate());
                userObject.get().setUserType(user.getUserType());

                userRepository.save(userObject.get());

                return new ResponseEntity<>(new ResponseUserInsertUpdate(ResponseEnum.SUCCESS_STATUS.getCode(),
                        userObject.get().getUserName()), HttpStatus.OK);

            } else {

                return new ResponseEntity<>(
                        new ResponseError(ResponseEnum.ERROR_STATUS.getCode(),
                                Collections
                                        .singletonList(new CodeDescError(ResponseEnum.FAILED_UPDATE_NOT_FOUND.getCode(),
                                                ResponseEnum.FAILED_UPDATE_NOT_FOUND.getDesc()))),
                        HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {

            return new ResponseEntity<>(new ResponseError(ResponseEnum.ERROR_STATUS.getCode(),
                    Collections.singletonList(new CodeDescError(ResponseEnum.FAILED_UPDATE.getCode(),
                            ResponseEnum.FAILED_UPDATE.getDesc()))),
                    HttpStatus.NOT_FOUND);

        }

    }

    @Transactional(timeout = 10)
    public ResponseEntity<DefaultResponse> deleteExistingUserById(Long id) {

        try {

            userRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseUserDelete(ResponseEnum.SUCCESS_STATUS.getCode(), id),
                    HttpStatus.ACCEPTED);

        } catch (Exception e) {

            return new ResponseEntity<>(new ResponseError(ResponseEnum.ERROR_STATUS.getCode(),
                    Collections.singletonList(new CodeDescError(ResponseEnum.FAILED_DELETE.getCode(),
                            ResponseEnum.FAILED_DELETE.getDesc()))),
                    HttpStatus.NOT_FOUND);

        }

    }

    private boolean isValidToInsertUserName(User user) {

        List<String> userNames = getAllUserNameListInString();
        return !userNames.contains(user.getUserName());

    }

    private List<String> getAllUserNameListInString() {

        List<String> resultUserNamesExistingList = new ArrayList<>();

        List<User> existingUserNamesList = userRepository.findAll();

        for (User loopUserName : existingUserNamesList) {
            resultUserNamesExistingList.add(loopUserName.getUserName());
        }

        return resultUserNamesExistingList;

    }

}
