package com.wirecard.userapp.response.user;

import java.util.List;

import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.paging.Paging;
import com.wirecard.userapp.response.sorting.Sorting;
import com.wirecard.userapp.user.entity.User;

public class ResponseUserView extends DefaultResponse {

    private List<User> users;
    private Paging paging;
    private List<Sorting> sorts;
    
    public ResponseUserView(String status, List<User> users, Paging paging, List<Sorting> sorts) {
        super(status);
        this.users = users;
        this.paging = paging;
        this.sorts = sorts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
