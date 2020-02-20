package com.wirecard.userapp.response.usertype;

import java.util.List;

import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.paging.Paging;
import com.wirecard.userapp.response.sorting.Sorting;
import com.wirecard.userapp.usertype.entity.UserType;

public class ResponseUserTypeView extends DefaultResponse {
    
    private List<UserType> userTypes;
    private Paging paging;
    private List<Sorting> sorts;
    
    public ResponseUserTypeView(String status, List<UserType> userTypes, Paging paging, List<Sorting> sorts) {
        super(status);
        this.userTypes = userTypes;
        this.paging = paging;
        this.sorts = sorts;
    }

    public List<UserType> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Sorting> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sorting> sorts) {
        this.sorts = sorts;
    }

}
