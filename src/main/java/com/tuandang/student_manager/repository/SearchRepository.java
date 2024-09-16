package com.tuandang.student_manager.repository;

import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.dto.response.PageResponse;
import com.tuandang.student_manager.entity.Announcement;
import com.tuandang.student_manager.mapper.AnnouncementMapper;
import com.tuandang.student_manager.repository.criteria.AnnouncementSearchCriteriaQueryConsumer;
import com.tuandang.student_manager.repository.criteria.SearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRepository {
    @PersistenceContext
    EntityManager entityManager;
    AnnouncementMapper announcementMapper;
    public PageResponse<List<AnnouncementResponse>> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String... search) {
        // Tham số search có dạng: name: A, datePost: 12,...
        // Lấy ra dách sách
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (search != null) {
            for (String s: search) {
                // name:asc|desc
                //Sử dụng regex để tách chuỗi kiểm tra
                Pattern pattern = Pattern.compile("(\\w+?)(:|>|<)(.*)");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        List<AnnouncementResponse> announcements = new ArrayList<>();
        getAnnouncementsWithCriteria(pageNo, pageSize, criteriaList, sortBy)
                .forEach(announcement -> announcements.add(announcementMapper.toAnnouncementResponse(announcement)));
        // Lấy ra số lượng bản ghi
        return PageResponse.<List<AnnouncementResponse>>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .pageTotal(0)
                .item(announcements)
                .build();
    }

    private List<Announcement> getAnnouncementsWithCriteria(
            int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = query.from(Announcement.class);
        // Xử lý các điều kiện động (WHERE)
        // conjunction() tạo điều kiện luôn true trong điều kiện AND(SELECT * FROM A WHERE TRUE AND)
        // Ngược lại là disjunction() tạo điều kiện False cho OR
        Predicate predicate = criteriaBuilder.conjunction();
        AnnouncementSearchCriteriaQueryConsumer queryConsumer =
                new AnnouncementSearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);
        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        query.where(predicate);

        // Xử lý điều kiện sort(ORDER BY)
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("desc"))
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));
                else
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
            }
        }
        return entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
    }
}
