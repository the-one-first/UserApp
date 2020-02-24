package com.wirecard.userapp.usertype.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import com.wirecard.userapp.response.usertype.ResponseUserTypeDelete;
import com.wirecard.userapp.response.usertype.ResponseUserTypeInsertUpdate;
import com.wirecard.userapp.response.usertype.ResponseUserTypeView;
import com.wirecard.userapp.usertype.entity.UserType;
import com.wirecard.userapp.usertype.repository.UserTypeRepository;
import com.wirecard.userapp.usertype.service.UserTypeService;
import com.wirecard.userapp.utils.PagingUtils;
import com.wirecard.userapp.utils.SortingUtils;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 10)
    public ResponseEntity<ResponseUserTypeView> getUserTypeList(Pageable pageable, Long id, String userTypeName) {

        List<UserType> userTypes = userTypeRepository.findUserTypeByPredicate(pageable, id, userTypeName);
        List<UserType> listOfUserTypes = PagingUtils.getSubsetList(userTypes, pageable);

        Page<UserType> userTypeData = new PageImpl<>(listOfUserTypes, pageable, userTypes.size());
        Paging paging = PagingUtils.getPaging(userTypeData);
        List<Sorting> listSorting = SortingUtils.getListSorting(userTypeData);

        return new ResponseEntity<>(
                new ResponseUserTypeView(ResponseEnum.SUCCESS_STATUS.getCode(), listOfUserTypes, paging, listSorting),
                HttpStatus.OK);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 5)
    public List<UserType> getAllUserTypeList() {

        return userTypeRepository.findAll();

    }

    public ResponseEntity<DefaultResponse> insertNewUserType(UserType userType) {

        List<CodeDescError> details = new ArrayList<>();

        try {

            if (!isValidToInsertUserTypeName(userType)) {
                CodeDescError errorConstraintUserTypeNameUnique = new CodeDescError(
                        ErrorEnum.ERR_USER_TYPE_NM_UNIQUE.getCode(), ErrorEnum.ERR_USER_TYPE_NM_UNIQUE.getDesc());
                details.add(errorConstraintUserTypeNameUnique);
            }

            if (details.isEmpty()) {

                userTypeRepository.save(userType);

                return new ResponseEntity<>(
                        new ResponseUserTypeInsertUpdate(ResponseEnum.SUCCESS_STATUS.getCode(), userType.getTypeName()),
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
    public ResponseEntity<DefaultResponse> updateExistingUserTypeById(Long id, UserType userType) {

        try {

            Optional<UserType> userTypeExist = userTypeRepository.findById(id);

            if (userTypeExist.isPresent()) {

                userTypeExist.get().setTypeName(userType.getTypeName());

                userTypeRepository.save(userTypeExist.get());

                return new ResponseEntity<>(
                        new ResponseUserTypeInsertUpdate(ResponseEnum.SUCCESS_STATUS.getCode(), userType.getTypeName()),
                        HttpStatus.OK);

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
    public ResponseEntity<DefaultResponse> deleteExistingUserTypeById(Long id) {

        try {

            userTypeRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseUserTypeDelete(ResponseEnum.SUCCESS_STATUS.getCode(), id),
                    HttpStatus.ACCEPTED);

        } catch (Exception e) {

            return new ResponseEntity<>(new ResponseError(ResponseEnum.ERROR_STATUS.getCode(),
                    Collections.singletonList(new CodeDescError(ResponseEnum.FAILED_DELETE.getCode(),
                            ResponseEnum.FAILED_DELETE.getDesc()))),
                    HttpStatus.NOT_FOUND);

        }

    }

    private boolean isValidToInsertUserTypeName(UserType userType) {

        List<String> userTypeNames = getAllUserTypeNameListInString();
        return !userTypeNames.contains(userType.getTypeName());

    }

    private List<String> getAllUserTypeNameListInString() {

        List<String> resultUserTypeNamesExistingList = new ArrayList<>();

        List<UserType> existingUserTypeNamesList = userTypeRepository.findAll();

        for (UserType loopUserTypeName : existingUserTypeNamesList) {
            resultUserTypeNamesExistingList.add(loopUserTypeName.getTypeName());
        }

        return resultUserTypeNamesExistingList;

    }

}
