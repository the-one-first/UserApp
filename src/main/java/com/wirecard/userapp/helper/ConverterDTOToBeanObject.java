package com.wirecard.userapp.helper;

import org.springframework.stereotype.Component;

import com.wirecard.userapp.dto.UserDTO;
import com.wirecard.userapp.dto.UserTypeDTO;
import com.wirecard.userapp.mapper.UserMapper;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.usertype.entity.UserType;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class ConverterDTOToBeanObject {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public User convertUserDTOToUser(UserDTO userDTO) {

        UserMapper userMapper = new UserMapper();
        mapperFactory.classMap(UserDTO.class, User.class).mapNulls(false).customize(userMapper).byDefault().register();
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(userDTO, User.class);

    }

    public UserType convertUserTypeDTOToUserType(UserTypeDTO userTypeDTO) {

        mapperFactory.classMap(UserTypeDTO.class, UserType.class).mapNulls(false).field("name", "typeName").byDefault()
                .register();
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(userTypeDTO, UserType.class);

    }

}
