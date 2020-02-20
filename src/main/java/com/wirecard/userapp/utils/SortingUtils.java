package com.wirecard.userapp.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import com.wirecard.userapp.enumerator.ConfigEnum;
import com.wirecard.userapp.response.sorting.Sorting;

public class SortingUtils {

    private SortingUtils() {

    }

    public static <T> List<Sorting> getListSorting(Page<T> pageOfData) {

        List<Sorting> listSorting = new ArrayList<>();
        Iterator<Order> iterSorting = pageOfData.getSort().get().iterator();
        while (iterSorting.hasNext()) {
            Order order = iterSorting.next();
            Sorting sorting = new Sorting(order.getProperty(), order.getDirection().toString());
            listSorting.add(sorting);
        }
        return listSorting;

    }

    public static <T> List<javax.persistence.criteria.Order> getListSortingOrder(CriteriaBuilder cb, Root<T> root,
            Pageable pageable) {

        List<javax.persistence.criteria.Order> listSortingOrder = new ArrayList<>();
        Iterator<Order> iterSorting = pageable.getSort().get().iterator();

        while (iterSorting.hasNext()) {
            Order orderTemp = iterSorting.next();
            javax.persistence.criteria.Order order;
            if (ConfigEnum.DESCENDING.getDesc().equalsIgnoreCase(orderTemp.getDirection().toString())) {
                order = cb.desc(root.get(orderTemp.getProperty()));
            } else {
                order = cb.asc(root.get(orderTemp.getProperty()));
            }
            listSortingOrder.add(order);
        }

        return listSortingOrder;

    }

}
