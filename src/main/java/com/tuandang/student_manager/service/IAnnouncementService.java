package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAnnouncementService {
    AnnouncementResponse create(AnnouncementRequest request);
    PageResponse<List<AnnouncementResponse>> getAnnouncements(int pageNo, int pageSize, String sortBy);
    PageResponse<List<AnnouncementResponse>> getAnnouncementsWithMultipleColumns(int pageNo, int pageSize, String... sorts);
    AnnouncementResponse update(String id, AnnouncementRequest request);
    void delete(String id);

    PageResponse<List<AnnouncementResponse>> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String... search);
}
