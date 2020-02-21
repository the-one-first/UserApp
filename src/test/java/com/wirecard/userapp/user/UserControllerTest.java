package com.wirecard.userapp.user;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import com.wirecard.userapp.dto.UserDTO;
import com.wirecard.userapp.enumerator.ErrorEnum;
import com.wirecard.userapp.enumerator.ResponseEnum;
import com.wirecard.userapp.utils.Utils;

@SpringBootTest(classes = UserAppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*
     * Testing : GET /user
     * Expectation : Success to get all user type data
     */
    @Test
    public void getAllUserTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/user")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.users").exists())
                .andExpect(jsonPath("$.users").isArray());
    }
    
    /*
     * Testing : GET /user?id=7
     * Expectation : Success to get user data with ID = 7
     */
    @Test
    public void getUserWithSpecificIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/user?id=7")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.users").exists())
                .andExpect(jsonPath("$.users[*].id").value(7))
                .andExpect(jsonPath("$.users[*].userName").value("Jesslyn L."))
                .andExpect(jsonPath("$.paging.totalData").value(1));
    }
    
    /*
     * Testing : GET /user?id=79
     * Expectation : Failed to get user data with ID = 79
     */
    @Test
    public void getUserWithInvalidIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/user?id=79")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.users").exists())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.paging.totalData").value(0));
    }
    
    /*
     * Testing : POST /user
     * Expectation : Success to add new user data with ID = 1000
     */
    @Test
    public void addNewUserTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/user")
                .content(Utils.asJsonString(new UserDTO(null, "Hadi Gunawan", new Date(), 4L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("Hadi Gunawan"));
    }
    
    /*
     * Testing : POST /user
     * Expectation : Failed to add new user because name is already exist
     */
    @Test
    public void addNewUserWithInvalidNameTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/user")
                .content(Utils.asJsonString(new UserDTO(null, "Monica L.", new Date(), 6L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(ResponseEnum.ERROR_STATUS.getCode()))
                .andExpect(jsonPath("$.details").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[*].code").value(ErrorEnum.ERR_USER_NM_UNIQUE.getCode()))
                .andExpect(jsonPath("$.details[*].desc").value(ErrorEnum.ERR_USER_NM_UNIQUE.getDesc()));
    }
    
    /*
     * Testing : PUT /user/2
     * Expectation : Success to update existing user data with ID = 2
     */
    @Test
    public void updateExistingUserTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/user/{id}",2)
                .content(Utils.asJsonString(new UserDTO(null, "Michael Owen", new Date(), 9L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("Michael Owen"));
    }
    
    /*
     * Testing : PUT /user/77
     * Expectation : Failed to update existing user data with ID = 77
     */
    @Test
    public void updateExistingUserWithInvalidIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/user/{id}",77)
                .content(Utils.asJsonString(new UserDTO(null, "Michael Owen", new Date(), 9L)))
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
     * Testing : DELETE /user/3
     * Expectation : Success to delete existing user data with ID = 3
     */
    @Test
    public void deleteExistingUserTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/user/{id}",3))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value(ResponseEnum.SUCCESS_STATUS.getCode()))
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.userId").value(3));
    }
    
    /*
     * Testing : DELETE /user/54
     * Expectation : Failed to delete existing user data with ID = 54 because of data not found
     */
    @Test
    public void deleteExistingUserWithInvalidIdTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/user/{id}",54))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(ResponseEnum.ERROR_STATUS.getCode()))
                .andExpect(jsonPath("$.details").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[*].code").value(ErrorEnum.ERR_UNEXPECTED_ROLLBACK.getCode()))
                .andExpect(jsonPath("$.details[*].desc").value(ErrorEnum.ERR_UNEXPECTED_ROLLBACK.getDesc()));
    }

}
