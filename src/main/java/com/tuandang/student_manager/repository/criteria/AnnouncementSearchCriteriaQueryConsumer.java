package com.tuandang.student_manager.repository.criteria;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementSearchCriteriaQueryConsumer implements Consumer<SearchCriteria> {

    CriteriaBuilder builder;
    // Lưu ý gói của jakarta persistence
    Predicate predicate;
    Root root;

    @Override
    public void accept(SearchCriteria param) {
        if (param.getOperation().equals(">")) {
            builder.and(predicate, builder.greaterThanOrEqualTo(root.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equals("<")) {
            builder.and(predicate, builder.lessThanOrEqualTo(root.get(param.getKey()), param.getValue().toString()));
        } else {
            if (root.get(param.getKey()).getJavaType() == String.class) {
                builder.and(predicate, builder.like(root.get(param.getKey()), param.getValue().toString()));
            } else {
                builder.and(predicate, builder.equal(root.get(param.getKey()), param.getValue().toString()));
            }
        }
    }
}
