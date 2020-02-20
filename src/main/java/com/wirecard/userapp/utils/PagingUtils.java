package com.wirecard.userapp.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wirecard.userapp.response.paging.Paging;

public class PagingUtils {

    private PagingUtils() {

    }

    public static <T> List<T> getSubsetList(List<T> listOfData, Pageable pageable) {

        int totalPage = (listOfData.size() / pageable.getPageSize()) + 1;
        if (listOfData.isEmpty()) {
            return new ArrayList<>();
        } else if (pageable.getPageNumber() >= totalPage) {
            return new ArrayList<>();
        } else {
            int firstIndex = getFirstIndex(pageable);
            int lastIndex = getLastIndex(listOfData, pageable);

            return listOfData.subList(firstIndex, lastIndex);
        }

    }

    public static <T> Paging getPaging(Page<T> pageOfData) {

        return new Paging(pageOfData.getTotalElements(), pageOfData.getSize(), pageOfData.getNumber(),
                pageOfData.getTotalPages());

    }

    static int getFirstIndex(Pageable pageable) {

        return pageable.getPageNumber() * pageable.getPageSize();

    }

    static <T> int getLastIndex(List<T> listOfData, Pageable pageable) {

        int lastIndexOfPage = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        int sizeOfData = listOfData.size();
        if (lastIndexOfPage < sizeOfData) {
            return lastIndexOfPage;
        } else {
            return sizeOfData;
        }

    }

}
