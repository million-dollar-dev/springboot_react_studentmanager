package com.tuandang.student_manager.repository.specification;

import com.tuandang.student_manager.entity.Announcement;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementSpecificationBuilder {
    public final List<SpecSearchCriteria> param;

    public AnnouncementSpecificationBuilder() {
        this.param = new ArrayList<>();
    }

    public AnnouncementSpecificationBuilder with(String key, String operation, Object value, String prefix, String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public AnnouncementSpecificationBuilder with(String orPredicate, String key, String operation, Object value, String prefix, String suffix) {
        SearchOperation oper = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (oper == SearchOperation.EQUALITY) {
            boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            boolean endWithAsterisk = suffix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            if (startWithAsterisk && endWithAsterisk) {
                oper = SearchOperation.CONTAINS;
            } else if (startWithAsterisk)
                oper = SearchOperation.ENDS_WITH;
            else if (endWithAsterisk)
                oper = SearchOperation.STARTS_WITH;
        }
        param.add(new SpecSearchCriteria(orPredicate, key, oper, value));
        return this;
    }

    public Specification<Announcement> build() {
        if (param.isEmpty())    return null;
        // Tạo specification (điều kiện where) đầu tiên
        Specification<Announcement> specification = new AnnouncementSpecification(param.get(0));
        // Tiếp tục nối tiếp các điều kiện where nếu có
        for (int i = 1; i < param.size(); i++) {
            specification = param.get(i).isOrPredicate() ?
                    Specification.where(specification).or(new AnnouncementSpecification(param.get(i))) :
                    Specification.where(specification).and(new AnnouncementSpecification(param.get(i)));
        }
        return specification;
    }
}
