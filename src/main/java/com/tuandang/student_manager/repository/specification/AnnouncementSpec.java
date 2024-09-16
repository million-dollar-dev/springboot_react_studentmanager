package com.tuandang.student_manager.repository.specification;

import com.tuandang.student_manager.entity.Announcement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class AnnouncementSpec {
    public static Specification<Announcement> hasTitle(String title) {
//        return new Specification<Announcement>() {
//            @Override
//            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.like(root.get("title"), "%" + title + "%");
//            }
//        };
        // Sử dụng biểu thức lamda:
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Announcement> hasContent(String content) {
        // Sử dụng biểu thức lamda:
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + content + "%");
    }

}
