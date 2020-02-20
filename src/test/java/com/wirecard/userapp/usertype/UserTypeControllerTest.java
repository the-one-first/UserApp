package com.wirecard.userapp.usertype;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wirecard.userapp.UserAppApplication;
import com.wirecard.userapp.dto.UserTypeDTO;
import com.wirecard.userapp.enumerator.ErrorEnum;
import com.wirecard.userapp.enumerator.ResponseEnum;
import com.wirecard.userapp.utils.Utils;

@SpringBootTest(classes = UserAppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class UserTypeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    /*
     * Testing : GET /usertype
     * Expectation : Success to get all user type data
     */
    @Test
    public void getAllUserTypeTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/usertype")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userTypes").exists())
                .andExpect(jsonPath("$.userTypes").isArray());
    }
    
    /*
     * Testing : GET /usertype?id=4
     * Expectation : Success to get user type data with ID = 4
     */
    @Test
    public void getUserTypeWithSpecificIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/usertype?id=4")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userTypes").exists())
                .andExpect(jsonPath("$.userTypes[*].id").value(4))
                .andExpect(jsonPath("$.userTypes[*].typeName").value("Developer Local"))
                .andExpect(jsonPath("$.paging.totalData").value(1));
    }
    
    /*
     * Testing : GET /usertype?id=99
     * Expectation : Failed to get user type data with ID = 99
     */
    @Test
    public void getUserTypeWithInvalidIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/usertype?id=99")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userTypes").exists())
                .andExpect(jsonPath("$.userTypes").isArray())
                .andExpect(jsonPath("$.paging.totalData").value(0));
    }
    
    /*
     * Testing : POST /usertype
     * Expectation : Success to add new user type data with ID = 1000
     */
    @Test
    public void addNewUserTypeTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/usertype")
                .content(Utils.asJsonString(new UserTypeDTO(null, "Senior Manager Local")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userTypeName").exists())
                .andExpect(jsonPath("$.userTypeName").value("Senior Manager Local"));
    }
    
    /*
     * Testing : POST /usertype
     * Expectation : Failed to add new user type because name is already exist
     */
    @Test
    public void addNewUserTypeWithInvalidNameTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/usertype")
                .content(Utils.asJsonString(new UserTypeDTO(null, "Senior Developer Local")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(ResponseEnum.ERROR_STATUS.getCode()))
                .andExpect(jsonPath("$.details").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[*].code").value(ErrorEnum.ERR_USER_TYPE_NM_UNIQUE.getCode()))
                .andExpect(jsonPath("$.details[*].desc").value(ErrorEnum.ERR_USER_TYPE_NM_UNIQUE.getDesc()));
    }
    
    /*
     * Testing : PUT /usertype/1
     * Expectation : Success to update existing user type data with ID = 1
     */
    @Test
    public void updateExistingUserTypeTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/usertype/{id}",1)
                .content(Utils.asJsonString(new UserTypeDTO(1L, "Junior Manager Local")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userTypeName").exists())
                .andExpect(jsonPath("$.userTypeName").value("Junior Manager Local"));
    }
    
    /*
     * Testing : PUT /usertype/87
     * Expectation : Failed to update existing user type data with ID = 87
     */
    @Test
    public void updateExistingUserTypeWithInvalidIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/usertype/{id}",87)
                .content(Utils.asJsonString(new UserTypeDTO(87L, "Middle Manager Local")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(ResponseEnum.ERROR_STATUS.getCode()))
                .andExpect(jsonPath("$.details").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[*].code").value(ResponseEnum.FAILED_UPDATE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.details[*].desc").value(ResponseEnum.FAILED_UPDATE_NOT_FOUND.getDesc()));
    }
    
    /*
     * Testing : DELETE /usertype/17
     * Expectation : Success to delete existing user type data with ID = 17
     */
    @Test
    public void deleteExistingUserTypeTest() throws Exception {
    	mockMvc.perform( MockMvcRequestBuilders
                .delete("/usertype/{id}",17))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userTypeId").exists())
                .andExpect(jsonPath("$.userTypeId").value(17));
    }
    
    /*
     * Testing : DELETE /usertype/2
     * Expectation : Failed to delete existing user type data with ID = 2 because of data integration exception
     */
    @Test
    public void deleteExistingUserTypeWithInvalidIdTest() throws Exception {
    	mockMvc.perform( MockMvcRequestBuilders
                .delete("/usertype/{id}",2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(ResponseEnum.ERROR_STATUS.getCode()))
                .andExpect(jsonPath("$.details").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[*].code").value(ErrorEnum.ERR_DATA_INTEGRATION.getCode()))
                .andExpect(jsonPath("$.details[*].desc").value(ErrorEnum.ERR_DATA_INTEGRATION.getDesc()));
    }

}
