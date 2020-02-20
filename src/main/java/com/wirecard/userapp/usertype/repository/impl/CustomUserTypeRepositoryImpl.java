package com.wirecard.userapp.usertype.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;

import com.wirecard.userapp.usertype.entity.UserType;
import com.wirecard.userapp.usertype.repository.CustomUserTypeRepository;
import com.wirecard.userapp.utils.SortingUtils;

import liquibase.util.StringUtils;

public class CustomUserTypeRepositoryImpl implements CustomUserTypeRepository {
    
    private final EntityManager em;

    public CustomUserTypeRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<UserType> findUserTypeByPredicate(Pageable pageable, Long id, String userTypeName) {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserType> cq = cb.createQuery(UserType.class);
        Root<UserType> root = cq.from(UserType.class);
        
        List<Predicate> predicates = getPredicateList(cb, root, id, userTypeName);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(SortingUtils.getListSortingOrder(cb, root, pageable));
        
        return em.createQuery(cq).getResultList();
    }
    
    private List<Predicate> getPredicateList(CriteriaBuilder cb, Root<UserType> root, Long id, String userTypeName) {

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (StringUtils.isNotEmpty(userTypeName)) {
            predicates.add(cb.like(cb.lower(root.get("typeName")), "%" + userTypeName.toLowerCase() + "%"));
        }

        return predicates;

    }

}
