package com.tuandang.student_manager.repository;

import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.dto.response.PageResponse;
import com.tuandang.student_manager.entity.Announcement;
import com.tuandang.student_manager.repository.criteria.SearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public PageResponse<List<AnnouncementResponse>> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String... search) {
        // Tham số search có dạng: name: A, datePost: 12,...
        // Lấy ra dách sách
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (search != null) {
            for (String s: search) {
                // name:asc|desc
                //Sử dụng regex để tách chuỗi kiểm tra
                Pattern pattern = Pattern.compile("(\\w+?)(:|>|<)(.*)");
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        // Lấy ra số lượng bản ghi
        Long totalElements = 1L;
        return PageResponse.<List<AnnouncementResponse>>builder()

                .build();
    }

    private List<Announcement> getAnnouncementsWithCriteria(
            int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = query.from(Announcement.class);
        return new ArrayList<>();
    }
}
