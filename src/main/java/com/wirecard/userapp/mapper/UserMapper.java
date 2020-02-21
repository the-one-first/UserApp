package com.wirecard.userapp.mapper;

import com.wirecard.userapp.dto.UserDTO;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.usertype.entity.UserType;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class UserMapper extends CustomMapper<UserDTO, User> {

    @Override
    public void mapAtoB(UserDTO a, User b, MappingContext context) {

        UserType userType = new UserType();
        userType.setId(a.getUserTypeId());
        b.setUserType(userType);

    }

    @Override
    public void mapBtoA(User b, UserDTO a, MappingContext context) {

        a.setUserTypeId(b.getUserType().getId());

    }

}
