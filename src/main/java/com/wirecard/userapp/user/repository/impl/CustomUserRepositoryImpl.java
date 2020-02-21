package com.wirecard.userapp.user.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;

import com.wirecard.userapp.enumerator.ConfigEnum;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.user.repository.CustomUserRepository;
import com.wirecard.userapp.usertype.entity.UserType;
import com.wirecard.userapp.utils.SortingUtils;

import liquibase.util.StringUtils;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Value("${spring.profiles.active}")
    private String activeEnv;

    private final EntityManager em;

    public CustomUserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<User> findUserByPredicate(Pageable pageable, Long id, String name, Date date, Long userType) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        List<Predicate> predicates = getPredicateList(cb, root, id, name, date, userType);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(SortingUtils.getListSortingOrder(cb, root, pageable));

        return em.createQuery(cq).getResultList();

    }

    private List<Predicate> getPredicateList(CriteriaBuilder cb, Root<User> root, Long id, String name, Date date,
            Long userType) {

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (StringUtils.isNotEmpty(name)) {
            predicates.add(cb.like(cb.lower(root.get("userName")), "%" + name.toLowerCase() + "%"));
        }
        if (date != null) {
            
            if (ConfigEnum.LOCAL_ENV.getDesc().equals(activeEnv)) {
                SimpleDateFormat sdf = new SimpleDateFormat(ConfigEnum.DATE_FORMAT_H2_COMPARE.getDesc());
                predicates.add(cb.equal(
                        cb.function("FORMATDATETIME", Date.class, root.get("userDate"), cb.literal("YYYY-MM-DD")),
                        sdf.format(date)));
            }
            if (ConfigEnum.SERVER_ENV.getDesc().equals(activeEnv)) {
                SimpleDateFormat sdf = new SimpleDateFormat(ConfigEnum.DATE_FORMAT_MYSQL_COMPARE.getDesc());
                predicates.add(
                        cb.equal(cb.function("DATE_FORMAT", Date.class, root.get("userDate"), cb.literal("%Y-%m-%d")),
                                sdf.format(date)));
            }
        }
        if (userType != null) {
            Join<User, UserType> userTypeJoin = root.join("userType");
            predicates.add(cb.equal(userTypeJoin.<Long>get("id"), userType));
        }

        return predicates;

    }
    
}
