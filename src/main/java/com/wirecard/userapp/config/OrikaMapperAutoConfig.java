package com.wirecard.userapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.wirecard.userapp.dto.UserDTO;
import com.wirecard.userapp.dto.UserTypeDTO;
import com.wirecard.userapp.helper.ConverterDTOToBeanObject;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.usertype.entity.UserType;

@Configuration
public class OrikaMapperAutoConfig {

    @Autowired
    private ConverterDTOToBeanObject converterDTOToBeanObject;
    
    public User getUserBeanFromUserDTO(UserDTO userDTO) {
        
        return converterDTOToBeanObject.convertUserDTOToUser(userDTO);
        
    }
    
    public UserType getUserTypeBeanFromUserTypeDTO(UserTypeDTO userTypeDTO) {
        
        return converterDTOToBeanObject.convertUserTypeDTOToUserType(userTypeDTO);
        
    }
    
}
